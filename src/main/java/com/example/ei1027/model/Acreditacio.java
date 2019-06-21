package com.example.ei1027.model;



public class Acreditacio {
	String idInstructor;
	String nomTipusActivitat;
	String certificatUrl;
	String estat;
	public String getIdInstructor() {
		return idInstructor;
	}
	public void setIdInstructor(String idInstructor) {
		this.idInstructor = idInstructor;
	}
	public String getNomTipusActivitat() {
		return nomTipusActivitat;
	}
	public void setNomTipusActivitat(String nomTipusActivitat) {
		this.nomTipusActivitat = nomTipusActivitat;
	}
	public String getCertificatUrl() {
		return certificatUrl;
	}
	public void setCertificatUrl(String certificatUrl) {
		this.certificatUrl = certificatUrl;
	}
	public String getEstat() {
		return estat;
	}
	public void setEstat(String estat) {
		this.estat = estat;
	}
	@Override
	public String toString() {
        return "Acreditacio [instructorId=" + idInstructor + ", nom=" + nomTipusActivitat
                + ", instructorId=" + certificatUrl + ", estat=" + estat + "]";
    }
}
