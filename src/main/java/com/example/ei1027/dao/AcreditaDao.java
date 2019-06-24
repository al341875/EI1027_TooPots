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

    public List<Acredita> getAllAcreditaByInstructor(String instructorId) {
        return this.jdbcTemplate.query(
                "select * from acredita where id_instructor = ?", new AcreditaMapper(), instructorId);
    }

    public List<Acredita> getAcredita(String instructorId, String nomTipusActivitat) {
        return this.jdbcTemplate.query("select * from acredita where id_instructor=? AND nom_tipus_activitat=?  ", new AcreditaMapper(), instructorId, nomTipusActivitat);
    }

    public void add(List<Acredita> acreditaciones) {
        List<Acredita> acreditacionesBD = this.getAllAcreditaByInstructor(acreditaciones.get(0).getInstructorId());
        for (Acredita acredita : acreditacionesBD)
            if (!acreditaciones.contains(acredita))
                this.deleteAcredita(acredita.getNomTipusActivitat(), acredita.getInstructorId());
        for (Acredita acredita : acreditaciones)
            if (!acreditacionesBD.contains(acredita))
                this.add(acredita);
    }

    public void add(Acredita acredita) {
        this.jdbcTemplate.update(
                "insert into Acredita(nom_tipus_activitat, id_instructor) values(?,?)",
                acredita.getNomTipusActivitat(),
                acredita.getInstructorId());
    }

    public void updateAcredita(Acredita acredita) {
        this.jdbcTemplate.update("update Acredita set nom_tipus_activitat=?,id_instructor=? ",
                acredita.getNomTipusActivitat(), acredita.getInstructorId());
	}

    public void deleteAcredita(String nomTipusActivitat, String idInstructor) {
        this.jdbcTemplate.update("delete from acredita where nom_tipus_activitat=? AND id_instructor=?", nomTipusActivitat, idInstructor);
    }

    public void deleteAll(String idInstructor) {
        this.jdbcTemplate.update("delete from acredita where id_instructor=?", idInstructor);
    }

    public boolean exist(Acredita acredita) {
        return this.jdbcTemplate.queryForObject("select count(*) from acredita where nom_tipus_activitat=? AND id_instructor=?", Integer.class, acredita.getNomTipusActivitat(), acredita.getInstructorId()).intValue() > 0;
    }

	private static final class AcreditaMapper implements RowMapper<Acredita> {
		public Acredita mapRow(ResultSet rs, int rowNum) throws SQLException {
			Acredita acredita = new Acredita();
            acredita.setNomTipusActivitat(rs.getString("nom_tipus_activitat"));
            acredita.setInstructorId(rs.getString("id_instructor"));
            return acredita;
		}
	}
	
}

