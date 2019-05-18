package com.example.ei1027.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

import com.example.ei1027.model.UserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import com.example.ei1027.model.UserDetails;


@Repository
public class UserProvider implements UserDao {

	private JdbcTemplate jdbcTemplate;

	@Autowired
	public void setDataSource(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}

	private static final class UserMapper implements RowMapper<UserDetails> {
		public UserDetails mapRow(ResultSet rs, int rowNum) throws SQLException {
			UserDetails userDetails = new UserDetails();
			userDetails.setUsername(rs.getString("usuari"));
			userDetails.setPassword(rs.getString("contrasenya"));
			userDetails.setTipus(rs.getString("tipus"));
			return userDetails;
		}
	}

	@Override
	public UserDetails loadUserByUsername(String usuari, String contrasenya) {

		UserDetails user = null;
		try {
			user = this.jdbcTemplate.queryForObject("SELECT usuari, contrasenya,tipus, type FROM usuaris WHERE usuari = ?;",
					new Object[] { usuari }, new UserMapper());
		} catch (DataAccessException e) {
			// TODO Auto-generated catch block
		}

		if (user == null)
			return null;

		// BasicPasswordEncryptor passwordEncryptor = new
		// BasicPasswordEncryptor();

		if (contrasenya.compareTo(user.getPassword()) == 0) {
			return user;
		} else {
			return null;
		}
	}

	@Override
	public List<UserDetails> listAllUsers() {

		return this.jdbcTemplate.query("SELECT usuari FROM usuaris;", new UserMapper());
	}

	public void addUser(UserDetails user) {
		this.jdbcTemplate.update("insert into usuaris(usuari, contrasenya, tipus) values(?, ?, ?)", user.getUsername(),
				user.getPassword(), user.getTipus());
	}

	public void deleteUser(String userDni) {
		this.jdbcTemplate.update("delete from usuarios where usuari = ?", userDni);
	}

}