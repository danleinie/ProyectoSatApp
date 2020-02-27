package com.example.proyectosataapp.services;

import com.example.proyectosataapp.models.Equipo;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface EquipoService {

    @GET("inventariable")
    Call<List<Equipo>> listEquipo (@Query("access_token") String masterKey);

    @GET("/inventariable/img/{id}")
    Call<String> imagenEquipo(@Query("access_token") String masterKey, @Query("id") String idImag);
}