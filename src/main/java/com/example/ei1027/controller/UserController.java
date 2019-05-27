package com.example.ei1027.controller;

import com.example.ei1027.dao.UserDao;
import com.example.ei1027.model.UserDetails;
import com.example.ei1027.validation.UserValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpSession;


@Controller
@RequestMapping("/user") 
public class UserController {
    @Autowired
    private UserDao userDao;

    @RequestMapping("/login")
    public String login(Model model) {
        //model.addAttribute("user", new UserDetails());
        //Logger.getAnonymousLogger().log(Level.INFO, "He llegado hasta aqui");
        return "home/login";
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public String checkLogin(@ModelAttribute("user") UserDetails user,
                             BindingResult bindingResult, HttpSession session) {
        UserValidator userValidator = new UserValidator();
        userValidator.validate(user, bindingResult);
        if (bindingResult.hasErrors()) {
            return "login";
        }
        // Autenticats correctament.
        // Guardem les dades de l'usuari autenticat a la sessió
        session.setAttribute("user", user);

        // Torna a la pàgina principal
        return "redirect:/";
    }

    @RequestMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/";
    }

}
