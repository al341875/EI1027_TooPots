package com.example.ei1027.controller;


import com.example.ei1027.dao.ClientDao;
import com.example.ei1027.model.Client;
import com.example.ei1027.validation.ClientValidator;
import com.example.ei1027.validation.excepcions.ClientException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/client")
public class ClientController {

	@Autowired
	private ClientDao clientDao;

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
	@PostMapping(value = "/add")
	public String addClient(@ModelAttribute("client") Client client, BindingResult bindingResult) {
        ClientValidator clientValidator = new ClientValidator();
        clientValidator.validate(client, bindingResult);
        //if (clientDao.existId(client.getClientId()))
        //El id ya existe!
        //if (clientDao.existMail(client.getEmail()))
        //El email ya existe!
        if (bindingResult.hasErrors())
			return "client/add";
        try {
        	clientDao.addClient(client);
        }catch(DuplicateKeyException e) {
        	throw new ClientException("Ja existeix un client en el NIF: "+client.getClientId(),"ClauPrimariaDuplicada");
        }
	
		return "redirect:list";
	}
	@GetMapping(value="/update/{clientId}")
	public String update(Model model, @PathVariable String clientId) {
			model.addAttribute("client", clientDao.getClient(clientId));
		return "client/update"; 
	}
	@PostMapping(value="/update")
	public String update(@ModelAttribute("client") Client client,
						 BindingResult bindingResult) {
        ClientValidator clientValidator = new ClientValidator();
        clientValidator.validate(client, bindingResult);
        //if (clientDao.existId(client.getClientId()))
        //El id ya existe!
        //if (clientDao.existMail(client.getEmail()))
        //El email ya existe!
        if (bindingResult.hasErrors())
			return "client/update";
		clientDao.updateClient(client);
		return "redirect:list";
	  }
	@RequestMapping(value = "/delete/{clientId}")
	public String delete(@PathVariable String clientId) {
		clientDao.deleteClient(clientId);
		return "redirect:../list";
	}
}
