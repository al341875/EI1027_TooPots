package com.example.ei1027.controller;

import com.example.ei1027.dao.NivellDao;
import com.example.ei1027.model.Nivell;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/nivells")
public class NivellControler {

    @Autowired
    private NivellDao nivellDao;

    @GetMapping("/list")
    public String listNivells(Model model) {
        model.addAttribute("nivells", nivellDao.findAll());
        return "nivell/list";
    }
    @GetMapping(value="/list/{nivellId}")
    public String getNivell(Model model, @PathVariable Short nivellId) {
        model.addAttribute("nivell", nivellDao.findOne(nivellId));
        return "nivell/list";
    }
    @GetMapping(value = "/add")
    public String addNivell(Model model) {
        model.addAttribute("nivell", new Nivell());
        return "nivell/add";
    }
    @PostMapping(value = "/add")
    public String addNivell(@ModelAttribute("nivell") Nivell nivell, BindingResult bindingResult) {
        if (bindingResult.hasErrors())
            return "nivell/add";
        nivellDao.add(nivell);
        return "redirect:list";
    }
    @GetMapping(value="/update/{nivellId}")
    public String update(Model model, @PathVariable Short nivellId) {
        model.addAttribute("nivell", nivellDao.findOne(nivellId));
        return "nivell/update";
    }
    @PostMapping(value="/update")
    public String update(@ModelAttribute("nivell") Nivell nivell,
                         BindingResult bindingResult) {
        if (bindingResult.hasErrors())
            return "nivell/update";
        nivellDao.update(nivell);
        return "redirect:list";
    }
    @RequestMapping(value = "/delete/{nivellId}")
    public String delete(@PathVariable Short nivellId) {
        nivellDao.delete(nivellId);
        return "redirect:../list";
    }

}
