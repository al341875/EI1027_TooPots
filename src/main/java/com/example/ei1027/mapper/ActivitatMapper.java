package com.example.ei1027.mapper;

import com.example.ei1027.model.Activitat;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * Created by CIT on 19/05/2019.
 */
public class ActivitatMapper implements RowMapper<Activitat> {
    public Activitat mapRow(ResultSet rs, int rowNum) throws SQLException {
        Activitat Activitat = new Activitat();
        Activitat.setNomLlarg(rs.getString("nom_llarg"));
        Activitat.setEstat(rs.getString("estat"));
        Activitat.setDescripcio(rs.getString("descripcio"));
        Activitat.setDurada(rs.getFloat("durada"));
        LocalDate date = rs.getDate("data").toLocalDate();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd MMMM yyyy");
        Activitat.setData(date.format(formatter));
        Activitat.setPreu(rs.getFloat("preu"));
        Activitat.setMinAssistents(rs.getInt("min_assistents"));
        Activitat.setMaxAssistents(rs.getInt("max_assistents"));
        Activitat.setLloc(rs.getString("lloc"));
        Activitat.setPuntDeTrobada(rs.getString("punt_de_trobada"));
        Activitat.setHoraDeTrobada(rs.getTime("hora_de_trobada"));
        Activitat.setTextPerClient(rs.getString("text_per_clients"));
        Activitat.setIdInstructor(rs.getString("id_instructor"));
        Activitat.setNomTipusActivitat(rs.getString("nom_tipus_activitat"));
        return Activitat;
    }

    private String reverse(String original) {
        return new StringBuilder(original).reverse().toString();
    }
}
