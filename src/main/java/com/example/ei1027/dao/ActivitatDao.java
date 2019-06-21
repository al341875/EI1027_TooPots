package com.example.ei1027.dao;

import com.example.ei1027.mapper.ActivitatMapper;
import com.example.ei1027.model.Activitat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Repository
public class ActivitatDao {

	@Autowired
	private JdbcTemplate jdbcTemplate;

	public List<Activitat> getActivitats(){
		return this.jdbcTemplate.query(
				"select * from Activitat", new ActivitatMapper());
	}

    public List<Activitat> getActivitatsByStatus(String status) {
        return this.jdbcTemplate.query(
                "select * from Activitat where estat = ?", new ActivitatMapper(), status);
    }

    public List<Activitat> getActivitatsByInstructor(String idInstructor) {
        return this.jdbcTemplate.query(
                "select * from Activitat where id_instructor = ?", new ActivitatMapper(), idInstructor);
    }

    public Activitat getActivitat(String nomLlarg) {
        return this.jdbcTemplate.queryForObject(
                "select * from Activitat where nom_llarg=?",
				new Object[] {nomLlarg}, new ActivitatMapper());
	}
	public void addActivitat(Activitat Activitat) {
		LocalDate DOB = LocalDate.parse(Activitat.getData(), DateTimeFormatter.ofPattern("d/M/yyyy"));

		this.jdbcTemplate.update(
				"insert into Activitat(nom_llarg,estat,descripcio,durada,data,preu,min_assistents,max_assistents,lloc,punt_de_trobada,hora_de_trobada,"
				+ "text_per_clients,id_instructor,nom_tipus_activitat) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?)",
				Activitat.getNomLlarg(), Activitat.getEstat(), Activitat.getDescripcio(),Activitat.getDurada(),DOB,
				Activitat.getPreu(), Activitat.getMinAssistents(), Activitat.getMaxAssistents(), Activitat.getLloc(),
				Activitat.getPuntDeTrobada(), Activitat.getHoraDeTrobada(),Activitat.getTextPerClient(), Activitat.getIdInstructor(), Activitat.getNomTipusActivitat());
	}

	public void updateActivitat(Activitat Activitat) {
		LocalDate DOB = LocalDate.parse(Activitat.getData(), DateTimeFormatter.ofPattern("d/M/yyyy"));

		this.jdbcTemplate.update("update Activitat set estat=?,descripcio=?,durada=?,data=?,preu=?,min_assistents=?,max_assistents=?,lloc=?,punt_de_trobada=?,hora_de_trobada=?,"
				+ " text_per_clients=?,id_instructor=?,nom_tipus_activitat=? where nom_llarg=?",
				Activitat.getEstat(), Activitat.getDescripcio(),Activitat.getDurada(),DOB,
				Activitat.getPreu(), Activitat.getMinAssistents(), Activitat.getMaxAssistents(), Activitat.getLloc(),
				Activitat.getPuntDeTrobada(), Activitat.getHoraDeTrobada(),Activitat.getTextPerClient(), Activitat.getIdInstructor(), Activitat.getNomTipusActivitat(), Activitat.getNomLlarg());
	}

    public void deleteActivitat(String nomLlarg) {
        this.jdbcTemplate.update("delete from Activitat where nom_llarg=?", nomLlarg);
    }

    public boolean existIdInstructor(String idInstructor) {
        return this.jdbcTemplate.queryForObject("select count(id_instructor) from instructor where id_instructor = ?", Integer.class, idInstructor) > 0;
    }





}
