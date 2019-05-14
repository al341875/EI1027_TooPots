package com.example.ei1027.model;


public enum Estat {
    PENDENT("pendent"),
    ACCEPTADA("acceptada"),
    REBUTJADA("rebutjada");

    private final String descripcioEstat;

    Estat(String descripcioEstat) {
        this.descripcioEstat = descripcioEstat;
    }

    @Override
    public String toString() {
        return descripcioEstat;
    }
}
