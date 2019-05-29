package com.example.ei1027.controller;

import com.example.ei1027.dao.ReservaDao;
import com.example.ei1027.dao.ActivitatDao;
import com.example.ei1027.model.Activitat;
import com.example.ei1027.model.EstatActivitat;
import com.example.ei1027.model.EstatPagament;
import com.example.ei1027.model.Reserva;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/reserva")
public class ReservaController {

    @Autowired
    private ReservaDao reservaDao;

    @Autowired
    private ActivitatDao activitatDao;

    @GetMapping("/list")
    public String listReserva(Model model) {
        model.addAttribute("reserves", reservaDao.getReservas());
        return "reserva/list";
    }
    @GetMapping("/listInstructor")
    public String listReserva2(Model model) {
        model.addAttribute("reserves", reservaDao.getReservas());
        return "reserva/listInstructor";
    }
    @GetMapping("/pendents")
    public String listReservesPendents(Model model) {
        model.addAttribute("reserves", reservaDao.getReservaByStatus(EstatPagament.PENDENT.toString()));
        model.addAttribute("boldPendent", true);
        model.addAttribute("tabs", true);
        return "reserva/listInstructor";
    }


    @GetMapping("/acceptats")
    public String listReservesAcceptats(Model model) {
        model.addAttribute("reserves", reservaDao.getReservaByStatus(EstatPagament.ACCEPTADA.toString()));
        model.addAttribute("boldAcceptat", true);
        model.addAttribute("tabs", true);
        return "reserva/listInstructor";
    }
    @GetMapping("/listUsuaris")
    public String listReservesPendents(Model model, @SessionAttribute("user") String usuario) {
        model.addAttribute("pendents", reservaDao.getReservaByStatus(EstatPagament.PENDENT.toString()));
        return "reserva/list";
    }

    @GetMapping("/listInstructor/{nomActivitat}")
    public String listReservesActivitat(Model model, @SessionAttribute("user") String usuario,@PathVariable String nomActivitat ) {
        model.addAttribute("reserves", reservaDao.getReservaByActivitat(nomActivitat));

        return "reserva/list";
    }
    @GetMapping("/listUsuari")
    public String listReservesUsuari(Model model, @SessionAttribute("user") String usuario ) {
        model.addAttribute("instructors", reservaDao.getReservaByStatus(EstatPagament.PENDENT.toString()));

        return "reserva/list";
    }

    @GetMapping(value="/list/{idReserva}")
    public String getReserva(Model model, @PathVariable String idReserva) {
        model.addAttribute("reserva", reservaDao.getReserva(idReserva));
        return "reserva/list";
    }

    @GetMapping(value="/list/{idUsuari}")
    public String getReservesUser(Model model, @PathVariable String idUsuari) {
        model.addAttribute("reservaUser", reservaDao.getReservaByUsuari(idUsuari));
        return "reserva/list";
    }
    @GetMapping(value="/list/{NomActivitat}")
    public String getReservesByActivitats(Model model, @PathVariable String NomActivitat) {
        model.addAttribute("reservaActivitat", reservaDao.getReservaByActivitat(NomActivitat));
        return "reserva/list";
    }
    @GetMapping(value = "/add/{nomActivitat}")
    public String addReserva(Model model,@PathVariable String nomActivitat) {
        Reserva reserva = new Reserva();
        reserva.setNomActivitat(nomActivitat);
        model.addAttribute("reserva",  reserva);

        return "reserva/add";
    }
    @PostMapping(value = "/add")
    public String addReserva(@ModelAttribute("reserva") Reserva reserva , BindingResult bindingResult) {
        if (bindingResult.hasErrors())
            return "reserva/add";


        Activitat activitat = activitatDao.getActivitat(reserva.getNomActivitat());
        if(activitat.getEstat() != "oberta"){
            bindingResult.rejectValue("estat", "obligatori", "Actividad no disponible");
            return "reserva/add";
        }
        if( reserva.getNumAssistents() > activitat.getMaxAssistents() ){
            bindingResult.rejectValue("numAssistents", "obligatori", "No hi han places disponibles");
            return "reserva/add";
        }
        reserva.setDataActivitat(activitat.getData());
        reserva.setPreuPersona((double) activitat.getPreu());
        reserva.setEstatPagament("pendent");
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
            return "reserva/update";
        reservaDao.updateReserva(reserva);
        return "redirect:list";
    }
    @RequestMapping(value = "/accept/{idReserva}")
    public String accept(@PathVariable String idReserva) {
        reservaDao.aceptarSolicitud(idReserva);
        return "redirect:../pendents";
    }
    @GetMapping(value="/pagar/{idReserva}")
    public String pagarReserva(Model model, @PathVariable String idReserva) {
        model.addAttribute("reserva",reservaDao.getReserva(idReserva) );
        return "reserva/update";
    }
    @PostMapping(value="/pagar/{idReserva}")
    public String pagarReserva(@ModelAttribute("reserva") Reserva reserva, @PathVariable String idReserva,
                         BindingResult bindingResult) {

        if (bindingResult.hasErrors())
            return "reserva/update";
        reservaDao.updateReserva(reserva);
        return "redirect:list";
    }
    @RequestMapping(value = "/delete/{idReserva}")
    public String delete(@PathVariable String idReserva) {
        reservaDao.deleteReserva(idReserva);

        return "redirect:../list";
    }

}


