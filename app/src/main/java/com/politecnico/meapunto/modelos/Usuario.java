package com.politecnico.meapunto.modelos;

public class Usuario {
    public String getDNI() {
        return DNI;
    }

    public void setDNI(String DNI) {
        this.DNI = DNI;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getContraseña() {
        return contraseña;
    }

    public void setContraseña(String contraseña) {
        this.contraseña = contraseña;
    }

    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    public String getNivelDeJuego() {
        return nivelDeJuego;
    }

    public void setNivelDeJuego(String nivelDeJuego) {
        this.nivelDeJuego = nivelDeJuego;
    }

    public String getPreferencia() {
        return Preferencia;
    }

    public void setPreferencia(String preferencia) {
        Preferencia = preferencia;
    }

    private String DNI,nombre,apellidos,direccion,telefono,correo,descripcion, contraseña;

    private String genero;
    private String nivelDeJuego;
    private String Preferencia;




    public Usuario(String dni, String nombre, String apellidos, String direccion, String telefono, String correo, String descripcion, String contraseña, String genero, String nivelDeJuego, String preferencia) {
        this.DNI = dni;
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.direccion = direccion;
        this.telefono = telefono;
        this.correo = correo;
        this.descripcion = descripcion;
        this.contraseña = contraseña;
        this.genero = genero;
        this.nivelDeJuego = nivelDeJuego;
        this.Preferencia = preferencia;
    }
}
