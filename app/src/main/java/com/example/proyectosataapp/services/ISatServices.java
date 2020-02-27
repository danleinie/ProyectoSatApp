package com.example.proyectosataapp.services;

import com.example.proyectosataapp.models.Equipo;

import java.util.List;

import lombok.Getter;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface ISatServices {

    @GET ("/inventariable")
    Call<List<Equipo>> listEquipos();
}
