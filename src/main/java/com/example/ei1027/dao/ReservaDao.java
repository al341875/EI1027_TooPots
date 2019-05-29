package com.example.ei1027.dao;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

import java.time.format.DateTimeFormatter;

import java.util.List;

import javax.sql.DataSource;

import com.example.ei1027.model.EstatPagament;
import com.example.ei1027.model.Reserva;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;


@Repository
public class ReservaDao {

	private JdbcTemplate jdbcTemplate;

	@Autowired
	public void setDataSource(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}

	private static final class ReservaMapper implements RowMapper<Reserva> {
		public Reserva mapRow(ResultSet rs, int rowNum) throws SQLException {
			Reserva reserva = new Reserva();
			reserva.setIdReserva(rs.getInt("id_reserva"));
			LocalDate DOB = rs.getObject("data_reserva", LocalDate.class);
			reserva.setDataReserva(String.format("%d/%d/%d", DOB.getDayOfMonth(), DOB.getMonthValue(), DOB.getYear()));
			LocalDate DOB2 = rs.getObject("data_activitat", LocalDate.class);
			reserva.setDataActivitat(String.format("%d/%d/%d", DOB2.getDayOfMonth(), DOB2.getMonthValue(), DOB2.getYear()));
			reserva.setNumTransaccio(rs.getString("id_reserva"));
			reserva.setIdClient(rs.getString("id_reserva"));
			reserva.setNomActivitat(rs.getString("id_reserva"));
			reserva.setNumAssistents(rs.getInt("num_assistents"));
			reserva.setPreuPersona(rs.getDouble("preu_persona"));
			reserva.setPreuTotal(rs.getDouble("preu_total"));
			reserva.setEstatPagament(rs.getString("id_reserva"));
			return reserva;
	}
		
	}

	public List<Reserva> getReservas(){
		return this.jdbcTemplate.query(
				"select * from Reserva", new ReservaMapper());
	}
	public Reserva getReserva(String idReserva){
		return this.jdbcTemplate.queryForObject(
				"select * from Reserva where id_Reserva=?",
				new Object[] {idReserva}, new ReservaMapper());
	}

	public Reserva getReservaByActivitat(String nomActivitat){
		return this.jdbcTemplate.queryForObject(
				"select * from Reserva where nom_activitat=?",
				new Object[] {nomActivitat}, new ReservaMapper());
	}
	public List<Reserva> getReservaByStatus(String status){
		return this.jdbcTemplate.query(
				"select * from reserva where estat_pagament = ?", new ReservaMapper(), status);
	}
	public Reserva getReservaByUsuari(String idUsuari){
		return this.jdbcTemplate.queryForObject(
				"select * from Reserva where id_client=? ",
				new Object[] {idUsuari}, new ReservaMapper());
	}
	//canviar estat de la reserva
	public void aceptarPagament(String idReserva) {
		String numTransaccio = Double.toString((Math.random() * ((2000000 - 1) + 1)) + 1);

		this.jdbcTemplate.update("update Reserva set estat_pagament = ?,num_transaccio=?  where id_reserva = ?",
				EstatPagament.PAGAT.toString(), idReserva, numTransaccio);
	}


	public void addReserva(Reserva Reserva) {
		LocalDate today = LocalDate.now();
		 today.format( DateTimeFormatter.ofPattern("d/M/yyyy"));
		LocalDate DOB = LocalDate.parse(Reserva.getDataActivitat(), DateTimeFormatter.ofPattern("d/M/yyyy"));
		Integer idReserva = (int)((Math.random() * ((2000000 - 1) + 1)) + 1);
		this.jdbcTemplate.update(
				"insert into Reserva(id_Reserva,data_activitat,data_reserva,nom_activitat,num_transaccio,id_client,num_assistents,preu_persona,preu_total,estat_pagament) values(?,?,?,?,?,?,?,?,?,?)",
				idReserva, DOB, today,
				 Reserva.getNomActivitat(), "", Reserva.getIdClient(), Reserva.getNumAssistents(),
				 Reserva.getPreuPersona(),Reserva.getPreuPersona()* Reserva.getNumAssistents(),  Reserva.getEstatPagament());
	}
	public void updateReserva(Reserva Reserva) {
		this.jdbcTemplate.update("update Reserva set data_activitat =?,data_reserva =?,nom_activitat=?,num_transaccio=?,id_client=?,num_assistents=?,preu_persona=?,preu_total=?,estat_pagament=? where id_Reserva=?",
				Reserva.getIdReserva(), Reserva.getDataActivitat(), Reserva.getDataReserva(),
				 Reserva.getNomActivitat(), Reserva.getNumTransaccio(), Reserva.getIdClient(), Reserva.getNumAssistents(),
				 Reserva.getPreuPersona(), Reserva.getPreuTotal(), Reserva.getEstatPagament());
	}

	public void deleteReserva(String idReserva) {
		this.jdbcTemplate.update("delete from Reserva where id_Reserva=?", idReserva);
	}
	
}