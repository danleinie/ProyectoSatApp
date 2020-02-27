package com.example.proyectosataapp.services;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface TicketService {

    @Multipart
    @POST("/ticket")
    Call<com.example.proyectosatapp.models.Ticket> register(@Part("titulo") RequestBody titulo,
                                                            @Part("descripcion") RequestBody descripcion,
                                                            @Part("tecnico") RequestBody tecnico,
                                                            @Part MultipartBody.Part fotos);
}
