package com.politecnico.meapunto.modelos;

import java.sql.Date;

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

    public String getFecha_nacimiento() {
        return fecha_nacimiento;
    }

    public void setFecha_nacimiento(String fecha_nacimiento) {
        this.fecha_nacimiento = fecha_nacimiento;
    }

    private String fecha_nacimiento;




    public Usuario(String dni, String nombre, String apellidos, String direccion, String telefono, String correo, String descripcion, String contraseña, String genero, String nivelDeJuego, String preferencia,String FechaNacimiento) {
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
        this.fecha_nacimiento = FechaNacimiento;
    }
}



//todo añadir facebook y twitter
// boton google recoger datos usuario
// splash screen  login usuario que se vea mas tiempo y cambiar diseño

// rellenar informacion de contacto

// editar perfil acabar sql
// bloquear 2 semanas dede hoy en la reserva
// dia y pista consultar que horas estan librbes y las que no este bloquearlas
// acabar reserva usuario


////añadir un jugador a un partido
// ver los detalles de los jugadores en un partido
//modificar la reserva de jugador

//añadir un jugador > si ya estan 4 modificar estado de partido en agenda