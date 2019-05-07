package com.example.ei1027.model;

public class Acredita {
	String nomTipusActivitat;
	String certificatUrl;

	@Override
	public String toString() {
		return "Acredita [nomTipusActivitat=" + nomTipusActivitat + ", certificatUrl=" + certificatUrl + "]";
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

	
}
