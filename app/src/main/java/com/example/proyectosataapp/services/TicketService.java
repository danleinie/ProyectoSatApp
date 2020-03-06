package com.example.proyectosataapp.services;

import com.example.proyectosataapp.models.Ticket;

import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface TicketService {

    @Multipart
    @POST("/ticket")
    Call<Ticket> postTicket(@Part("titulo") RequestBody titulo,
                          @Part("descripcion") RequestBody descripcion,
                          @Part("tecnico_id") RequestBody tecnico_id,
                          @Part("inventariable") RequestBody inventariable,
                          @Part MultipartBody.Part fotos);

    @GET("/ticket")
    Call<List<Ticket>> getAllTickets();

    @GET("/ticket/inventariable/{id}")
    Call<List<Ticket>> getTicketsByInventariable(@Path("id") String idInventariable);

    @GET("/ticket/{id}")
    Call<Ticket> getTicketById(@Path("id") String idTicket);

}
