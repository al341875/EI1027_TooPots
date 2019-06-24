package com.example.ei1027.model;

public class Instructor {
	String instructorId;
	String nom;
	String email;
	String iban;
	String estat;
	String domicili;
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

	public String getInstructorId() {
		return instructorId;
	}

	public void setInstructorId(String instructorId) {
		this.instructorId = instructorId;
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

	public String getSexe() {
		return sexe;
	}

	public void setSexe(String sexe) {
		this.sexe = sexe;
	}

	public String getDataNaixement() {
		return dataNaixement;
	}

	public void setDataNaixement(String dataNaixement) {
		this.dataNaixement = dataNaixement;
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
		return "Instructor [instructorId=" + instructorId + ", nom=" + nom + ", email=" + email + ", iban=" + iban
				+ ", estat=" + estat + ", domicili=" + domicili + "]";
	}
	
	
	
	
	
}
