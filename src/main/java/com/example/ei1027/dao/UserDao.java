package com.example.ei1027.dao;

import com.example.ei1027.model.UserDetails;

import java.util.List;



public interface UserDao {

		UserDetails loadUserByUsername(String usuari, String contrasenya);
		List <UserDetails> listAllUsers();
}
