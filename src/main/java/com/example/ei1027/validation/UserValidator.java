package com.example.ei1027.validation;

import com.example.ei1027.model.UserDetails;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;


public class UserValidator implements Validator {
    @Override
    public boolean supports(Class<?> cls) {
        return UserDetails.class.isAssignableFrom(cls);
    }

    @Override
    public void validate(Object obj, Errors errors) {
        UserDetails user = (UserDetails) obj;
        if (user.getUsername().trim().equals(""))
            errors.rejectValue("username", "Required", "A Username is required");
        if (user.getPassword().trim().equals(""))
            errors.rejectValue("password", "Required", "A password is required");

    }
}