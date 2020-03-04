package com.example.proyectosataapp.models;


import java.util.List;

public class EquipoResponse {
    private String id;
    private String nombre;
    private String tipo;
    private String descripcion;
    private String ubicacion;
    private String imagen;
    //Atributo propio para filtro
    private List<String> palabrasClaves;

    public EquipoResponse(String id, String nombre, String tipo, String descripcion, String ubicacion, String imagen) {
        this.id = id;
        this.nombre = nombre;
        this.tipo = tipo;
        this.descripcion = descripcion;
        this.ubicacion = ubicacion;
        this.imagen = imagen;
    }

    public EquipoResponse() {
    }

    public String getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public String getTipo() {
        return tipo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public String getUbicacion() {
        return ubicacion;
    }

    public String getImagen() {
        return imagen;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<String> getPalabrasClaves() {
        return palabrasClaves;
    }

    public void setPalabrasClaves(List<String> palabrasClaves) {
        this.palabrasClaves = palabrasClaves;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public void setUbicacion(String ubicacion) {
        this.ubicacion = ubicacion;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }
}
