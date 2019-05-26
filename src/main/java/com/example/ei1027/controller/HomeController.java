package com.example.ei1027.controller;

import com.example.ei1027.model.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
@RequestMapping("/home")
public class HomeController {

	@RequestMapping("/client")
	public String Client(Model model) {
        model.addAttribute("userDetails", new UserDetails());
        return "home/client";
	}
	@RequestMapping("/admin")
	public String Admin(Model model) {
		return "home/admin";
	}
	@RequestMapping("/monitor")
	public String Instructor(Model model) {
		return "home/monitor";
	}

}