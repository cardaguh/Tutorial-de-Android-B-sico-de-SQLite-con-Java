package com.jikansoftware.model;

import java.io.Serializable;

public class Empleado implements Serializable {
    private static final long serialVersionUID = 1L;
    private long id;
    private String nombre;


    //Los constructore de la clase de los setters y getters
    public Empleado() {

    }

    public Empleado(long id, String nombre) {
        this.id = id;
        this.nombre = nombre;
    }

    public Empleado(String nombre){
        this.nombre = nombre;
    }

    @Override
    public String toString(){ return nombre; }

    public long getId(){ return id; }

    public void setId(long id) { this.id = id; }

    public String getNombre(){ return nombre; }

    public void setNombre(String nombre) { this.nombre = nombre; }
}
