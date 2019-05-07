package com.example.ei1027.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.example.ei1027.model.Preferencia;
import org.springframework.jdbc.core.RowMapper;

public class PreferenciaMapper implements RowMapper<Preferencia>{

	@Override
	public Preferencia mapRow(ResultSet rs, int rowNum) throws SQLException {
		Preferencia preferencia = new Preferencia();
		preferencia.setClientId(rs.getString("id_client"));
		preferencia.setNomTipusActivitat(rs.getString("nom_tipus_activitat"));
		return preferencia;
	}
}
