package com.example.proyectosataapp.services;

import com.example.proyectosataapp.models.UserResponseLogin;
import com.example.proyectosataapp.models.UserResponseRegister;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;

public interface UserService {

    @POST("/auth")
    Call<UserResponseLogin> logIn(@Header("Authorization") String authHeader, @Query("access_token") String masterKey);

    @Multipart
    @POST("/users")
    Call<UserResponseRegister> register(@Query("access_token") String masterKey,
                                        @Part("email") RequestBody email,
                                        @Part("password") RequestBody password,
                                        @Part("name") RequestBody name,
                                        @Part MultipartBody.Part avatar
                                        );

}
