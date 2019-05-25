package com.example.ei1027.controller;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

import com.example.ei1027.validation.ClientException;

@ControllerAdvice
public class ClientControllerAdvice {
	 @ExceptionHandler(value = ClientException.class) 
	   public ModelAndView handleClientException(ClientException ex){ 

	       ModelAndView mav = new ModelAndView("error/ClientException"); 
	       mav.addObject("message", ex.getMessage()); 
	       mav.addObject("name", ex.getName());
	       return mav; 
	   }


}
