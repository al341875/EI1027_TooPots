package com.example.ei1027.dao;

import com.example.ei1027.model.TipusActivitat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;


@Repository
public class TipusActivitatDao {
	private JdbcTemplate jdbcTemplate;
	@Autowired
	public void setDataSource(DataSource dataSource) {
	        this.jdbcTemplate = new JdbcTemplate(dataSource); 
	}

	public List<TipusActivitat> getTipusActivitats(){
		return this.jdbcTemplate.query(
                "select * from tipus_activitat", new TipusActivitatMapper());
    }

	public TipusActivitat getTipusActivitat( String nomTipusActivitat){
		return this.jdbcTemplate.queryForObject(
				"select * from Tipus_activitat where nom_tipus_activitat=? ",
				new Object[] {nomTipusActivitat}, new TipusActivitatMapper());
	}

	public void addTipusActivitat(TipusActivitat tipusActivitat) {
		this.jdbcTemplate.update(
				"insert into Tipus_activitat(nom_tipus_activitat,nivell) values(?,?)",
                tipusActivitat.getNom(), tipusActivitat.getNivell());
    }

    public void updateTipusActivitat(TipusActivitat tipusActivitat) {
		this.jdbcTemplate.update("update Tipus_activitat set nivell=? where nom_tipus_activitat=?",
                tipusActivitat.getNivell(), tipusActivitat.getNom());
    }

    public void deleteTipusActivitat(String nomTipusActivitat) {
		this.jdbcTemplate.update("delete from Tipus_activitat where nom_tipus_activitat=?", nomTipusActivitat);
	}

    private static final class TipusActivitatMapper implements RowMapper<TipusActivitat> {
        public TipusActivitat mapRow(ResultSet rs, int rowNum) throws SQLException {
            TipusActivitat tipusActivitat = new TipusActivitat();
            tipusActivitat.setNom(rs.getString("nom_tipus_activitat"));
            tipusActivitat.setNivell(rs.getShort("nivell"));
            return tipusActivitat;
        }
    }
}
