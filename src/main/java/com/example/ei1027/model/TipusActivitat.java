package com.example.ei1027.model;

public class TipusActivitat {
    String nom;
    short nivell;

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }
	public short getNivell() {
		return nivell;
	}
	public void setNivell(short nivell) {
		this.nivell = nivell;
	}
	@Override
	public String toString() {
        return "TipusActivitat [nom=" + nom + ", nivell=" + nivell + "]";
    }

	
}
