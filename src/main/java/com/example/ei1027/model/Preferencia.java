package com.example.ei1027.model;

public class Preferencia{
    private String clientId;
    private String nomTipusActivitat;

    public Preferencia() {
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public String getNomTipusActivitat() {
        return nomTipusActivitat;
    }

    public void setNomTipusActivitat(String nomTipusActivitat) {
        this.nomTipusActivitat = nomTipusActivitat;
    }
}