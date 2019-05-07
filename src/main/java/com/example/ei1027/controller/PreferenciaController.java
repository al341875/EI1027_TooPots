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

    @GetMapping("/my-preferences")
    public String listMyPreferences(Model model) {
        //Obtener el Id del cliente que está loggeado
        model.addAttribute("preferencias", preferenciaDao.findByClient("ID_CLIENTE_LOGGEADO"));
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

    @RequestMapping(value = "/delete/{nom_tipus_activitat}")
    public String delete(@PathVariable String nomTipusActivitat) {
        Preferencia pref = new Preferencia();
        //OBTENER ID DEL CLIENTE
        pref.setClientId("CLIENT_ID");
        pref.setNomTipusActivitat(nomTipusActivitat);
        preferenciaDao.delete(pref);
        return "redirect:../list";
    }
}
