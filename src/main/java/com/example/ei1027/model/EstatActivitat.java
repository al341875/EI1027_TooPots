package com.example.ei1027.model;

public enum EstatActivitat {
    OBERTA("oberta"),
    TANCADA("tancada"),
    COMPLETA("completa"),
    CANCELADA( "cancelada");


    private final String descripcioEstatActivitat;

    EstatActivitat(String descripcioEstatActivitat) {
        this.descripcioEstatActivitat = descripcioEstatActivitat;
    }

    @Override
    public String toString() {
        return descripcioEstatActivitat;
    }
}

