package com.example.ei1027.controller;

import com.example.ei1027.dao.PreferenciaDao;
import com.example.ei1027.model.Preferencia;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/preferencias")
public class PreferenciaController {

    @Autowired
    private PreferenciaDao preferenciaDao;

    @GetMapping("/list")
    public String listPreferencias(Model model) {
        model.addAttribute("preferencias", preferenciaDao.findAll());
        return "";
    }
    @GetMapping(value="/preferencia/{preferenciaId}")
    public String getPreferencia(Model model, @PathVariable Short preferenciaId) {
        model.addAttribute("preferencia", preferenciaDao.findOne(preferenciaId));
        return "";
    }
    @GetMapping(value = "/add")
    public String addPreferencia(Model model) {
        model.addAttribute("preferencia", new Preferencia());
        return "preferencia/add";
    }
    @PostMapping(value = "/add")
    public String addPreferencia(@ModelAttribute("preferencia") Preferencia preferencia, BindingResult bindingResult) {
        if (bindingResult.hasErrors())
            return "preferencia/add";
        preferenciaDao.add(preferencia);
        return "redirect:list";
    }
    @GetMapping(value="/update/{preferenciaId}")
    public String update(Model model, @PathVariable Short preferenciaId) {
        model.addAttribute("preferencia", preferenciaDao.findOne(preferenciaId));
        return "preferencia/update";
    }
    @PostMapping(value="/update")
    public String update(@ModelAttribute("preferencia") Preferencia preferencia,
                         BindingResult bindingResult) {
        if (bindingResult.hasErrors())
            return "preferencia/update";
        preferenciaDao.update(preferencia);
        return "redirect:list";
    }
    @RequestMapping(value = "/delete/{preferenciaId}")
    public String delete(@PathVariable Short preferenciaId) {
        preferenciaDao.delete(preferenciaId);
        return "redirect:../list";
    }
}
