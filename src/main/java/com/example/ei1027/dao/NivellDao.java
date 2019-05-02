package com.example.ei1027.dao;


import com.example.ei1027.mapper.NivellMapper;
import com.example.ei1027.model.Nivell;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;


public class NivellDao {

	@Autowired
	JdbcTemplate jdbcTemplate;
	
	public void add(Nivell nivell) {
		String sql = "insert into nivell(id, descripcio) values(?,?)";
		jdbcTemplate.update(sql, nivell.getNivellId(), nivell.getDescripcio());
	}
	
	public void delete(short id) {
		String sql = "delete from nivell where id = ?";
		jdbcTemplate.update(sql, id);
	}
	
	public Nivell findOne(short id) {
		String sql = "select id from nivell where id = ?";
		return jdbcTemplate.queryForObject(sql, new NivellMapper(), id);
	}
	
	public List<Nivell> findAll(){
		String sql = "select * from nivell";
		List<Nivell> nivells = jdbcTemplate.query(sql, new NivellMapper());
		return nivells;
	}
	
	public void update(Nivell nivell) {
		String sql = "update nivell set descripcio = ? where id = ?";
		jdbcTemplate.update(sql, nivell.getDescripcio(), nivell.getNivellId());
	}

}
