package com.example.ei1027.model;

public enum EstatPagament {
    PENDENT("pendent"),
    ACCEPTADA("acceptat"),
    PAGAT("pagat"),
    CONFIRMAT( "confirmat");


    private final String descripcioEstatPagament;

    EstatPagament(String descripcioEstatPagament) {
        this.descripcioEstatPagament = descripcioEstatPagament;
    }

    @Override
    public String toString() {
        return descripcioEstatPagament;
    }
}
