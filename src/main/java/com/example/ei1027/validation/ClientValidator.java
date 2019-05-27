package com.example.ei1027.validation;

import com.example.ei1027.model.Client;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * Created by CIT on 14/05/2019.
 */
public class ClientValidator implements Validator {
    private static final int NIF_LENGTH = 9;
    private static final String SEXE_VALUES = "H|D";
    private static final int PASSWORD_MIN_LENGTH = 4;
    private static final int PASSWORD_MAX_LENGTH = 100;


	@Override
	public boolean supports(Class<?> clazz) {
		return Client.class.equals(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "nom", "required");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "clientId", "required");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "dataNaixement", "required");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "sexe", "required");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "email", "required");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "contrasenya", "required");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "recontrasenya", "required");


		Client client = (Client) target;

		if (client.getClientId().length() != NIF_LENGTH)
			errors.rejectValue("clientId", "length.nif");
		else if (!client.getClientId().matches("[0-9]{8}[a-zA-Z]{1}"))
			errors.rejectValue("clientId", "format.nif");
		if (!SEXE_VALUES.contains(client.getSexe()))
			errors.rejectValue("sexe", "value.sexe");
		LocalDate DOB = LocalDate.parse(client.getDataNaixement(), DateTimeFormatter.ofPattern("d/M/yyyy"));
		if (DOB.isAfter(LocalDate.now()))
			errors.rejectValue("dataNaixement", "value.dob");
		if (client.getContrasenya().length() <= PASSWORD_MIN_LENGTH
				|| client.getRecontrasenya().length() >= PASSWORD_MAX_LENGTH)
			errors.rejectValue("contrasenya", "length.password");
		else if (client.getRecontrasenya().length() <= PASSWORD_MIN_LENGTH
				|| client.getRecontrasenya().length() >= PASSWORD_MAX_LENGTH)
			errors.rejectValue("recontrasenya", "length.password");
		else if (!client.getContrasenya().equals(client.getRecontrasenya()))
			errors.rejectValue("recontrasenya", "noMatch.password");


	}
}
