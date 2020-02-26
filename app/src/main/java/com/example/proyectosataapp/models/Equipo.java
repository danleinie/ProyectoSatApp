package com.example.proyectosataapp.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class Equipo {
    private String nombre;
    private String tipo;
    private String descripcion;
    private String ubicacion;
    private String imagen;
}
