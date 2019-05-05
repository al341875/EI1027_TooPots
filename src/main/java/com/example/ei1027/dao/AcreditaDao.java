package com.example.ei1027.dao;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;


import com.example.ei1027.model.Acredita;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

@Repository
public class AcreditaDao {
	@Autowired
	private JdbcTemplate jdbcTemplate;

	private static final class AcreditaMapper implements RowMapper<Acredita> {
		 public Acredita mapRow(ResultSet rs, int rowNum) throws SQLException { 
		        Acredita acredita = new Acredita();
		        acredita.setNomTipusActivitat("nomTipusActivitat");
		        acredita.setCertificatUrl(rs.getString("certificat_url"));
		        return acredita;
		    }
	}
	
	public List<Acredita> getAllAcredita(){
		return this.jdbcTemplate.query(
				"select * from acredita", new AcreditaMapper());
	}
	public Acredita getAcredita(String certificatUrl,String nomTipusActivitat){
		return this.jdbcTemplate.queryForObject(
				"select * from acredita where certificatUrl=? AND nomTipusActivitat=?  ",
				new Object[] {certificatUrl, nomTipusActivitat}, new AcreditaMapper());
	}
	
	public void addAcredita(Acredita acredita) {
		this.jdbcTemplate.update(
				"insert into Acredita(nomTipusActivitat, certificat_url,) values(?,?)",
				acredita.getCertificatUrl(), 
				acredita.getNomTipusActivitat() );
	}
	
	public void updateAcredita(Acredita acredita) {
		this.jdbcTemplate.update("update Acredita set nomTipusActivitat=?,certificat_url=? ",
				acredita.getNomTipusActivitat(),acredita.getCertificatUrl());
	}
	public void deleteAcredita(String nomTipusActivitat ,String certificatUrl) {
		this.jdbcTemplate.update("delete from acredita where nom_tipus_activitat=? AND certificat_url=?", nomTipusActivitat,certificatUrl);
	}
	
}

