package com.example.ei1027.controller;

import com.example.ei1027.dao.ActivitatDao;
import com.example.ei1027.dao.TipusActivitatDao;
import com.example.ei1027.model.Activitat;
import com.example.ei1027.validation.ActivitatValidator;
import com.example.ei1027.validation.excepcions.ActivitatException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/activitat")
public class ActivitatController {

	@Autowired
	private ActivitatDao activitatDao;

	@Autowired
	private TipusActivitatDao tipusActivitatDao;

	@GetMapping("/list")
	public String listActivitats(Model model) {
		model.addAttribute("activitats", activitatDao.getActivitats());
		return "activitat/list";
	}
	@GetMapping("/listInstructor")
	public String listActivitatsInstructor(Model model,@SessionAttribute("username") String user) {
		model.addAttribute("activitats", activitatDao.getActivitatsByInstructor(user));
		return "activitat/listInstructor";
	}
	@GetMapping(value = "/list/{nomLlarg}")
	public String getActivitat(Model model, @PathVariable String nomLlarg) {
		model.addAttribute("activitat", activitatDao.getActivitat(nomLlarg));
		return "activitat/list";
	}

	@GetMapping(value = "/add")
	public String addActivitat(Model model) {
		model.addAttribute("activitat", new Activitat());
		model.addAttribute("tipusActivitats", tipusActivitatDao.getTipusActivitats());
		return "activitat/add";
	}

	@PostMapping(value = "/add")
    public String addActivitat(@ModelAttribute("activitat") Activitat activitat, BindingResult bindingResult,@SessionAttribute("username") String user) {
        if (bindingResult.hasErrors()) {
            System.out.println(bindingResult);
            return "activitat/add";
        }	//
		activitat.setIdInstructor(user);
        activitatDao.addActivitat(activitat);
        return "redirect:list";
    }
    @GetMapping(value="/update/{nomLlarg}")
    public String update(Model model, @PathVariable String nomLlarg) {
        model.addAttribute("activitat", activitatDao.getActivitat(nomLlarg));
        return "activitat/update";
    }
    @PostMapping(value="/update")
    public String update(@ModelAttribute("activitat") Activitat activitat,
                         BindingResult bindingResult) {
        if (bindingResult.hasErrors())
            return "activitat/update";
        activitatDao.updateActivitat(activitat);
    	 ActivitatValidator activitatValidator = new ActivitatValidator();
    	 activitatValidator.validate(activitat, bindingResult);
    	 
    	 //if( !(activitatDao.existIdInstructor(activitat.getIdInstructor())) )
    		 //No existe un instructor con este id
    			 
    			 
    	if (bindingResult.hasErrors()) {
            System.out.println(bindingResult);
            return "activitat/add";
        }
    	try {
    		activitatDao.addActivitat(activitat);
    	}catch(DuplicateKeyException e) {
    		throw new ActivitatException("Ja existeix una activitat amb el nom: "+activitat.getNomLlarg(),"ClauPrimariaDuplicada");
    	}
        return "redirect:list";
    }


	@RequestMapping(value = "/delete/{nomLlarg}")
	public String delete(@PathVariable String nomLlarg) {
		activitatDao.deleteActivitat(nomLlarg);
		return "redirect:../list";
	}

}
