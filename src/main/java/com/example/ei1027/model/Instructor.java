package com.example.ei1027.model;

public class Instructor {
	String idInstructor;
	String nom;
	String email;
	String iban;
	String estat;
	String domicili;
	public String getIdInstructor() {
		return idInstructor;
	}
	public void setIdInstructor(String idInstructor) {
		this.idInstructor = idInstructor;
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
	public String getIban() {
		return iban;
	}
	public void setIban(String iban) {
		this.iban = iban;
	}
	public String getEstat() {
		return estat;
	}
	public void setEstat(String estat) {
		this.estat = estat;
	}
	public String getDomicili() {
		return domicili;
	}
	public void setDomicili(String domicili) {
		this.domicili = domicili;
	}
	@Override
	public String toString() {
		return "Instructor [idInstructor=" + idInstructor + ", nom=" + nom + ", email=" + email + ", iban=" + iban
				+ ", estat=" + estat + ", domicili=" + domicili + "]";
	}
	
	
	
	
	
}
