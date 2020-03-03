package com.example.proyectosataapp.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class RequestEquipo {

    private String nombre;
    private String tipo;
    private String descripcion;
    private String ubicacion;
    private String imagen;



    public RequestEquipo(String nombre, String tipo, String descripcion, String ubicacion) {
        this.nombre = nombre;
        this.tipo = tipo;
        this.descripcion = descripcion;
        this.ubicacion = ubicacion;
    }

    public RequestEquipo(EquipoResponse equipoResponse) {
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getUbicacion() {
        return ubicacion;
    }

    public void setUbicacion(String ubicacion) {
        this.ubicacion = ubicacion;
    }

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }
}
