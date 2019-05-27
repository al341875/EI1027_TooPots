package com.example.ei1027.validation;

import com.example.ei1027.model.Instructor;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class InstructorValidator implements Validator {
	private static final int NIF_LENGTH = 9;
	private static final String SEXE_VALUES = "H|D";
	private static final int IBAN_LENGTH = 24;
	private static final int PASSWORD_MIN_LENGTH = 4;
	private static final int PASSWORD_MAX_LENGTH = 20;
	//private static final String ESTAT_VALUES = "pendent|rebutjada|acceptada";

	@Override
	public boolean supports(Class<?> clazz) {
		return Instructor.class.equals(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		// TODO Auto-generated method stub
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "nom", "required");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "instructorId", "required");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "iban", "required");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "dataNaixement", "required");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "domicili", "required");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "sexe", "required");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "email", "required");
		//ValidationUtils.rejectIfEmptyOrWhitespace(errors, "estat", "required");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "contrasenya", "required");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "recontrasenya", "required");

		Instructor instructor = (Instructor) target;

		if (instructor.getInstructorId().length() != NIF_LENGTH)
			errors.rejectValue("instructorId", "length.nif");
		else if (!instructor.getInstructorId().matches("[0-9]{8}[a-zA-Z]{1}"))
			errors.rejectValue("instructorId", "format.nif");
		if (instructor.getIban().length() != IBAN_LENGTH)
			errors.rejectValue("iban", "length.iban");
		if (!SEXE_VALUES.contains(instructor.getSexe()))
			errors.rejectValue("sexe", "value.sexe");
		/*if(!ESTAT_VALUES.contains(instructor.getEstat()))
			errors.rejectValue("estat", "value.estat");*/
		LocalDate DOB = LocalDate.parse(instructor.getDataNaixement(), DateTimeFormatter.ofPattern("d/M/yyyy"));
		if (DOB.isAfter(LocalDate.now()))
			errors.rejectValue("dataNaixement", "value.dob");
		if (instructor.getContrasenya().length() <= PASSWORD_MIN_LENGTH || instructor.getRecontrasenya().length() >= PASSWORD_MAX_LENGTH)
			errors.rejectValue("contrasenya", "length.password");
		else if (instructor.getRecontrasenya().length() <= PASSWORD_MIN_LENGTH || instructor.getRecontrasenya().length() >= PASSWORD_MAX_LENGTH)
			errors.rejectValue("recontrasenya", "length.password");
		else if (!instructor.getContrasenya().equals(instructor.getRecontrasenya()))
			errors.rejectValue("recontrasenya", "noMatch.password");
	}

}
