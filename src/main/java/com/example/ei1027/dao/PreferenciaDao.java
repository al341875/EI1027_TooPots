package com.example.ei1027.dao;

import com.example.ei1027.mapper.PreferenciaMapper;
import com.example.ei1027.model.Preferencia;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;


public class PreferenciaDao {
	@Autowired
	JdbcTemplate jdbcTemplate;
	
	public void add(Preferencia preferencia) {
		String sql = "insert into preferencies(id, nom_tipus_activitat) values(?,?)";
		jdbcTemplate.update(sql, preferencia.getPreferenciaId(), preferencia.getNomTipusActivitat());
	}
	
	public void delete(short id) {
		String sql = "delete from preferencies where id = ?";
		jdbcTemplate.update(sql, id);
	}
	
	public Preferencia findOne(short id) {
		String sql = "select id from preferencies where id = ?";
		return jdbcTemplate.queryForObject(sql, new PreferenciaMapper(), id);
		
	}
	
	public List<Preferencia> findAll(){
		String sql = "select * from preferencies";
		List<Preferencia> preferencies = jdbcTemplate.query(sql, new PreferenciaMapper());
		return preferencies;
	}
	
	public void update(Preferencia preferencia) {
		String sql = "update preferencies set nom_tipus_activitat = ? where id = ?";
		jdbcTemplate.update(sql, preferencia.getNomTipusActivitat(), preferencia.getPreferenciaId());
	}
}
