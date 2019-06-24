package com.example.ei1027.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import com.example.ei1027.model.Acreditacio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;



@Repository
public class AcreditacioDao {
	@Autowired
	private JdbcTemplate jdbcTemplate;

	private static final class AcreditacioMapper implements RowMapper<Acreditacio> {
		 public Acreditacio mapRow(ResultSet rs, int rowNum) throws SQLException { 
		        Acreditacio acreditacio = new Acreditacio();
		        acreditacio.setIdInstructor(rs.getString("id_instructor"));
		        acreditacio.setCertificatUrl(rs.getString("certificat_url"));
		        acreditacio.setEstat(rs.getString("estat"));
		        return acreditacio;
		    }
	}
	
	public List<Acreditacio> getAcreditacions(){
		return this.jdbcTemplate.query(
				"select * from acreditacio", new AcreditacioMapper());
	}
	public Acreditacio getAcreditacio(String url){
		return this.jdbcTemplate.queryForObject(
				"select * from acreditacio where certificat_url=?  ",
				new Object[] {url}, new AcreditacioMapper());
	}
	public List<Acreditacio> getAcreditacio2(String idInstructor){
		return this.jdbcTemplate.query(
				"select * from acreditacio where id_instructor=?  ",
				 new AcreditacioMapper(),idInstructor);
	}
	public void addAcreditacio(Acreditacio acreditacio) {
		this.jdbcTemplate.update(
				"insert into Acreditacio(id_instructor,certificat_url,estat) values(?,?,?)",
				acreditacio.getIdInstructor(), 
				acreditacio.getCertificatUrl(),acreditacio.getEstat() );
	}
	
	public void updateAcreditacio(Acreditacio acreditacio) {
		this.jdbcTemplate.update("update Acreditacio set certificat_url=?,estat=? "
				+ "where id_instructor=?",
				acreditacio.getCertificatUrl(), acreditacio.getEstat(), acreditacio.getIdInstructor());
	}
	public void deleteAcreditacio(String idInstructor) {
		this.jdbcTemplate.update("delete from acreditacio where id_instructor=?", idInstructor);
	}
	public Acreditacio getPdf(String url){
		return this.jdbcTemplate.queryForObject(
				"select * from acreditacio where certificat_url=?  ",
				new Object[] {url}, new AcreditacioMapper());
	}
	
}
