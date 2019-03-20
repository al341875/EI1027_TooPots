package com.example.ei1027.model;

public class TipusActivitat {
	String nomTipusActivitat;
	short nivell;
	
	public String getNomTipusActivitat() {
		return nomTipusActivitat;
	}
	public void setNomTipusActivitat(String nomTipusActivitat) {
		this.nomTipusActivitat = nomTipusActivitat;
	}
	public short getNivell() {
		return nivell;
	}
	public void setNivell(short nivell) {
		this.nivell = nivell;
	}
	@Override
	public String toString() {
		return "TipusActivitat [nomTipusActivitat=" + nomTipusActivitat + ", nivell=" + nivell + "]";
	}

	
}
