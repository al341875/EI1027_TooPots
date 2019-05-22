package com.example.ei1027.model;

public class UserDetails {

    String usuari;
    String  contrasenya;
    String tipus ;

    public String getUsername() {
        return usuari;
    }

    public void setUsername(String usuari) {
        this.usuari = usuari;
    }

    public String getPassword() {
        return contrasenya;
    }

    public void setPassword(String password) {
        this.contrasenya = password;
    }

    public String getTipus() {
        return tipus;
    }

    public void setTipus(String tipus) {
        this.tipus = tipus;
    }

    public String toString() {
        return usuari + " " + contrasenya + " " + tipus;
    }
}
