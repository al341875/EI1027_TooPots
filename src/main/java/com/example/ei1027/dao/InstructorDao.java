package com.example.ei1027.dao;

import com.example.ei1027.config.EncryptorFactory;
import com.example.ei1027.mapper.ActivitatMapper;
import com.example.ei1027.model.Activitat;
import com.example.ei1027.model.Client;
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
	private EncryptorFactory encryptorFactory;

	@Autowired
	public void setDataSource(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}

	public List<Instructor> getInstructorsByStatus(String status){
		return this.jdbcTemplate.query(
				"select * from instructor where estat = ?", new InstructorMapper(), status);
	}

	public Instructor getInstructor(String idInstructor) {
		return this.jdbcTemplate.queryForObject(
				"select * from instructor where id_instructor = ?", new InstructorMapper(), idInstructor);
	}

	public void addInstructor(Instructor instructor) {
		LocalDate DOB = LocalDate.parse(instructor.getDataNaixement(), DateTimeFormatter.ofPattern("d/M/yyyy"));
		String contrasenyaEnc = encryptorFactory.getEncryptor().encryptPassword(instructor.getContrasenya());

		this.jdbcTemplate.update(
				"insert into Instructor(id_instructor,nom,email,iban,estat,domicili,data_naixement,sexe,contrasenya,imatge) values(?,?,?,?,?,?,?,?,?,?)",
				instructor.getInstructorId(), instructor.getNom(), instructor.getEmail(),
				instructor.getIban(), Estat.PENDENT.toString(), instructor.getDomicili(), DOB, instructor.getSexe(), contrasenyaEnc, instructor.getImatge());
	}

	public void updateInstructor(Instructor instructor) {
		LocalDate DOB = LocalDate.parse(instructor.getDataNaixement(), DateTimeFormatter.ofPattern("d/M/yyyy"));
		String contrasenyaEnc = encryptorFactory.getEncryptor().encryptPassword(instructor.getContrasenya());


		this.jdbcTemplate.update("update Instructor set nom=?,email=?,iban=?,estat=?,domicili=?,data_naixement=?,sexe=?,contrasenya=?,imatge=? where id_instructor=?",
				instructor.getNom(), instructor.getEmail(), instructor.getIban(),
				Estat.ACCEPTADA.toString(), instructor.getDomicili(), DOB, instructor.getSexe(), contrasenyaEnc,instructor.getImatge(), instructor.getInstructorId());
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

	public List<Activitat> findActivitiesByInstructorId(String instructorId){
		return this.jdbcTemplate.query("select * from activitat where id_instructor = ?", new ActivitatMapper(), instructorId);
	}

	public String getEmail(String instructorId){
		return this.jdbcTemplate.queryForObject("select email from instructor where id_instructor = ?", String.class, instructorId);
	}

	public boolean existId(String instructorId) {
		return this.jdbcTemplate.queryForObject("select count(id_instructor) from instructor where id_instructor = ?", Integer.class, instructorId) > 0;
	}

	public boolean existEmail(String email) {
		return this.jdbcTemplate.queryForObject("select count(email) from instructor where email = ?", Integer.class, email) > 0;
	}

	public boolean existIban(String iban) {
		return this.jdbcTemplate.queryForObject("select count(iban) from instructor where iban = ?", Integer.class, iban) > 0;
	}
	public Instructor getImatge(String url){
		return this.jdbcTemplate.queryForObject(
				"select * from instructor where imatge=?  ",
				new Object[] {url}, new InstructorMapper());
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
			instructor.setImatge(rs.getString("imatge"));

			return instructor;
		}
	}
}
