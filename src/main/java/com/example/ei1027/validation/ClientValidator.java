package com.example.ei1027.validation;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowCallbackHandler;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.example.ei1027.dao.ClientDao;

import com.example.ei1027.model.Client;

/**
 * Created by CIT on 14/05/2019.
 */
public class ClientValidator implements Validator {
	private static final int NIF_LENGTH = 9;
	private static final String SEXE_VALUES = "H|D";
	private static final int PASSWORD_MIN_LENGTH = 4;
	private static final int PASSWORD_MAX_LENGTH = 20;

	private static final class ClientMapper implements RowMapper<Client> {

		public Client mapRow(ResultSet rs, int rowNum) throws SQLException {
			Client client = new Client();
			client.setNom(rs.getString("nom"));
			client.setEmail(rs.getString("email"));
			LocalDate DOB = rs.getObject("data_naixement", LocalDate.class);
			client.setDataNaixement(String.format("%d/%d/%d", DOB.getDayOfMonth(), DOB.getMonthValue(), DOB.getYear()));
			client.setClientId(rs.getString("id_client"));
			client.setSexe(rs.getString("sexe"));
			client.setContrasenya(rs.getString("contrasenya"));
			return client;
		}
	}

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
		else if (client.getClientId().matches("^[A-Z]{8}\\d{1}"))
			if (!SEXE_VALUES.contains(client.getSexe()))
				errors.rejectValue("sexe", "value.sexe");
		LocalDate DOB = LocalDate.parse(client.getDataNaixement(), DateTimeFormatter.ofPattern("d/M/yyyy"));
		if (DOB.isAfter(LocalDate.now()))
			errors.rejectValue("dataNaixement", "value.dob");
		if (client.getContrasenya().length() <= PASSWORD_MIN_LENGTH
				|| client.getRecontrasenya().length() >= PASSWORD_MAX_LENGTH)
			errors.rejectValue("contrasenya", "length.password");
		if (client.getRecontrasenya().length() <= PASSWORD_MIN_LENGTH
				|| client.getRecontrasenya().length() >= PASSWORD_MAX_LENGTH)
			errors.rejectValue("recontrasenya", "length.password");
		else if (!client.getContrasenya().equals(client.getRecontrasenya()))
			errors.rejectValue("recontrasenya", "noMatch.password");


	}
}
