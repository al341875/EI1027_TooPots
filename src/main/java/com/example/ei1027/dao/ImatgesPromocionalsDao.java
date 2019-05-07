package com.example.ei1027.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class ImatgesPromocionalsDao {
	@Autowired
	JdbcTemplate jdbcTemplate;
	
	public void add(String url, String nomActivitat) {
		String sql = "insert into imatges_promocionals(nom_activitat, url) values(?,?)";
		jdbcTemplate.update(sql, nomActivitat, url);
	}
	
	public void deleteOne(String url) {
		String sql = "delete from imatges_promocionals where url = ?";
		jdbcTemplate.update(sql, url);
	}
	
	public void deleteAll(String nomActivitat) {
		String sql = "delete from imatges_promocionals where nom_activitat = ?";
		jdbcTemplate.update(sql, nomActivitat);
	}
	
	public List<String> findAll(String nomActivitat) {
		String sql = "select url from imatges_promocionals where nom_activitat = ?";
		List<String> imatges = jdbcTemplate.queryForList(sql, String.class, nomActivitat);
		return imatges;
	}
	
}
