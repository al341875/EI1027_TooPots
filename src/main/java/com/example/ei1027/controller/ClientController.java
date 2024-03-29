package com.example.ei1027.controller;


import com.example.ei1027.dao.ClientDao;
import com.example.ei1027.dao.UserDao;
import com.example.ei1027.model.Client;
import com.example.ei1027.model.UserDetails;
import com.example.ei1027.validation.ClientValidator;
import com.example.ei1027.validation.excepcions.ClientException;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/client")
public class ClientController {

	@Autowired
	private ClientDao clientDao;
	@Autowired
	private UserDao userDao;
	@Value("${upload.file.directory}")
    private String uploadDirectory;
	@GetMapping("/list")
	public String listClient(Model model) {
		model.addAttribute("clients", clientDao.getClients());
		return "client/list";
	}
	@GetMapping(value="/list/{clientId}")
	public String getClient(Model model, @PathVariable String clientId) {
		model.addAttribute("clients", clientDao.getClient(clientId));
		return "client/list";
	}
	@GetMapping(value = "/add")
	public String addClient(Model model) {
		model.addAttribute("client", new Client());
		return "client/add";
	}
	@GetMapping(value = "/show/{clientId}")
	public String showClient(Model model,@PathVariable String clientId) {
		model.addAttribute("client", clientDao.getClient(clientId));
		return "client/show";
	}
	@PostMapping(value = "/add")
	public String addClient(@ModelAttribute("client") Client client, BindingResult bindingResult,
			@RequestParam("file") MultipartFile file,
            RedirectAttributes redirectAttributes) {
        ClientValidator clientValidator = new ClientValidator();
        clientValidator.validate(client, bindingResult);
        if (clientDao.existId(client.getClientId()))
        	throw new ClientException("DNI duplicat","ClauPrimariaDuplicada");
        //El id ya existe!
        if (clientDao.existEmail(client.getEmail()))
        	throw new ClientException("E-mail duplicat","ClauPrimariaDuplicada");
        //El email ya existe!
		//afegir client en tabla usuari
		UserDetails user = new UserDetails();
		user.setUsuari(client.getClientId());
		user.setTipus("client");
		user.setContrasenya(client.getContrasenya());
		userDao.add(user);
        if (bindingResult.hasErrors())
			return "client/add";
        if (file.isEmpty()) {
            // Enviar mensaje de error porque no hay fichero seleccionado
            redirectAttributes.addFlashAttribute("message",
                    "Please select a file to upload");
            return "redirect:/uploadStatus";
        }

        try {
            // Obtener el fichero y guardarlo
            byte[] bytes = file.getBytes();
            Path path = Paths.get(uploadDirectory + "imatges/"
                    + file.getOriginalFilename());
            Files.write(path, bytes);
        } catch (IOException e) {
            e.printStackTrace();
        }
        client.setImatge(file.getOriginalFilename());

        try {
            
        	clientDao.addClient(client);
        }catch(DuplicateKeyException e) {
        	throw new ClientException("Ja existeix un client en el NIF: "+client.getClientId(),"ClauPrimariaDuplicada");
        }
	
		return "home/client";
	}
	@GetMapping(value="/update/{clientId}")
	public String update(Model model, @PathVariable String clientId) {
			model.addAttribute("client", clientDao.getClient(clientId));
		return "client/update"; 
	}
	@PostMapping(value="/update")
	public String update(@ModelAttribute("client") Client client,
						 BindingResult bindingResult,@RequestParam("file") MultipartFile file,
				            RedirectAttributes redirectAttributes) {
        ClientValidator clientValidator = new ClientValidator();
        clientValidator.validate(client, bindingResult);
        //if (clientDao.existId(client.getClientId()))
        //El id ya existe!
        //if (clientDao.existMail(client.getEmail()))
        //El email ya existe!
        if (bindingResult.hasErrors())
			return "client/update";
        if (file.isEmpty()) {
            // Enviar mensaje de error porque no hay fichero seleccionado
            redirectAttributes.addFlashAttribute("message",
                    "Please select a file to upload");
            return "redirect:/uploadStatus";
        }

        try {
            // Obtener el fichero y guardarlo
            byte[] bytes = file.getBytes();
            Path path = Paths.get(uploadDirectory + "imatges/"
                    + file.getOriginalFilename());
            Files.write(path, bytes);
        } catch (IOException e) {
            e.printStackTrace();
        }
        client.setImatge(file.getOriginalFilename());
		clientDao.updateClient(client);
		return "/home/client";
	  }
	@RequestMapping(value = "/delete/{clientId}")
	public String delete(@PathVariable String clientId) {
		clientDao.deleteClient(clientId);
		return "redirect:../list";
	}
    @RequestMapping(value = "/imatges/{id}")
    public String mostrarImatge(Model model,@PathVariable String id) {
        model.addAttribute("client", clientDao.getImatge(id));
        return "client/list";
    }
}
