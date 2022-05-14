package com.politecnico.meapunto.modelos;

public class Pista {

    private String id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Pista(String id, String nombre) {
        this.id = id;
        this.nombre = nombre;
    }

    private String nombre;

}
