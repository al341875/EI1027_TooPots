package com.example.ei1027.model;

public class Acredita {
	String nomTipusActivitat;
	String instructorId;

	public Acredita() {
	}

	public Acredita(String nomTipusActivitat, String instructorId) {
		this.nomTipusActivitat = nomTipusActivitat;
		this.instructorId = instructorId;
	}

	@Override
	public String toString() {
		return "Acredita [nom=" + nomTipusActivitat + ", instructorId=" + instructorId + "]";
	}
	public String getNomTipusActivitat() {
		return nomTipusActivitat;
	}
	public void setNomTipusActivitat(String nomTipusActivitat) {
		this.nomTipusActivitat = nomTipusActivitat;
	}

	public String getInstructorId() {
		return instructorId;
	}

	public void setInstructorId(String instructorId) {
		this.instructorId = instructorId;
	}

	
}
