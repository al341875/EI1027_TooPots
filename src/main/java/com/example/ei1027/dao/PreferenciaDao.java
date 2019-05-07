package com.example.ei1027.dao;

import com.example.ei1027.mapper.PreferenciaMapper;
import com.example.ei1027.model.Preferencia;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class PreferenciaDao {
	@Autowired
	JdbcTemplate jdbcTemplate;
	
	public void add(Preferencia preferencia) {
		String sql = "insert into preferencies(id_client, nom_tipus_activitat) values(?,?)";
		jdbcTemplate.update(sql, preferencia.getClientId(), preferencia.getNomTipusActivitat());
	}
	
	public void delete(Preferencia preferencia) {
		String sql = "delete from preferencies where id_client = ? AND nom_tipus_activitat = ?";
		jdbcTemplate.update(sql, preferencia.getClientId(), preferencia.getNomTipusActivitat());
	}
	
	public List<Preferencia> findAll(){
		String sql = "select * from preferencies";
		List<Preferencia> preferencies = jdbcTemplate.query(sql, new PreferenciaMapper());
		return preferencies;
	}

	public List<Preferencia> findByClient(String clientId){
		String sql = "select * from preferencies where id_client = ?";
		List<Preferencia> preferencies = jdbcTemplate.query(sql, new PreferenciaMapper(), clientId);
		return preferencies;
	}

}
