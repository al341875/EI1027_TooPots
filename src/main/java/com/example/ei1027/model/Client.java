package com.example.ei1027.model;

public class Client {
	String clientId;
	String nom;
	String email;
	String sexe;
	String dataNaixement;
	String contrasenya;
	String recontrasenya;
	String imatge;
	public String getImatge() {
		return imatge;
	}

	public void setImatge(String imatge) {
		this.imatge = imatge;
	}
	public String getClientId() {
		return clientId;
	}

	public void setClientId(String id_client) {
		this.clientId = id_client;
	}

	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getSexe() {
		return sexe;
	}

	public void setSexe(String sexe) {
		this.sexe = sexe;
	}

	public String getDataNaixement() {
		return dataNaixement;
	}

	public void setDataNaixement(String data_naixement) {
		this.dataNaixement = data_naixement;
	}

	public String getContrasenya() {
		return contrasenya;
	}

	public void setContrasenya(String contrasenya) {
		this.contrasenya = contrasenya;
	}

	public String getRecontrasenya() {
		return recontrasenya;
	}

	public void setRecontrasenya(String recontrasenya) {
		this.recontrasenya = recontrasenya;
	}


	@Override
	public String toString() {
		return "Client[nom=" + nom + ", id_client=" + clientId + ", email="
				+ email + ", sexe=" + sexe + ", data_naixement="
				+ dataNaixement + "]";
	} 
}

