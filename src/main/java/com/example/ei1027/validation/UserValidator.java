package com.example.ei1027.validation;

import com.example.ei1027.model.UserDetails;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;


public class UserValidator implements Validator {
	private static final int PASSWORD_MIN_LENGTH = 4;
	private static final int PASSWORD_MAX_LENGTH = 100;
    @Override
    public boolean supports(Class<?> cls) {
        return UserDetails.class.isAssignableFrom(cls);
    }

    @Override
    public void validate(Object obj, Errors errors) {
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "usuari", "required", "Es requereix un usuari");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "contrasenya", "required", "Es requereix una contrasenya");
        UserDetails user = (UserDetails) obj;
        if (user.getContrasenya().length() <= PASSWORD_MIN_LENGTH || user.getContrasenya().length() <= PASSWORD_MAX_LENGTH)
            errors.reject("password","userLenght.password");


    }
}