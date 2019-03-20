package com.example.ei1027.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import com.example.ei1027.model.Activitat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

@Repository
public class ActivitatDao {

	@Autowired
	private JdbcTemplate jdbcTemplate;
	private static final class ActivitatMapper implements RowMapper<Activitat> {

		public Activitat mapRow(ResultSet rs, int rowNum) throws SQLException {
			Activitat Activitat = new Activitat();
			Activitat.setNomLlarg(rs.getString("nom_llarg"));
			Activitat.setEstat(rs.getString("estat"));
			Activitat.setDescripcio(rs.getString("descripcio"));
			Activitat.setDurada(rs.getFloat("durada"));
			Activitat.setData(rs.getDate("data"));
			Activitat.setPreu(rs.getFloat("preu"));
			Activitat.setMinAssistents(rs.getInt("min_assistents"));
			Activitat.setMaxAssistents(rs.getInt("max_assistents"));
			Activitat.setLloc(rs.getString("lloc"));
			Activitat.setPuntDeTrobada(rs.getString("punt_de_trobada"));
			Activitat.setHoraDeTrobada(rs.getTime("hora_de_trobada"));
			Activitat.setTextPerClient(rs.getString("text_per_clients"));
			Activitat.setIdInstructor(rs.getString("id_instructor"));
			Activitat.setNomTipusActivitat(rs.getString("nom_tipus_activitat"));
			return Activitat;
		}
	}
	public List<Activitat> getActivitats(){
		return this.jdbcTemplate.query(
				"select * from Activitat", new ActivitatMapper());
	}
	public Activitat getActivitat(String nomLlarg){
		return this.jdbcTemplate.queryForObject(
				"select * from Activitat where nom_llarg=?",
				new Object[] {nomLlarg}, new ActivitatMapper());
	}
	public void addActivitat(Activitat Activitat) {
		this.jdbcTemplate.update(
				"insert into Activitat(nom_llarg,estat,descripcio,durada,data,preu,min_assistents,max_assistents,lloc,punt_de_trobada,hora_de_trobada,"
				+ "text_per_clients,id_instructor,nom_tipus_activitat) values(?,?,?,?,?,?,?,?,?,?,?)",
				Activitat.getNomLlarg(), Activitat.getEstat(), Activitat.getDescripcio(),Activitat.getDurada(),
				 Activitat.getPreu(), Activitat.getMinAssistents(), Activitat.getMaxAssistents(), Activitat.getLloc(),
				 Activitat.getPuntDeTrobada(), Activitat.getHoraDeTrobada(), Activitat.getIdInstructor(), Activitat.getNomTipusActivitat());
	}
	public void updateActivitat(Activitat Activitat) {
		this.jdbcTemplate.update("update Activitat set estat=?,descripcio=?,durada=?,data=?,preu=?,min_assistents=?,max_assistents=?,lloc=?,punt_de_trobada=?,hora_de_trobada=?,"
				 + " text_per_clients=?,id_instructor=?,nom_tipus_activitat=? where nom_llarg=?",
				 Activitat.getEstat(), Activitat.getDescripcio(),Activitat.getDurada(),
				 Activitat.getPreu(), Activitat.getMinAssistents(), Activitat.getMaxAssistents(), Activitat.getLloc(),
				 Activitat.getPuntDeTrobada(), Activitat.getHoraDeTrobada(), Activitat.getIdInstructor(), Activitat.getNomTipusActivitat(), Activitat.getNomLlarg());
	}
	public void deleteActivitat(String nomLlarg) {
		this.jdbcTemplate.update("delete from Activitat where nom_llarg=?", nomLlarg);
	}
		

}
