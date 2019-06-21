package com.example.ei1027.controller;

import com.example.ei1027.dao.ActivitatDao;
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
public class LoginController {
	@Autowired
	private UserDao userDao;

    @Autowired
    private ActivitatDao activitatDao;

	@RequestMapping("/home")
	public String login(Model model) {
		model.addAttribute("user", new UserDetails());
        model.addAttribute("activitats", activitatDao.getActivitats());
        return "home/main";
	}

	@RequestMapping(value="/login", method=RequestMethod.POST)
	public String checkLogin(@ModelAttribute("user") UserDetails userData,
							 BindingResult bindingResult, HttpSession session) {
		UserValidator userValidator = new UserValidator();
		userValidator.validate(userData, bindingResult);
		UserDetails user = userDao.find(userData);

		if (bindingResult.hasErrors() || user == null) {
			return "home/main";
		}
		//System.out.println("el usuario es :"+ user.getClave());
	       // Comprova que el login siga correcte
		// intentant carregar les dades de l'usuari 
		session.setAttribute("user", user);
//		session.setAttribute("username",user.getUsuari());
		if (user.getTipus().equals("client"))
			return "redirect:home";
		else if (user.getTipus().equals("admin"))
			return "redirect:instructor/pendents";
		return "redirect:home";

//			if(user.getTipus().equals("client")) {session.setAttribute("home", "home/client");
//			return "redirect:/home/client";
//			}
//			if(user.getTipus().equals("monitor")) {session.setAttribute("home", "home/monitor");
//			return "redirect:/home/monitor";
//			}else{
//				session.setAttribute("home", "home/admin");
//				return "redirect:/home/admin";
//
//			}
	}

	@RequestMapping("/logout") 
	public String logout(HttpSession session) {
		session.invalidate();
		return "redirect:home?logout=true";
	}
//	@RequestMapping("/home")
//	public String home(HttpSession session) {
//		String tipus = (String) session.getAttribute("type");
//
//		if (tipus.compareTo("monitor") == 0) {
//			session.setAttribute("home", "/home/monitor");
//			return "/home/monitor";
//		} else {
//			if (tipus.compareTo("client") == 0) {
//				session.setAttribute("home", "home/client");
//				return "home/client";
//			} else {
//					session.setAttribute("home", "/home/admin");
//					return "home/admin";
//
//				}
//			}
//		}
	}

	

