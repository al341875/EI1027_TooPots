package com.example.ei1027.controller.advice;

import com.example.ei1027.validation.excepcions.UserException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

@ControllerAdvice
public class UserControllerAdvice {
    @ExceptionHandler(value = UserException.class)
    public ModelAndView handleUserException(UserException ex){

        ModelAndView mav = new ModelAndView("error/ErrorLogin");
        mav.addObject("message", ex.getMessage());
        mav.addObject("name", ex.getName());
        return mav;
    }


}
