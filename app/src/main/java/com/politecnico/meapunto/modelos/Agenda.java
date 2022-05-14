package com.politecnico.meapunto.modelos;

public class Agenda {
   private int timeSlot,id_pista,disponible,id_partido;
   private String dia;

    public int getTimeSlot() {
        return timeSlot;
    }

    public void setTimeSlot(int timeSlot) {
        this.timeSlot = timeSlot;
    }

    public int getId_pista() {
        return id_pista;
    }

    public void setId_pista(int id_pista) {
        this.id_pista = id_pista;
    }

    public int getDisponible() {
        return disponible;
    }

    public void setDisponible(int disponible) {
        this.disponible = disponible;
    }

    public int getId_partido() {
        return id_partido;
    }

    public void setId_partido(int id_partido) {
        this.id_partido = id_partido;
    }

    public String getDia() {
        return dia;
    }

    public Agenda(int timeSlot, int id_pista, int disponible, int id_partido, String dia) {
        this.timeSlot = timeSlot;
        this.id_pista = id_pista;
        this.disponible = disponible;
        this.id_partido = id_partido;
        this.dia = dia;
    }

    public void setDia(String dia) {
        this.dia = dia;
    }
}
