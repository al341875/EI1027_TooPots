package com.example.ei1027.controller;

import com.example.ei1027.dao.ActivitatDao;
import com.example.ei1027.dao.TipusActivitatDao;
import com.example.ei1027.model.Activitat;
import org.springframework.beans.factory.annotation.Autowired;
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
    @GetMapping(value="/list/{nomLlarg}")
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
    public String addActivitat(@ModelAttribute("activitat") Activitat activitat, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            System.out.println(bindingResult);
            return "activitat/add";
        }
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
        return "redirect:list";
    }
    @RequestMapping(value = "/delete/{nomLlarg}")
    public String delete(@PathVariable String nomLlarg) {
        activitatDao.deleteActivitat(nomLlarg);
        return "redirect:../list";
    }

}

