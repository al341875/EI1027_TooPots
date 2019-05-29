package com.example.ei1027.validation;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.example.ei1027.model.Activitat;

public class ActivitatValidator implements Validator {
	private static final int NOM_LLARG_LENGHT = 100;
	@Override
	public boolean supports(Class<?> clazz) {
		// TODO Auto-generated method stub
		return Activitat.class.equals(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		// TODO Auto-generated method stub
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "nomLlarg", "required");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "estat", "required");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "descripcio", "required");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "durada", "required");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "data", "required");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "preu", "required");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "minAssistents", "required");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "maxAssistents", "required");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "lloc", "required");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "puntDeTrobada", "required");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "horaDeTrobada", "required");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "textPerClient", "required");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "idInstructor", "required");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "nomTipusActivitat", "required");


		Activitat activitat = (Activitat) target;

		if (activitat.getNomLlarg().length() >=NOM_LLARG_LENGHT)
			errors.rejectValue("nomLlarg", "length.nif");
		LocalDate DOB = LocalDate.parse(activitat.getData(), DateTimeFormatter.ofPattern("d/M/yyyy"));
		if (DOB.isBefore(LocalDate.now()))
			errors.rejectValue("data", "value.dataActivita");
		if(activitat.getMinAssistents() > activitat.getMaxAssistents())
			errors.rejectValue("minAssistents", "length.minMax");
			
		
	}


}