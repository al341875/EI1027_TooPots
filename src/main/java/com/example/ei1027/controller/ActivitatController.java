package com.example.ei1027.controller;

import com.example.ei1027.dao.ActivitatDao;
import com.example.ei1027.dao.TipusActivitatDao;
import com.example.ei1027.model.Activitat;
import com.example.ei1027.model.EstatActivitat;
import com.example.ei1027.validation.ActivitatValidator;
import com.example.ei1027.validation.excepcions.ActivitatException;
import com.example.ei1027.validation.excepcions.ClientException;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import com.example.ei1027.validation.excepcions.UserException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/activitat")
public class ActivitatController {

	@Autowired
	private ActivitatDao activitatDao;

	@Autowired
	private TipusActivitatDao tipusActivitatDao;
	@Value("${upload.file.directory}")
    private String uploadDirectory;

	@GetMapping("/list")
	public String listActivitatsDisponibles(Model model) {
	    //sols es mostren les que tenen el estat disponible
		model.addAttribute("activitats", activitatDao.getActivitatsByStatus(EstatActivitat.OBERTA.toString()));
		return "activitat/list";
	}
    @GetMapping("/listAdmin")
    public String listActivitats(Model model) {
        // es mostren totes les activitats
        model.addAttribute("activitats", activitatDao.getActivitats());
        return "activitat/listAdmin";
    }
    @GetMapping("/sortFecha")
    public String listActivitatsFecha(Model model) {
        model.addAttribute("activitats", activitatDao.getActivitatsSortFecha());
        return "activitat/listAdmin";
    }
    @GetMapping("/sortTipus")
    public String listActivitatsTipus(Model model) {
        model.addAttribute("activitats", activitatDao.getActivitatsSortTipus());
        return "activitat/listAdmin";
    }
    @GetMapping("/sortEstat")
    public String listActivitatsEstat(Model model) {
        model.addAttribute("activitats", activitatDao.getActivitatsSortEstat());
        return "activitat/listAdmin";
    }
	@GetMapping("/listInstructor")
	public String listActivitatsInstructor(Model model,HttpSession session) {
        String user = (String)  session.getAttribute("username");
        if ( user == null) {
            throw new UserException("Usuari no valid","usuariNoValid");
        }
		model.addAttribute("activitats", activitatDao.getActivitatsByInstructor(user));
		return "activitat/listInstructor";
	}
	@GetMapping(value = "/list/{nomLlarg}")
	public String getActivitat(Model model, @PathVariable String nomLlarg) {
		model.addAttribute("activitats", activitatDao.getActivitat(nomLlarg));
		return "activitat/list";
	}

	@GetMapping(value = "/add")
	public String addActivitat(Model model) {
		model.addAttribute("activitat", new Activitat());
		model.addAttribute("tipusActivitats", tipusActivitatDao.getTipusActivitats());
		
		return "activitat/add";
	}

	@PostMapping(value = "/add")
    public String addActivitat(@ModelAttribute("activitat") Activitat activitat, BindingResult bindingResult, HttpSession session,
                               @RequestParam("file") MultipartFile file, Model model,
                               RedirectAttributes redirectAttributes) {
        String user = (String)  session.getAttribute("username");
        if ( user == null) {
            throw new UserException("Usuari no valid","usuariNoValid");
        }
		ActivitatValidator activitatValidator=new ActivitatValidator();
		  activitatValidator.validate(activitat, bindingResult);
		  model.addAttribute("tipusActivitats", tipusActivitatDao.getTipusActivitats());

        if (bindingResult.hasErrors()) {
            System.out.println(bindingResult);
            return "activitat/add";
            
        }	//
		activitat.setIdInstructor(user);
		activitat.setEstat(EstatActivitat.OBERTA.toString());
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
        activitat.setImatge(file.getOriginalFilename());
        
        
        try {
        	activitatDao.addActivitat(activitat);
        }catch(DuplicateKeyException e) {
        	throw new ActivitatException("Ja existeix un activitat de nom: "+activitat.getNomLlarg(),"ClauPrimariaDuplicada");
        }
        return "redirect:listInstructor";
    }
    @GetMapping(value="/update/{nomLlarg}")
    public String update(Model model, @PathVariable String nomLlarg) {
        model.addAttribute("activitat", activitatDao.getActivitat(nomLlarg));
        model.addAttribute("tipusActivitats", tipusActivitatDao.getTipusActivitats());
        return "activitat/update";
    }
    @PostMapping(value="/update")
    public String update(@ModelAttribute("activitat") Activitat activitat,
                         BindingResult bindingResult, Model model ,@RequestParam("file") MultipartFile file,
                         RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors())
            return "activitat/update";
        
    	 ActivitatValidator activitatValidator = new ActivitatValidator();
    	 activitatValidator.validate(activitat, bindingResult);
    	 model.addAttribute("tipusActivitats", tipusActivitatDao.getTipusActivitats()); 
    	 //if( !(activitatDao.existIdInstructor(activitat.getIdInstructor())) )
    		 //No existe un instructor con este id
    			 
    			 
    	if (bindingResult.hasErrors()) {
            System.out.println(bindingResult);
            return "activitat/update";
        }
    	activitat.setEstat(EstatActivitat.OBERTA.toString());
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
        activitat.setImatge(file.getOriginalFilename());
    	activitatDao.updateActivitat(activitat);

        return "redirect:listInstructor";
    }

    @GetMapping(value = "/show/{nomLlarg}")
    public String showClient(Model model,@PathVariable String nomLlarg) {
        model.addAttribute("activitat", activitatDao.getActivitat(nomLlarg));
        return "activitat/show";
    }
    @GetMapping(value = "/show2/{nomLlarg}")
    public String show2Client(Model model,@PathVariable String nomLlarg) {
        model.addAttribute("activitat", activitatDao.getActivitat(nomLlarg));
        return "activitat/show2C";
    }
    @GetMapping(value = "/showInstructor/{nomLlarg}")
    public String showInstructor(Model model,@PathVariable String nomLlarg) {
        model.addAttribute("activitat", activitatDao.getActivitat(nomLlarg));
        return "activitat/showInstructor";
    }
	@RequestMapping(value = "/delete/{nomLlarg}")
	public String delete(@PathVariable String nomLlarg) {
		activitatDao.deleteActivitat(nomLlarg);
		return "redirect:../listInstructor";
	}
    @RequestMapping(value = "/tanca/{nomLlarg}")
    public String tancaActivitat(@PathVariable String nomLlarg) {
        activitatDao.tancaActivitat(nomLlarg);
        return "redirect:../listInstructor";
    }
    @RequestMapping(value = "/cancela/{nomLlarg}")
    public String cancelaActivitat(@PathVariable String nomLlarg) {
        activitatDao.cancelaActivitat(nomLlarg);
        return "redirect:../listInstructor";
    }
    @RequestMapping(value = "/imatges/{id}")
    public String mostrarImatge(Model model,@PathVariable String id) {
        model.addAttribute("activitat", activitatDao.getImatge(id));
        return "activitat/list";
    }

}
