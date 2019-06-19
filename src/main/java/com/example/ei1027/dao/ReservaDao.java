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
			reserva.setNumTransaccio(rs.getString("num_transaccio"));
			reserva.setIdClient(rs.getString("id_client"));
			reserva.setNomActivitat(rs.getString("nom_activitat"));
			reserva.setNumAssistents(rs.getInt("num_assistents"));
			reserva.setPreuPersona(rs.getDouble("preu_persona"));
			reserva.setPreuTotal(rs.getDouble("preu_total"));
			reserva.setEstatPagament(rs.getString("estat_pagament"));
			return reserva;
	}
		
	}

	public List<Reserva> getReservas(){
		return this.jdbcTemplate.query(
				"select * from Reserva", new ReservaMapper());
	}
	public Reserva getReserva(Integer idReserva){
		return this.jdbcTemplate.queryForObject(
				"select * from Reserva where id_Reserva=?",
				 new ReservaMapper(), idReserva);
	}



	public List<Reserva> getReservaByActivitat(String nomActivitat){
		return this.jdbcTemplate.query(
				"select * from reserva where nom_activitat =? ", new ReservaMapper(), nomActivitat);
	}
	public List<Reserva> getReservaByUsuariStatus( String status,String idUsuari){
		return this.jdbcTemplate.query(
				"select * from Reserva where id_client=? and  estat_pagament = ? ",
			 new ReservaMapper(),idUsuari,status);
	}
	public List<Reserva> getReservaByUsuari(String idUsuari){
		return this.jdbcTemplate.query(
				"select * from Reserva where id_client=? ",
				new ReservaMapper(),idUsuari);
	}
	//canviar estat de la reserva
	public void aceptarPagament(Integer idReserva) {
		String numTransaccio = Double.toString((Math.random() * ((2000000 - 1) + 1)) + 1);

		this.jdbcTemplate.update("update Reserva set estat_pagament = ?,num_transaccio=?  where id_reserva = ?",
				EstatPagament.PAGAT.toString(), numTransaccio,idReserva );
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
	public void aceptarSolicitud(Integer idReserva) {
		this.jdbcTemplate.update("update reserva set estat_pagament = ? where id_reserva = ?",
				EstatPagament.ACCEPTADA.toString(), idReserva);
	}
	public void deleteReserva(Integer idReserva) {
		this.jdbcTemplate.update("delete from Reserva where id_Reserva=?", idReserva);
	}
	public void confirmaReserva(Integer idReserva) {
		this.jdbcTemplate.update("update reserva set estat_pagament = ? where id_reserva = ?",
				EstatPagament.CONFIRMAT.toString(), idReserva);
	}
	public List<Reserva> reservesCancelables(){
		return this.jdbcTemplate.query(
				"select * from Reserva where DATEDIFF(day, data_activitat, data_reserva) > 10  ",
				new ReservaMapper());
	}

}