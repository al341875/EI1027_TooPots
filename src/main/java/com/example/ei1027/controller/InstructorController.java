package com.example.ei1027.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.ei1027.dao.InstructorDao;
import com.example.ei1027.message.EmailService;
import com.example.ei1027.model.Estat;
import com.example.ei1027.model.Instructor;
import com.example.ei1027.validation.InstructorValidator;
import com.example.ei1027.validation.excepcions.ClientException;


@Controller
@RequestMapping("/instructor")
public class InstructorController {

    @Autowired
    private InstructorDao instructorDao;

    @Autowired
    private EmailService emailService;

    @GetMapping("/pendents")
    public String listInstructorsPendents(Model model) {
        model.addAttribute("instructors", instructorDao.getInstructorsByStatus(Estat.PENDENT.toString()));
        model.addAttribute("boldPendent", true);
        model.addAttribute("tabs", true);
        return "instructor/list";
    }


    @GetMapping("/acceptats")
    public String listInstructorsAcceptats(Model model) {
        model.addAttribute("instructors", instructorDao.getInstructorsByStatus(Estat.ACCEPTADA.toString()));
        model.addAttribute("boldAcceptat", true);
        model.addAttribute("tabs", true);
        return "instructor/list";
    }

    @GetMapping("/rebutjats")
    public String listInstructorsRebutjats(Model model) {
        model.addAttribute("instructors", instructorDao.getInstructorsByStatus(Estat.REBUTJADA.toString()));
        model.addAttribute("boldRebutjat", true);
        model.addAttribute("tabs", true);
        return "instructor/list";
    }

    @GetMapping(value = "/{instructorId}")
    public String getInstructor(Model model, @PathVariable String instructorId) {
        model.addAttribute("instructor", instructorDao.getInstructor(instructorId));
        return "instructor/list";
    }

    @GetMapping(value = "/add")
    public String addInstructor(Model model) {
        model.addAttribute("instructor", new Instructor());
        return "instructor/add";
    }

    @PostMapping(value = "/add")
    public String addInstructor(@ModelAttribute("instructor") Instructor instructor, BindingResult bindingResult) {
    	InstructorValidator instructorValidator = new InstructorValidator();
    	instructorValidator.validate(instructor, bindingResult);
        if (bindingResult.hasErrors())
            return "instructor/add";
        try {
        	instructorDao.addInstructor(instructor);
        }catch(DuplicateKeyException e) {
        	throw new ClientException("DNI o camp unic(iban, email) duplicat","ClauPrimariaDuplicada");
        }
        return "redirect:pendents";
    }

    @GetMapping(value = "/update/{instructorId}")
    public String update(Model model, @PathVariable String instructorId) {
        model.addAttribute("instructor", instructorDao.getInstructor(instructorId));
        return "instructor/update";
    }

    @PostMapping(value = "/update")
    public String update(@ModelAttribute("instructor") Instructor instructor,
                         BindingResult bindingResult) {
    	InstructorValidator instructorValidator = new InstructorValidator();
    	instructorValidator.validate(instructor, bindingResult);
        if (bindingResult.hasErrors())
            return "instructor/update";
        instructorDao.updateInstructor(instructor);
        return "redirect:acceptats";
    }

    @RequestMapping(value = "/delete/{instructorId}")
    public String delete(@PathVariable String instructorId) {
        instructorDao.deleteInstructor(instructorId);
        //Hi ha que vore on redirigix
        return "redirect:../pendents";
    }

    @RequestMapping(value = "/accept/{instructorId}")
    public String accept(@PathVariable String instructorId) {
        instructorDao.aceptarSolicitud(instructorId);
        return "redirect:../pendents";
    }

    @RequestMapping(value = "/decline/{instructorId}")
    public String decline(@PathVariable String instructorId) {
        instructorDao.rebutjarSolicitud(instructorId);
        emailService.sendSimpleMessage(instructorDao.getEmail(instructorId),"PRUEBA", "Este es un correo de prueba diciendote que tu solicitud ha sido rechazada");
        return "redirect:../pendents";
    }

    @RequestMapping(value = "/recover/{instructorId}")
    public String recover(@PathVariable String instructorId) {
        instructorDao.recuperarSolicitud(instructorId);
        return "redirect:../rebutjats";
    }

    @RequestMapping(value = "/{instructorId}/activitats")
    public String getActivitatsInstructor(Model model, @PathVariable String instructorId){
        model.addAttribute("activitats", instructorDao.findActivitiesByInstructorId(instructorId));
        return "instructor/activitats";
    }

}
