package com.example.ei1027.controller;

import com.example.ei1027.dao.InstructorDao;
import com.example.ei1027.model.Instructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;


@Controller
@RequestMapping("/instructor")
public class InstructorController {

    @Autowired
    private InstructorDao instructorDao;

    @GetMapping("/list")
    public String listInstructors(Model model) {
        model.addAttribute("instructors", instructorDao.getInstructors());
        return "instructor/list";
    }


    @GetMapping(value = "/list/{instructorId}")
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
        //ClientValidator clientValidator = new ClientValidator();
        //clientValidator.validate(instructor, bindingResult);
        if (bindingResult.hasErrors())
            return "instructor/add";
        instructorDao.addInstructor(instructor);
        return "redirect:list";
    }

    @GetMapping(value = "/update/{instructorId}")
    public String update(Model model, @PathVariable String instructorId) {
        model.addAttribute("instructor", instructorDao.getInstructor(instructorId));
        return "instructor/update";
    }

    @PostMapping(value = "/update")
    public String update(@ModelAttribute("instructor") Instructor instructor,
                         BindingResult bindingResult) {
        if (bindingResult.hasErrors())
            return "instructor/update";
        instructorDao.updateInstructor(instructor);
        return "redirect:list";
    }

    @RequestMapping(value = "/delete/{instructorId}")
    public String delete(@PathVariable String instructorId) {
        instructorDao.deleteInstructor(instructorId);
        return "redirect:../list";
    }

    @RequestMapping(value = "/accept/{instructorId}")
    public String accept(@PathVariable String instructorId) {
        instructorDao.aceptarSolicitud(instructorId);
        return "redirect:../list";
    }

    @RequestMapping(value = "/decline/{instructorId}")
    public String decline(@PathVariable String instructorId) {
        instructorDao.rebutjarSolicitud(instructorId);
        return "redirect:../list";
    }

    @RequestMapping(value = "/recover/{instructorId}")
    public String recover(@PathVariable String instructorId) {
        instructorDao.recuperarSolicitud(instructorId);
        return "redirect:../list";
    }


}
