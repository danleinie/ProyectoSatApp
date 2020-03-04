package com.example.proyectosataapp.services;


import android.net.Uri;

import com.example.proyectosataapp.models.RequestEquipo;

import com.example.proyectosataapp.models.EquipoResponse;
import com.example.proyectosataapp.models.UserResponseRegister;

import java.io.File;
import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.Part;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface EquipoService {

    @GET("inventariable")
    Call<List<EquipoResponse>> listEquipo (@Query("access_token") String masterKey);

    @GET("inventariable/img/{id}")
    Call<Uri> imagenEquipo(@Path("id") String idImag, @Query("access_token") String masterKey);

    @GET("/inventariable/{id}")
    Call<EquipoResponse> getEquipo(@Path("id") String idEquipo, @Query("access_token") String masterKey);

    @DELETE("/inventariable/{id}") //TODO el acces token es momentaneo
    Call<EquipoResponse> eliminarEquipo(@Path("id") String id);


    Call<EquipoResponse> crearEquipo(@Body RequestEquipo requestEquipo);

    @Multipart
    @POST("/inventariable")
    Call<EquipoResponse> crearEquipo(@Part("nombre") RequestBody nombre,
                                        @Part("descripcion") RequestBody descripcion,
                                        @Part("ubicacion") RequestBody ubicacion,
                                        @Part("tipo") RequestBody tipo,
                                        @Part MultipartBody.Part imagen
    );


    @PUT("/inventariable/{id}")
    Call<EquipoResponse> editarEquipo(@Path("id") String idEquipo,@Body RequestEquipo requestEquipo);
}
