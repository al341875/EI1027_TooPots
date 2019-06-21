package com.example.ei1027.dao;

import com.example.ei1027.model.Acredita;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class AcreditaDao {
	@Autowired
	private JdbcTemplate jdbcTemplate;

	public List<Acredita> getAllAcredita(){
		return this.jdbcTemplate.query(
				"select * from acredita", new AcreditaMapper());
	}

	public Acredita getAcredita(String instructorId, String nomTipusActivitat) {
		return this.jdbcTemplate.queryForObject("select * from acredita where instructor_id=? AND nomTipusActivitat=?  ", new AcreditaMapper(), instructorId, nomTipusActivitat);
	}

	public void addAcredita(Acredita acredita) {
		this.jdbcTemplate.update(
				"insert into Acredita(nomTipusActivitat, instructor_id) values(?,?)",
				acredita.getNomTipusActivitat(),
				acredita.getInstructorId());
	}
	
	public void updateAcredita(Acredita acredita) {
		this.jdbcTemplate.update("update Acredita set nomTipusActivitat=?,instructor_id=? ",
				acredita.getNomTipusActivitat(), acredita.getInstructorId());
	}

	public void deleteAcredita(String nomTipusActivitat, String instructor_id) {
		this.jdbcTemplate.update("delete from acredita where nom_tipus_activitat=? AND instructor_id=?", nomTipusActivitat, instructor_id);
	}

	private static final class AcreditaMapper implements RowMapper<Acredita> {
		public Acredita mapRow(ResultSet rs, int rowNum) throws SQLException {
			Acredita acredita = new Acredita();
			acredita.setNomTipusActivitat("nomTipusActivitat");
			acredita.setInstructorId(rs.getString("instructor_id"));
			return acredita;
		}
	}
	
}

