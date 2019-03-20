package com.example.ei1027.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

import com.example.ei1027.model.Instructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

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
		        instructor.setIdInstructor(rs.getString("id_instructor"));
		        instructor.setNom(rs.getString("nom"));
		        instructor.setEmail(rs.getString("email"));
		        instructor.setIban(rs.getString("iban"));
		        instructor.setEstat(rs.getString("estat"));
		        instructor.setDomicili(rs.getString("domicili"));

		        return instructor;
		    }
	}
	public List<Instructor> getInstructors(){
		return this.jdbcTemplate.query(
				"select * from instructor", new InstructorMapper());
	}
	public Instructor getInstructors(String idInstructor){
		return this.jdbcTemplate.queryForObject(
				"select * from instructor where id_instructor=?",
				new Object[] {idInstructor}, new InstructorMapper());
	}
	public void addInstructor(Instructor instructor) {
		this.jdbcTemplate.update(
				"insert into Instructor(id_instructor,nom,email,iban,estat,domicili) values(?,?,?,?,?,?)",
				instructor.getIdInstructor(), instructor.getNom(), instructor.getEmail(),
				 instructor.getIban(), instructor.getEstat(), instructor.getDomicili() );
	}
	public void updateInstructor(Instructor instructor) {
		this.jdbcTemplate.update("update Instructor set nom=?,email=?,iban=?,estat=?,domicili=? where id_instructor=?",
				instructor.getNom(), instructor.getEmail(), instructor.getIban(), 
				instructor.getEstat(), instructor.getDomicili(), instructor.getIdInstructor());
	}
	public void deleteInstructor(String idInstructor) {
		this.jdbcTemplate.update("delete from instructor where id_instructor=?", idInstructor);
	}
	
}
