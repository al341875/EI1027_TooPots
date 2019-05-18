package com.example.ei1027.controller;

import com.example.ei1027.dao.TipusActivitatDao;
import com.example.ei1027.model.TipusActivitat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/tipusActivitat")
public class TipusActivitatController {

    @Autowired
    private TipusActivitatDao tipusActivitatDao;

    @GetMapping("/list")
    public String listTipusActitat(Model model) {
        model.addAttribute("tipusActivitats", tipusActivitatDao.getTipusActivitats());
        return "tipusActivitat/list";
    }
    @GetMapping(value="/list/{nomTipusActivtat}")
    public String getTipusActivitat(Model model, @PathVariable String nomTipusActivtat) {
        model.addAttribute("tipusActivitat", tipusActivitatDao.getTipusActivitat(nomTipusActivtat));
        return "tipusActivitat/list";
    }
    @GetMapping(value = "/add")
    public String addNivell(Model model) {
        model.addAttribute("tipusActivitat", new TipusActivitat());
        return "tipusActivitat/add";
    }
    @PostMapping(value = "/add")
    public String addTipusActivitat(@ModelAttribute("tipusActivitat") TipusActivitat tipusActivitat, BindingResult bindingResult) {
        if (bindingResult.hasErrors())
            return "tipusActivitat/add";
        tipusActivitatDao.addTipusActivitat(tipusActivitat);
        return "redirect:list";
    }
    @GetMapping(value="/update/{nomTipusActivitat}")
    public String update(Model model, @PathVariable String nomTipusActivitat) {
        model.addAttribute("tipusActivitat", tipusActivitatDao.getTipusActivitat(nomTipusActivitat));
        return "tipusActivitat/update";
    }
    @PostMapping(value="/update/{nomTipusActivitat}")
    public String update(@PathVariable String nomTipusActivitat, @ModelAttribute("tipusActivitat") TipusActivitat tipusActivitat,
                         BindingResult bindingResult) {
        if (bindingResult.hasErrors())
            return "tipusActivitat/update";
        tipusActivitat.setNomTipusActivitat(nomTipusActivitat);
        tipusActivitatDao.updateTipusActivitat(tipusActivitat);
        return "redirect:../list";
    }
    @RequestMapping(value = "/delete/{nomTipusActivitat}")
    public String delete(@PathVariable String nomTipusActivitat) {
        tipusActivitatDao.deleteTipusActivitat(nomTipusActivitat);
        return "redirect:../list";
    }

}