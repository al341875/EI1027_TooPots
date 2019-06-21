package com.example.ei1027.dao;

import com.example.ei1027.config.EncryptorFactory;
import com.example.ei1027.model.UserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;


@Repository
public class UserDao {

	private JdbcTemplate jdbcTemplate;

	@Autowired
	private EncryptorFactory encryptorFactory;

	@Autowired
	public void setDataSource(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}

	public List<UserDetails> findAll() {
		return this.jdbcTemplate.query("SELECT usuari FROM usuaris;", new UserMapper());
	}

	public void add(UserDetails user) {
        String passwordEnc = encryptorFactory.getEncryptor().encryptPassword(user.getContrasenya());
        this.jdbcTemplate.update("insert into usuaris(usuari, contrasenya, tipus) values(?, ?, ?)", user.getUsuari(),
                passwordEnc, user.getTipus());
    }

	public void delete(String userDni) {
		this.jdbcTemplate.update("delete from usuarios where usuari = ?", userDni);
	}
	public UserDetails find(UserDetails userDetails) {
		if (exists(userDetails))
			return this.jdbcTemplate.queryForObject("select * from usuaris where usuari = ?", new UserMapper(), userDetails.getUsuari());
		return null;
	}

	private boolean exists(UserDetails userDetails) {
		String contrasenya = userDetails.getContrasenya();
		String contrasenyaEnc = this.jdbcTemplate.queryForObject("select contrasenya from usuaris where usuari = ?", String.class, userDetails.getUsuari());
		return encryptorFactory.getEncryptor().checkPassword(contrasenya, contrasenyaEnc);
	}

	private static final class UserMapper implements RowMapper<UserDetails> {
		public UserDetails mapRow(ResultSet rs, int rowNum) throws SQLException {
			UserDetails userDetails = new UserDetails();
			userDetails.setUsuari(rs.getString("usuari"));
			userDetails.setContrasenya(rs.getString("contrasenya"));
			userDetails.setTipus(rs.getString("tipus"));
			return userDetails;
		}
	}

}