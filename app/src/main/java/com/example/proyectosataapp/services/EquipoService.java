package com.example.proyectosataapp.services;


import android.net.Uri;

import com.example.proyectosataapp.models.RequestEquipo;

import com.example.proyectosataapp.models.EquipoResponse;
import com.example.proyectosataapp.models.UserResponseRegister;

import java.io.File;
import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.ResponseBody;
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
    Call<List<EquipoResponse>> listEquipo (@Query("sort") List<String> atributo,@Query("access_token") String masterKey);

    @GET("inventariable/img/{id}")
    Call<ResponseBody> imagenEquipo(@Path("id") String idImag, @Query("access_token") String masterKey);

    @GET("/inventariable/{id}")
    Call<EquipoResponse> getEquipo(@Path("id") String idEquipo, @Query("access_token") String masterKey);

    @GET("/inventariable/ubicaciones")
    Call<List<String>> getUbicaciones(@Query("access_token") String masterKey);
    
    @DELETE("inventariable/{id}") //TODO el acces token es momentaneo
    Call<EquipoResponse> eliminarEquipo(@Query("access_token") String masterKey,@Path("id") String id);

    @POST("inventariable/{id}")
    Call<EquipoResponse> crearEquipo(@Body RequestEquipo requestEquipo);

    @PUT("inventariable/{id}")
    Call<EquipoResponse> editarEquipo(@Body RequestEquipo requestEquipo);

    @Multipart
    @PUT("/inventariable/{id}/img")
    Call<ResponseBody> putInventariableImg(@Path("id") String idEquipo,
                                           @Part MultipartBody.Part imagen);

    @DELETE("/inventariable/{id}/img")
    Call<EquipoResponse> borrarFotoEquipo(@Path("id") String idEquipo);
}
