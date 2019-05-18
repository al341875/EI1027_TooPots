package com.example.ei1027.controller;

import com.example.ei1027.dao.ReservaDao;
import com.example.ei1027.model.Reserva;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

public class ReservaController {

    @Autowired
    private ReservaDao reservaDao;

    @GetMapping("/list")
    public String listReserva(Model model) {
        model.addAttribute("reserva", reservaDao.getReservas());

        return "reserva/list";
    }
    @GetMapping(value="/list/{idReserva}")
    public String getReserva(Model model, @PathVariable String idReserva) {
        model.addAttribute("reserva", reservaDao.getReserva(idReserva));
        return "reserva/list";
    }
    @GetMapping(value = "/add")
    public String addReserva(Model model) {
        model.addAttribute("reserva", new Reserva());
        return "reserva/add";
    }
    @PostMapping(value = "/add")
    public String addReserva(@ModelAttribute("reserva") Reserva reserva, BindingResult bindingResult) {
        if (bindingResult.hasErrors())
            return "reserva/add";
        reservaDao.addReserva(reserva);
        return "redirect:list";
    }
    @GetMapping(value="/update/{idReserva}")
    public String update(Model model, @PathVariable String idReserva) {
        model.addAttribute("reserva",reservaDao.getReserva(idReserva) );
        return "reserva/update";
    }
    @PostMapping(value="/update")
    public String update(@ModelAttribute("reserva") Reserva reserva,
                         BindingResult bindingResult) {
        if (bindingResult.hasErrors())
            return "client/update";
        reservaDao.updateReserva(reserva);
        return "redirect:list";
    }
    @RequestMapping(value = "/delete/{idReserva}")
    public String delete(@PathVariable String idReserva) {
        reservaDao.deleteReserva(idReserva);

        return "redirect:../list";
    }

}


