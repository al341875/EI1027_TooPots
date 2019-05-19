package com.example.ei1027.model;

import java.sql.Time;
import java.util.Date;

public class Activitat {
	String nomLlarg;
	String estat;
	String descripcio;
	Float durada;
	String data;
	Float preu;
	Integer minAssistents;
	Integer maxAssistents;
	String lloc;
	String puntDeTrobada;
	Time horaDeTrobada;
	String textPerClient;
	String idInstructor;
	String nomTipusActivitat;
	public String getNomLlarg() {
		return nomLlarg;
	}
	public void setNomLlarg(String nomLlarg) {
		this.nomLlarg = nomLlarg;
	}
	public String getEstat() {
		return estat;
	}
	public void setEstat(String estat) {
		this.estat = estat;
	}
	public String getDescripcio() {
		return descripcio;
	}
	public void setDescripcio(String descripcio) {
		this.descripcio = descripcio;
	}
	public Float getDurada() {
		return durada;
	}
	public void setDurada(Float durada) {
		this.durada = durada;
	}
	public String getData() {
		return data;
	}
	public void setData(String data) {
		this.data = data;
	}
	public Float getPreu() {
		return preu;
	}
	public void setPreu(Float preu) {
		this.preu = preu;
	}
	public Integer getMinAssistents() {
		return minAssistents;
	}
	public void setMinAssistents(Integer minAssistents) {
		this.minAssistents = minAssistents;
	}
	public Integer getMaxAssistents() {
		return maxAssistents;
	}
	public void setMaxAssistents(Integer maxAssistents) {
		this.maxAssistents = maxAssistents;
	}
	public String getLloc() {
		return lloc;
	}
	public void setLloc(String lloc) {
		this.lloc = lloc;
	}
	public String getPuntDeTrobada() {
		return puntDeTrobada;
	}
	public void setPuntDeTrobada(String puntDeTrobada) {
		this.puntDeTrobada = puntDeTrobada;
	}
	public Time getHoraDeTrobada() {
		return horaDeTrobada;
	}
	public void setHoraDeTrobada(Time horaDeTrobada) {
		this.horaDeTrobada = horaDeTrobada;
	}
	public String getTextPerClient() {
		return textPerClient;
	}
	public void setTextPerClient(String textPerClient) {
		this.textPerClient = textPerClient;
	}
	public String getIdInstructor() {
		return idInstructor;
	}
	public void setIdInstructor(String idInstructor) {
		this.idInstructor = idInstructor;
	}
	public String getNomTipusActivitat() {
		return nomTipusActivitat;
	}
	public void setNomTipusActivitat(String nomTipusActivitat) {
		this.nomTipusActivitat = nomTipusActivitat;
	}
	
}
