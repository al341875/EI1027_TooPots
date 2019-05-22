package com.example.ei1027.controller;

import javax.servlet.http.HttpSession;

import com.example.ei1027.dao.UserDao;
import com.example.ei1027.model.UserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller; 
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult; 
import org.springframework.web.bind.annotation.ModelAttribute; 
import org.springframework.web.bind.annotation.RequestMapping; 
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;



class UserValidator implements Validator { 
	@Override
	public boolean supports(Class<?> cls) { 
		return UserDetails.class.isAssignableFrom(cls);
	}
	@Override 
	public void validate(Object obj, Errors errors) {
	  // Exercici: Afegeix codi per comprovar que 
         // l'usuari i la contrasenya no estiguen buits 
         // ...
		UserDetails user= (UserDetails)obj;
		if (user.getUsername().trim().equals(""))
	       errors.rejectValue("usuari", "obligatori",
	                          "Cal introduir un valor");
		if (user.getPassword().trim().equals(""))
		       errors.rejectValue("contrasenya", "obligatori",
		                          "Cal introduir un valor");
	}
}

@Controller
public class LoginController {
	@Autowired
	private UserDao userDao;

	@RequestMapping("/login")
	public String login(Model model, HttpSession session) {
		model.addAttribute("user", new UserDetails());

		return "login";
	}

	@RequestMapping(value="/login", method=RequestMethod.POST)
	public String checkLogin(@ModelAttribute("user") UserDetails user,
				BindingResult bindingResult, HttpSession session) {
		UserValidator userValidator = new UserValidator();
		userValidator.validate(user, bindingResult);
		if (bindingResult.hasErrors()) {
			return "index";
		}
		//System.out.println("el usuario es :"+ user.getClave());
	       // Comprova que el login siga correcte
		// intentant carregar les dades de l'usuari 
		user = userDao.loadUserByUsername(user.getUsername(),user.getPassword());
		if (user == null) {
			bindingResult.rejectValue("password", "incorrectpass", "Contrasenya incorrecta");
			return "index";
		}

			session.setAttribute("user", user);

			if(user.getTipus().equals("client")) {session.setAttribute("home", "home/client");
			return "redirect:/home/client";
			}
			if(user.getTipus().equals("monitor")) {session.setAttribute("home", "home/monitor");
			return "redirect:/home/monitor";
			}else{
				session.setAttribute("home", "home/admin");

			}
		return "redirect:/home/admin";


		
		/*if(session.getAttribute("nextUrl") != null){
			String r="redirect:";
			return r+session.getAttribute("nextUrl").toString();
		}
		session.removeAttribute("nextUrl");
			
		// Torna a la pàgina principal
		return "redirect:/";*/
	}

	@RequestMapping("/logout") 
	public String logout(HttpSession session) {
		session.invalidate(); 
		return "redirect:/";
	}
	@RequestMapping("/home")
	public String home(HttpSession session) {
		String tipus = (String) session.getAttribute("tipus");

		if (tipus.compareTo("monitor") == 0) {
			session.setAttribute("home", "/home/monitor");
			return "/home/monitor";
		} else {
			if (tipus.compareTo("client") == 0) {
				session.setAttribute("home", "home/client");
				return "home/client";
			} else {
					session.setAttribute("home", "/home/admin");
					return "home/admin";

				}
			}
		}
	}

	

