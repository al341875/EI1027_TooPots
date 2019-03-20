package com.example.ei1027.mapper;

import com.example.ei1027.model.Nivell;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;


public class NivellMapper implements RowMapper<Nivell> {

	@Override
	public Nivell mapRow(ResultSet rs, int rowNum) throws SQLException {
		Nivell nivell = new Nivell();
		nivell.setNivellId(rs.getShort("id"));
		nivell.setDescripcio(rs.getString("descripcio"));
		return nivell;
	}

}
