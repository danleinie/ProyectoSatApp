package com.example.proyectosataapp.services;

import com.example.proyectosataapp.models.EquipoResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface EquipoService {

    @GET("inventariable")
    Call<List<EquipoResponse>> listEquipo (@Query("access_token") String masterKey);

    @GET("inventariable/img/{id}")
    Call<String> imagenEquipo( @Path("id") String idImag, @Query("access_token") String masterKey);

    @GET("/inventariable/{id}")
    Call<EquipoResponse> getEquipo(@Path("id") String idEquipo, @Query("access_token") String masterKey);

}
