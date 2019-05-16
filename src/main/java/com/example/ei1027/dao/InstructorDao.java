package com.example.ei1027.dao;

import com.example.ei1027.model.Estat;
import com.example.ei1027.model.Instructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Repository
public class InstructorDao {
	private JdbcTemplate jdbcTemplate;
	@Autowired
	public void setDataSource(DataSource dataSource) {
	        this.jdbcTemplate = new JdbcTemplate(dataSource); 
	}
	
	private static final class InstructorMapper implements RowMapper<Instructor> { 
		 public Instructor mapRow(ResultSet rs, int rowNum) throws SQLException {
			 Instructor instructor = new Instructor();
			 instructor.setInstructorId(rs.getString("id_instructor"));
			 instructor.setNom(rs.getString("nom"));
			 instructor.setEmail(rs.getString("email"));
			 instructor.setIban(rs.getString("iban"));
			 instructor.setEstat(rs.getString("estat"));
			 instructor.setDomicili(rs.getString("domicili"));
			 LocalDate DOB = rs.getObject("data_naixement", LocalDate.class);
			 instructor.setDataNaixement(String.format("%d/%d/%d", DOB.getDayOfMonth(), DOB.getMonthValue(), DOB.getYear()));
			 instructor.setSexe(rs.getString("sexe"));
			 instructor.setContrasenya(rs.getString("contrasenya"));

		        return instructor;
		    }
	}
	public List<Instructor> getInstructorsByStatus(String status){
		return this.jdbcTemplate.query(
				"select * from instructor where estat = ?", new InstructorMapper(), status);
	}

	public Instructor getInstructor(String idInstructor) {
		return this.jdbcTemplate.queryForObject(
				"select * from instructor where id_instructor=?",
				new Object[] {idInstructor}, new InstructorMapper());
	}
	public void addInstructor(Instructor instructor) {
		LocalDate DOB = LocalDate.parse(instructor.getDataNaixement(), DateTimeFormatter.ofPattern("d/M/yyyy"));

		this.jdbcTemplate.update(
				"insert into Instructor(id_instructor,nom,email,iban,estat,domicili,data_naixement,sexe,contrasenya) values(?,?,?,?,?,?,?,?,?)",
				instructor.getInstructorId(), instructor.getNom(), instructor.getEmail(),
				instructor.getIban(), Estat.PENDENT.toString(), instructor.getDomicili(), DOB, instructor.getSexe(), instructor.getContrasenya());
	}
	public void updateInstructor(Instructor instructor) {
		LocalDate DOB = LocalDate.parse(instructor.getDataNaixement(), DateTimeFormatter.ofPattern("d/M/yyyy"));

		this.jdbcTemplate.update("update Instructor set nom=?,email=?,iban=?,estat=?,domicili=?,data_naixement=?,sexe=?,contrasenya=? where id_instructor=?",
				instructor.getNom(), instructor.getEmail(), instructor.getIban(),
				Estat.ACCEPTADA.toString(), instructor.getDomicili(), DOB, instructor.getSexe(), instructor.getContrasenya(), instructor.getInstructorId());
	}
	public void deleteInstructor(String idInstructor) {
		this.jdbcTemplate.update("delete from instructor where id_instructor=?", idInstructor);
	}

	public void aceptarSolicitud(String idInstructor) {
		this.jdbcTemplate.update("update Instructor set estat = ? where id_instructor = ?",
				Estat.ACCEPTADA.toString(), idInstructor);
	}

	public void rebutjarSolicitud(String idInstructor) {
		this.jdbcTemplate.update("update Instructor set estat = ? where id_instructor = ?",
				Estat.REBUTJADA.toString(), idInstructor);
	}

	public void recuperarSolicitud(String idInstructor) {
		this.jdbcTemplate.update("update Instructor set estat = ? where id_instructor = ?",
				Estat.PENDENT.toString(), idInstructor);
	}
}
