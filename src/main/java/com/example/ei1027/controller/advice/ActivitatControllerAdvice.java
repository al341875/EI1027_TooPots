package com.example.ei1027.controller.advice;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

import com.example.ei1027.validation.excepcions.ActivitatException;

@ControllerAdvice
public class ActivitatControllerAdvice {
	@ExceptionHandler(value = ActivitatException.class) 
	   public ModelAndView handleActivitatException(ActivitatException ex){ 

	       ModelAndView mav = new ModelAndView("error/IdException"); 
	       mav.addObject("message", ex.getMessage()); 
	       mav.addObject("name", ex.getName());
	       return mav; 
	   }
}
