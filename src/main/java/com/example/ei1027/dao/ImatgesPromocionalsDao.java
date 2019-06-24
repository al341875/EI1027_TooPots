package com.example.ei1027.dao;

import com.example.ei1027.model.ImatgePromocional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

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

	public List<ImatgePromocional> findAll(String nomActivitat) {
		String sql = "select url from imatges_promocionals where nom_activitat = ?";
		return jdbcTemplate.query(sql, new ImatgesPromocionalsMapper(), nomActivitat);
	}

	public List<ImatgePromocional> findAll() {
		String sql = "select url from imatges_promocionals";
		return jdbcTemplate.query(sql, new ImatgesPromocionalsMapper());
	}

	private static final class ImatgesPromocionalsMapper implements RowMapper<ImatgePromocional> {

		public ImatgePromocional mapRow(ResultSet rs, int rowNum) throws SQLException {
			ImatgePromocional imatgePromocional = new ImatgePromocional();
			imatgePromocional.setImatge(rs.getString("url"));
			return imatgePromocional;
		}
	}
	
}
