package com.example.ei1027.controller;

import com.example.ei1027.dao.ReservaDao;
import com.example.ei1027.dao.ActivitatDao;
import com.example.ei1027.dao.UserDao;
import com.example.ei1027.model.*;
import com.example.ei1027.validation.UserValidator;
import com.example.ei1027.validation.excepcions.UserException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequestMapping("/reserva")
public class ReservaController {

    @Autowired
    private ReservaDao reservaDao;

    @Autowired
    private ActivitatDao activitatDao;

    @Autowired
    private UserDao userDao;

    @GetMapping("/list")
    public String listReserva(Model model) {
        model.addAttribute("reserves", reservaDao.getReservas());
        return "reserva/list";
    }


    @GetMapping("/pendents")
    public String listReservesPendents(Model model,HttpSession session) {

        String user = (String)  session.getAttribute("username");
        if ( user == null) {
            throw new UserException("Usuari no valid","usuariNoValid");
        }
        model.addAttribute("reserves", reservaDao.getReservaByUsuariStatus(EstatPagament.PENDENT.toString(),user));
        model.addAttribute("boldPendent", true);
        model.addAttribute("tabs", true);
        return "reserva/listClient";
    }
    @GetMapping("/pagades")
    public String listReservesPagat(Model model,HttpSession session) {
        String user = (String)  session.getAttribute("username");
        if ( user == null) {
            throw new UserException("Usuari no valid","usuariNoValid");
        }
        model.addAttribute("reserves", reservaDao.getReservaByUsuariStatus(EstatPagament.PAGAT.toString(),user));
        model.addAttribute("boldPendent", true);
        model.addAttribute("tabs", true);
        return "reserva/listClient";
    }

    @GetMapping("/acceptades")
    public String listReservesAcceptats(Model model,HttpSession session) {
        String user = (String)  session.getAttribute("username");
        if ( user == null) {
            throw new UserException("Usuari no valid","usuariNoValid");
        }
        model.addAttribute("reserves", reservaDao.getReservaByUsuariStatus(EstatPagament.ACCEPTADA.toString(),user));
        model.addAttribute("boldAcceptat", true);
        model.addAttribute("tabs", true);
        return "reserva/listClient";
    }
    @GetMapping("/confirmades")
    public String listReservesConfirmades(Model model,HttpSession session) {
        String user = (String)  session.getAttribute("username");
        if ( user == null) {
            throw new UserException("Usuari no valid","usuariNoValid");
        }
        model.addAttribute("reserves", reservaDao.getReservaByUsuariStatus(EstatPagament.CONFIRMAT.toString(),user));
        model.addAttribute("boldPendent", true);
        model.addAttribute("tabs", true);
        return "reserva/listClient";
    }
    @GetMapping("/acceptarReservesInstructor")
    public String listReservesByInstructor(Model model,HttpSession session) {
        String user = (String)  session.getAttribute("username");
        if ( user == null) {
            throw new UserException("Usuari no valid","usuariNoValid");
        }
        model.addAttribute("reserves", reservaDao.reservesByInstructor(user));
        return "reserva/list";
    }
        @GetMapping("/listClient")
    public String listReservesUsuari(Model model ,HttpSession session)  {
        String user = (String)  session.getAttribute("username");
            if ( user == null) {
                throw new UserException("Usuari no valid","usuariNoValid");
            }
            model.addAttribute("reserves", reservaDao.getReservaByUsuari(user));

        return "reserva/listClient";
    }

    @GetMapping(value="/list2/{idReserva}")
    public String getReserva(Model model, @PathVariable Integer idReserva) {
        model.addAttribute("reserves", reservaDao.getReserva(idReserva));
        return "reserva/list";
    }


    @GetMapping(value="/list/{NomActivitat}")
    public String getReservesByActivitats(Model model, @PathVariable String NomActivitat) {
        model.addAttribute("reserves", reservaDao.getReservaByActivitat(NomActivitat));
        return "reserva/list";
    }
    @GetMapping("/listInstructor/{nomActivitat}")
    public String getReservesByInstructor(Model model,@PathVariable String nomActivitat) {
        model.addAttribute("reserves", reservaDao.getReservaByActivitat(nomActivitat));
        return "reserva/list";
    }
    @GetMapping(value = "/add/{nomActivitat}")
    public String addReserva(Model model,@PathVariable String nomActivitat) {
        Reserva reserva = new Reserva();
        reserva.setNomActivitat(nomActivitat);
        model.addAttribute("reserva",  reserva);
        model.addAttribute("puestosLliures", reservaDao.getPuestosLliures(nomActivitat));
        return "reserva/add";
    }
    @PostMapping(value = "/add")
    public String addReserva(Model model,@ModelAttribute("reserva") Reserva reserva,HttpSession session , BindingResult bindingResult) {
    	//model.addAttribute("puestosLliures", reservaDao.getPuestosLliures(reserva.getNomActivitat()));
    	String user = (String)  session.getAttribute("username");
        if ( user == null) {
            throw new UserException("Usuari no valid","usuariNoValid");
        }
        if (bindingResult.hasErrors())
            return "reserva/add";


        Activitat activitat = activitatDao.getActivitat(reserva.getNomActivitat());
        if(activitat.getEstat().equals("oberta") == false){
            bindingResult.rejectValue("estat", "obligatori", "Actividad no disponible");
            return "reserva/add";
        }
        if( reserva.getNumAssistents() > activitat.getMaxAssistents() ){
            bindingResult.rejectValue("numAssistents", "obligatori", "No hi han places disponibles");
            return "reserva/add";
        }

        reserva.setDataActivitat(activitat.getData());
        reserva.setIdClient(user);
        reserva.setPreuPersona((double) activitat.getPreu());
        reserva.setEstatPagament("pendent");
        reservaDao.addReserva(reserva);
        return "redirect:pendents";
    }

    @GetMapping(value="/update/{idReserva}")
    public String update(Model model, @PathVariable Integer idReserva) {
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
    public String accept(@PathVariable Integer idReserva) {
        reservaDao.aceptarSolicitud(idReserva);
        return "redirect:../acceptarReservesInstructor";
    }




    @GetMapping(value="/pagament/{idReserva}")
    public String pagament(Model model, @PathVariable Integer idReserva) {
        model.addAttribute("reserva",reservaDao.getReserva(idReserva) );
        return "reserva/pagament";
    }
    @PostMapping(value="/pagament")
    public String pagament(@ModelAttribute("reserva") Reserva reserva,
                         BindingResult bindingResult) {
        if (bindingResult.hasErrors())
            return "reserva/pendents";
        System.out.println("procesant solicitud");
        reservaDao.aceptarPagament(reserva.getIdReserva());
        System.out.println("Solicitud de pagament acceptada");

        return "redirect:pagades";
    }
    @RequestMapping(value="/confirma/{idReserva}")
    public String confirma(Model model, @PathVariable Integer idReserva) {
        reservaDao.confirmaReserva(idReserva);

        return "redirect:../confirmades";
    }
    @RequestMapping(value = "/delete/{idReserva}")
    public String delete(@PathVariable Integer idReserva) {
        reservaDao.deleteReserva(idReserva);

        return "redirect:../pendents";
    }
    @RequestMapping(value="/show/{idReserva}")
    public String show(Model model, @PathVariable Integer idReserva) {
        model.addAttribute("reserva",reservaDao.getReserva(idReserva) );
        return "reserva/show";
    }


}


