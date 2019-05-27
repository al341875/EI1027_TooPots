package com.example.ei1027.dao;


import com.example.ei1027.config.EncryptorFactory;
import com.example.ei1027.model.Client;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Repository
public class ClientDao{

	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Autowired
	private EncryptorFactory encryptorFactory;

	public void addClient(Client client) {
        LocalDate DOB = LocalDate.parse(client.getDataNaixement(), DateTimeFormatter.ofPattern("d/M/yyyy"));

		String contrasenyaEnc = encryptorFactory.getEncryptor().encryptPassword(client.getContrasenya());
		this.jdbcTemplate.update(
				"insert into Client(id_client,nom,email,data_naixement,sexe,contrasenya) values(?,?,?,?,?,?)",
				client.getClientId(), client.getNom(), client.getEmail(), DOB, client.getSexe(), contrasenyaEnc);

	}

	public Client getClient(String idClient) {
		return this.jdbcTemplate.queryForObject(
				"select * from Client where id_client=?  ", new ClientMapper(), idClient);
	}

	public void deleteClient(String idClient) {
		this.jdbcTemplate.update("delete from Client where id_client=?", idClient);
	}

	public void updateClient(Client client) {
        LocalDate DOB = LocalDate.parse(client.getDataNaixement(), DateTimeFormatter.ofPattern("d/M/yyyy"));

		String contrasenyaEnc = this.encryptorFactory.getEncryptor().encryptPassword(client.getContrasenya());
		this.jdbcTemplate.update("update Client set nom=?,email=?,sexe=?,data_naixement=?, contrasenya=? "
				+ "where id_client=?",
				client.getNom(), client.getEmail(), client.getSexe(), DOB, contrasenyaEnc, client.getClientId());
	}

	public List<Client> getClients() {
		return this.jdbcTemplate.query(
				"select * from Client", new ClientMapper());
	}

	public boolean existId(String clientId) {
		return this.jdbcTemplate.queryForObject("select count(id_client) from client where id_client = ?", Integer.class, clientId) > 0;
	}

	public boolean existEmail(String email) {
		return this.jdbcTemplate.queryForObject("select count(email) from client where email = ?", Integer.class, email) > 0;
	}

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
}
