package com.example.ei1027.dao;


import com.example.ei1027.model.Client;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

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
	        return client;
	    }
	}


	
	public void addClient(Client client) {
		// TODO Auto-generated method stub
		Date DOB = null;
		try {
			DOB = new SimpleDateFormat("dd/MM/yyyy").parse(client.getDataNaixement());
		} catch (ParseException e) {
			e.printStackTrace();
		}
		this.jdbcTemplate.update(
				"insert into Client(id_client,nom,email,data_naixement,sexe) values(?,?,?,?,?)",
				client.getClientId(), client.getNom(), client.getEmail(), DOB ,
				client.getSexe() );
		
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
		Date DOB = null;

		try {
			Logger.getAnonymousLogger().log(Level.WARNING, client.getDataNaixement());
			DOB = new SimpleDateFormat("dd/MM/yyyy").parse(client.getDataNaixement());
			Logger.getAnonymousLogger().log(Level.WARNING, DOB.toString());
		} catch (ParseException e) {
			e.printStackTrace();
		}
		this.jdbcTemplate.update("update Client set nom=?,email=?,sexe=?,data_naixement=? "
				+ "where id_client=?",
			 client.getNom(), client.getEmail(), client.getSexe(), DOB,
					client.getClientId());
	}
	

	
	public List<Client> getClients() {
		// TODO Auto-generated method stub
		return this.jdbcTemplate.query(
				"select * from Client", new ClientMapper());
	}
}
