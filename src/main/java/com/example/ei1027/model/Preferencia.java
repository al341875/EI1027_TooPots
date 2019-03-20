package com.example.ei1027.model;

public class Preferencia{
    private short preferenciaId;
    private String nomTipusActivitat;

    public Preferencia() {
    }

    public short getPreferenciaId() {
        return preferenciaId;
    }

    public void setPreferenciaId(short preferenciaId) {
        this.preferenciaId = preferenciaId;
    }

    public String getNomTipusActivitat() {
        return nomTipusActivitat;
    }

    public void setNomTipusActivitat(String nomTipusActivitat) {
        this.nomTipusActivitat = nomTipusActivitat;
    }
}