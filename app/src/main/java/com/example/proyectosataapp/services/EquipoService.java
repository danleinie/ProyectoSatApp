package com.example.proyectosataapp.services;

import android.net.Uri;

import com.example.proyectosataapp.models.EquipoResponse;

import java.io.File;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface EquipoService {

    @GET("inventariable")
    Call<List<EquipoResponse>> listEquipo (@Query("access_token") String masterKey);

    @GET("inventariable/img/{id}")
    Call<ResponseBody> imagenEquipo(@Path("id") String idImag, @Query("access_token") String masterKey);

    @GET("/inventariable/{id}")
    Call<EquipoResponse> getEquipo(@Path("id") String idEquipo, @Query("access_token") String masterKey);

    @GET("/inventariable/ubicaciones")
    Call<List<String>> getUbicaciones(@Query("access_token") String masterKey);
}
