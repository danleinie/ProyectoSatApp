package com.example.proyectosataapp.services;


import com.example.proyectosataapp.models.EquipoResponse;
import com.example.proyectosataapp.models.RequestEquipo;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface EquipoService {

    @GET("inventariable")
    Call<List<EquipoResponse>> listEquipo (@Query("access_token") String masterKey);

    @DELETE("inventariable/{id}") //TODO el acces token es momentaneo
    Call<EquipoResponse> eliminarEquipo(@Query("access_token") String masterKey,@Path("id") int id);

    @POST("inventariable/{id}")
    Call<EquipoResponse> crearEquipo(@Body RequestEquipo requestEquipo);

    @PUT("inventariable/{id}")
    Call<EquipoResponse> editarEquipo(@Body RequestEquipo requestEquipo);
}
