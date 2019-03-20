package com.example.ei1027.model;

import java.util.Set;
import java.util.Date;
import java.util.HashSet;

public class Reserva {
	Integer idReserva;
    Date dataReserva;
    Date dataActivitat;
    String numTransaccio;
    String idClient;
    String nomActivitat;
    Integer numAssistents;
    Double preuPersona;
    Double preuTotal;
    String estatPagament;
    
	public Integer getIdReserva() {
		return idReserva;
	}
	public void setIdReserva(Integer idReserva) {
		this.idReserva = idReserva;
	}
	public Date getDataReserva() {
		return dataReserva;
	}
	public void setDataReserva(Date dataReserva) {
		this.dataReserva = dataReserva;
	}
	public Date getDataActivitat() {
		return dataActivitat;
	}
	public void setDataActivitat(Date dataActivitat) {
		this.dataActivitat = dataActivitat;
	}
	public String getNumTransaccio() {
		return numTransaccio;
	}
	public void setNumTransaccio(String numTransaccio) {
		this.numTransaccio = numTransaccio;
	}
	public String getIdClient() {
		return idClient;
	}
	public void setIdClient(String idClient) {
		this.idClient = idClient;
	}
	public String getNomActivitat() {
		return nomActivitat;
	}
	public void setNomActivitat(String nomActivitat) {
		this.nomActivitat = nomActivitat;
	}
	public Integer getNumAssistents() {
		return numAssistents;
	}
	public void setNumAssistents(Integer numAssistents) {
		this.numAssistents = numAssistents;
	}
	public Double getPreuPersona() {
		return preuPersona;
	}
	public void setPreuPersona(Double preuPersona) {
		this.preuPersona = preuPersona;
	}
	public Double getPreuTotal() {
		return preuTotal;
	}
	public void setPreuTotal(Double preuTotal) {
		this.preuTotal = preuTotal;
	}
	public String getEstatPagament() {
		return estatPagament;
	}
	public void setEstatPagament(String estatPagament) {
		this.estatPagament = estatPagament;
	}
	

}
