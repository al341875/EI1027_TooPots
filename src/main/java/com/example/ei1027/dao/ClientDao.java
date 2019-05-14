package com.example.ei1027.dao;


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

	private static final class ClientMapper implements RowMapper<Client> { 

	    public Client mapRow(ResultSet rs, int rowNum) throws SQLException { 
	        Client client = new Client();
	        client.setNom(rs.getString("nom"));
	        client.setEmail(rs.getString("email"));
	        client.setDataNaixement(rs.getDate("data_naixement").toString());
	        client.setClientId(rs.getString("id_client"));
	        client.setSexe(rs.getString("sexe"));
			client.setContrasenya(rs.getString("contrasenya"));
			return client;
	    }
	}

	public void addClient(Client client) {
		LocalDate DOB = LocalDate.parse(client.getDataNaixement(), DateTimeFormatter.ofPattern("d/MM/yyyy"));

		this.jdbcTemplate.update(
				"insert into Client(id_client,nom,email,data_naixement,sexe,contrasenya) values(?,?,?,?,?,?)",
				client.getClientId(), client.getNom(), client.getEmail(), DOB ,
				client.getSexe(), client.getContrasenya());
		
	}

	public Client getClient(String idClient) {
		return this.jdbcTemplate.queryForObject(
				"select * from Client where id_client=?  ",
				new Object[] {idClient}, new ClientMapper());
	}

	
	public void deleteClient(String idClient) {
		this.jdbcTemplate.update("delete from Client where id_client=?", idClient);
	}		
	

	
	public void updateClient(Client client) {
		LocalDate DOB = LocalDate.parse(client.getDataNaixement(), DateTimeFormatter.ofPattern("d/MM/yyyy"));

		this.jdbcTemplate.update("update Client set nom=?,email=?,sexe=?,data_naixement=?, contrasenya=? "
				+ "where id_client=?",
				client.getNom(), client.getEmail(), client.getSexe(), DOB, client.getContrasenya(),
					client.getClientId());
	}

	public List<Client> getClients() {
		return this.jdbcTemplate.query(
				"select * from Client", new ClientMapper());
	}
}
