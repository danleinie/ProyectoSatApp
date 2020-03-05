package com.example.proyectosataapp.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class RequestEquipo {

    private String nombre;
    private String descripcion;
    private String ubicacion;



    public RequestEquipo(EquipoResponse equipoResponse) {
    }

    public RequestEquipo(String ubicacion, String nombre, String descripcion) {
    }

    public RequestEquipo(String nombre, String tipo, String descripcion, String ubicacion, String imagen) {
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.ubicacion = ubicacion;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
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


}
