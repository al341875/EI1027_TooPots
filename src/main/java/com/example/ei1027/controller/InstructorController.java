package com.example.ei1027.controller;

import com.example.ei1027.config.EncryptorFactory;
import com.example.ei1027.dao.InstructorDao;
import com.example.ei1027.dao.UserDao;
import com.example.ei1027.email.EmailService;
import com.example.ei1027.email.EmailTemplates;
import com.example.ei1027.model.Estat;
import com.example.ei1027.model.Instructor;
import com.example.ei1027.model.UserDetails;
import com.example.ei1027.validation.InstructorValidator;
import com.example.ei1027.validation.excepcions.ClientException;
import com.example.ei1027.validation.excepcions.InstructorException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.mail.MessagingException;


@Controller
@RequestMapping("/instructor")
public class InstructorController {

    @Autowired
    private InstructorDao instructorDao;

    @Autowired
    private EmailService emailService;

    @Autowired
    private EncryptorFactory encryptorFactory;
    
	@Value("${upload.file.directory}")
    private String uploadDirectory;


    @Autowired
    private UserDao userDao;

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
    public String addInstructor(@ModelAttribute("instructor") Instructor instructor, BindingResult bindingResult,@RequestParam("file") MultipartFile file,
            RedirectAttributes redirectAttributes) throws MessagingException {
        InstructorValidator instructorValidator = new InstructorValidator();
        instructorValidator.validate(instructor, bindingResult);
        if (instructorDao.existId(instructor.getInstructorId()))
        	throw new InstructorException("DNI duplicat","ClauPrimariaDuplicada");            
        if (instructorDao.existEmail(instructor.getEmail()))
        	throw new InstructorException("Email duplicat","ClauPrimariaDuplicada");
        if (instructorDao.existIban(instructor.getIban()))
        	throw new InstructorException("IBAN duplicat","ClauPrimariaDuplicada");
        UserDetails user = new UserDetails();
        user.setUsuari(instructor.getInstructorId());
        user.setTipus("instructor");
        user.setContrasenya(instructor.getContrasenya());
        userDao.add(user);  
        if (bindingResult.hasErrors())
            return "instructor/add";
        
        
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
        instructor.setImatge(file.getOriginalFilename());

        instructorDao.addInstructor(instructor);

      //  emailService.sendSimpleMessage(instructor.getEmail(), EmailTemplates.SOLICITUD_ENVIADA.subject(), EmailTemplates.SOLICITUD_ENVIADA.fileName());
        return "instructor/despRegistrar";
    }
    @GetMapping( "/despRegistrar")
    public String update(Model model) {
        return "instructor/despRegistrar";
    }
    @GetMapping(value = "/update/{instructorId}")
    public String update(Model model, @PathVariable String instructorId) {
        model.addAttribute("instructor", instructorDao.getInstructor(instructorId));
        return "instructor/update";
    }

    @PostMapping(value = "/update")
    public String update(@ModelAttribute("instructor") Instructor instructor,
                         BindingResult bindingResult,@RequestParam("file") MultipartFile file,
				            RedirectAttributes redirectAttributes) {
    	InstructorValidator instructorValidator = new InstructorValidator();
    	instructorValidator.validate(instructor, bindingResult);
//        if (instructorDao.existId(instructor.getInstructorId()))
//            //Accion si ya existe un instructor con dicho id
//        if (instructorDao.existEmail(instructor.getEmail()))
//            //Accion si ya existe el email en un instructor
//        if (instructorDao.existIban(instructor.getIban()))
//            //Accion si ya existe el iban en un instructor
        if (bindingResult.hasErrors())
            return "instructor/update";
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
        instructor.setImatge(file.getOriginalFilename());
        instructorDao.updateInstructor(instructor);
        return "redirect:acceptats";
    }
    @GetMapping(value = "/show/{instructorId}")
    public String showClient(Model model,@PathVariable String instructorId) {
        model.addAttribute("instructor", instructorDao.getInstructor(instructorId));
        return "instructor/show";
    }
    @RequestMapping(value = "/delete/{instructorId}")
    public String delete(@PathVariable String instructorId) {
        instructorDao.deleteInstructor(instructorId);
        //Hi ha que vore on redirigix
        return "redirect:../pendents";
    }

    @RequestMapping(value = "/accept/{instructorId}")
    public String accept(@PathVariable String instructorId) throws MessagingException {
        instructorDao.aceptarSolicitud(instructorId);
        emailService.sendSimpleMessage(instructorDao.getEmail(instructorId), EmailTemplates.SOLICITUD_ACCEPTADA.subject(), EmailTemplates.SOLICITUD_ACCEPTADA.fileName());
        return "redirect:../pendents";
    }

    @RequestMapping(value = "/decline/{instructorId}")
    public String decline(@PathVariable String instructorId) throws MessagingException {
        instructorDao.rebutjarSolicitud(instructorId);
        emailService.sendSimpleMessage(instructorDao.getEmail(instructorId), EmailTemplates.SOLICITUD_REBUTJADA.subject(), EmailTemplates.SOLICITUD_REBUTJADA.fileName());
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
    
    @RequestMapping(value = "/imatges/{id}")
    public String mostrarImatge(Model model,@PathVariable String id) {
        model.addAttribute("instructor", instructorDao.getImatge(id));
        return "instructor/list";
    }

}
