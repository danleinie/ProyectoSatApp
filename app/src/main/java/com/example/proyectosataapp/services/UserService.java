package com.example.proyectosataapp.services;

import com.example.proyectosataapp.models.DtoName;
import com.example.proyectosataapp.models.PasswordRequest;
import com.example.proyectosataapp.models.UserResponseLogin;
import com.example.proyectosataapp.models.UserResponseRegister;

import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.Path;
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

    @GET("/users")
    Call<List<UserResponseRegister>> getUsers();

    @GET("/users/no-validated")
    Call<List<UserResponseRegister>> getUsersNoValidated();

    @GET("/users/img/{id}")
    Call<ResponseBody> getImage(@Path("id") String idUser);

    @PUT("/users/{id}/validate")
    Call<UserResponseRegister> validarUsuario(@Path("id") String idUser);

    @DELETE("/users/{id}")
    Call<Void> deleteUsuario(@Path("id") String idUser);

    @GET("/users/{id}")
    Call<UserResponseRegister> getUser(@Path("id") String idUser);

    @PUT("/users/{id}/tecnico")
    Call<UserResponseRegister> changeToTecnico(@Path("id") String idUser);

    @GET("/users/me")
    Call<UserResponseRegister> getUserLogeado();

    @Multipart
    @PUT("/users/{id}/img")
    Call<UserResponseRegister> updatePhoto(@Path("id")String id,
                                    @Part MultipartBody.Part avatar);

    @DELETE("/users/{id}/img")
    Call<UserResponseRegister> deletePhoto(@Path("id") String idUser);

    @PUT("/users/{id}")
    Call<UserResponseRegister> changeName(@Path("id")String idUser,
                                @Body DtoName name);

    @PUT("/users/{id}/password")
    Call<UserResponseRegister> updatePassword(@Header("Authorization") String authHeader ,@Path("id")String idUser, @Body PasswordRequest password);
}
