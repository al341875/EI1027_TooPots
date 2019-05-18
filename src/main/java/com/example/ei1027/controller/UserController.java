package com.example.ei1027.controller;

import javax.servlet.http.HttpSession;

import com.example.ei1027.dao.UserDao;
import com.example.ei1027.model.UserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller; 
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping; 


@Controller
@RequestMapping("/user") 
public class UserController {
   private UserDao userDao;

   @Autowired 
   public void setSociDao(UserDao userDao) {
       this.userDao = userDao;
   }
  
   @RequestMapping("/login") 
   public String listUsuaris(HttpSession session, Model model) {
       if (session.getAttribute("user") == null) 
       { 
          model.addAttribute("user", new UserDetails());
          return "login";
       } 
       model.addAttribute("users", userDao.listAllUsers());
       return "users/list";
   }
}
