package com.example.ei1027.controller.advice;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

import com.example.ei1027.validation.excepcions.TipusActivitatException;

@ControllerAdvice
public class TipusActivitatControllerAdvice {
	@ExceptionHandler(value = TipusActivitatException.class) 
	   public ModelAndView handleTipusActivitatException(TipusActivitatException ex){ 

	       ModelAndView mav = new ModelAndView("error/IdException"); 
	       mav.addObject("message", ex.getMessage()); 
	       mav.addObject("name", ex.getName());
	       return mav; 
	   }
}
