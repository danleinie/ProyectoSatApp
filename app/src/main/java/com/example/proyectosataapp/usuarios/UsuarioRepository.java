package com.example.proyectosataapp.usuarios;


import android.graphics.BitmapFactory;
import android.util.Log;
import android.widget.Toast;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;

import com.example.proyectosataapp.common.Constantes;
import com.example.proyectosataapp.common.MyApp;
import com.example.proyectosataapp.common.SharedPreferencesManager;
import com.example.proyectosataapp.models.UserResponseRegister;
import com.example.proyectosataapp.services.UserService;
import com.example.proyectosataapp.servicesGenerators.ServiceGenerator;

import java.util.ArrayList;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UsuarioRepository {

    private UserService service;
    private LiveData<List<UserResponseRegister>> listUsers;

    public UsuarioRepository(){
        service = ServiceGenerator.createService(UserService.class, SharedPreferencesManager.getStringValue(Constantes.LABEL_TOKEN));
    }

    public LiveData<List<UserResponseRegister>> getUsers(){
        final MutableLiveData<List<UserResponseRegister>> data = new MutableLiveData<>();

        Call<List<UserResponseRegister>> call = service.getUsers();
        call.enqueue(new Callback<List<UserResponseRegister>>() {
            @Override
            public void onResponse(Call<List<UserResponseRegister>> call, final Response<List<UserResponseRegister>> response) {
                if (response.isSuccessful()){
                    final List<UserResponseRegister> listUsuariosValidados = new ArrayList<>();
                    for (UserResponseRegister user: response.body()) {
                        if (user.getValidated()){
                            listUsuariosValidados.add(user);
                        }
                    }
                    for (int i = 0; i < listUsuariosValidados.size() ; i++) {
                        final int finalI = i;
                        getImg(listUsuariosValidados.get(i).getId()).observeForever(new Observer<ResponseBody>() {
                            @Override
                            public void onChanged(ResponseBody responseBody) {
                                listUsuariosValidados.get(finalI).setPictureBitMap(BitmapFactory.decodeStream(responseBody.byteStream()));
                                }
                        });
                    }
                    data.setValue(listUsuariosValidados);
                } else {
                    Log.i("listausers", "Error al traer la lista de usuarios");
                }
            }

            @Override
            public void onFailure(Call<List<UserResponseRegister>> call, Throwable t) {
                Log.i("listausers", "Error en el failure al traer lista de users : " + t.getMessage());
            }
        });

        return data;
    }

    public LiveData<List<UserResponseRegister>> getUsersNoValidated(){
        final MutableLiveData<List<UserResponseRegister>> data = new MutableLiveData<>();

        Call<List<UserResponseRegister>> call = service.getUsersNoValidated();
        call.enqueue(new Callback<List<UserResponseRegister>>() {
            @Override
            public void onResponse(Call<List<UserResponseRegister>> call, final Response<List<UserResponseRegister>> response) {
                if (response.isSuccessful()){
                    for (int i = 0; i < response.body().size() ; i++) {
                        final int finalI = i;
                        getImg(response.body().get(i).getId()).observeForever(new Observer<ResponseBody>() {
                            @Override
                            public void onChanged(ResponseBody responseBody) {
                                response.body().get(finalI).setPictureBitMap(BitmapFactory.decodeStream(responseBody.byteStream()));
                            }
                        });
                    }
                    data.setValue(response.body());
                } else {
                    data.setValue(new ArrayList<UserResponseRegister>());
                    Log.i("listausersnovalidados", "Error al traer la lista de usuarios no validados");
                }
            }

            @Override
            public void onFailure(Call<List<UserResponseRegister>> call, Throwable t) {
                Log.i("listausersnovalidados", "Error en el failure al traer lista de users no validados : " + t.getMessage());
            }
        });

        return data;
    }

    public LiveData<ResponseBody> getImg(String idUser){
        final MutableLiveData<ResponseBody> data = new MutableLiveData<>();

        Call<ResponseBody> call = service.getImage(idUser);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()){
                    data.setValue(response.body());
                }else {
                    Log.i("getimageuser","Error al traer la imagen de usuario");
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.i("getimageuser", "Error en failure de getimage");
            }
        });

        return data;
    }

    public LiveData<UserResponseRegister> validarUser(String idUser){
        final MutableLiveData<UserResponseRegister> data = new MutableLiveData<>();

        Call<UserResponseRegister> call = service.validarUsuario(idUser);
        call.enqueue(new Callback<UserResponseRegister>() {
            @Override
            public void onResponse(Call<UserResponseRegister> call, Response<UserResponseRegister> response) {
                if (response.isSuccessful()){
                    data.setValue(response.body());
                }else {
                    Toast.makeText(MyApp.getCtx(), "Error al validar usuario", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<UserResponseRegister> call, Throwable t) {
                Toast.makeText(MyApp.getCtx(), "Error en el failure al validar usuario", Toast.LENGTH_SHORT).show();
            }
        });
        return data;
    }

    public LiveData<Boolean> deleteUser(String idUser){
        final MutableLiveData<Boolean> data = new MutableLiveData<>();
        Call<Void> call = service.deleteUsuario(idUser);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()){
                    data.setValue(true);
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {

            }
        });
        return data;
    }

    public LiveData<UserResponseRegister> changeToTecnico(String idUser){
        final MutableLiveData<UserResponseRegister> data = new MutableLiveData<>();
        service.changeToTecnico(idUser).enqueue(new Callback<UserResponseRegister>() {
            @Override
            public void onResponse(Call<UserResponseRegister> call, Response<UserResponseRegister> response) {
                if (response.isSuccessful()){
                    data.setValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<UserResponseRegister> call, Throwable t) {

            }
        });
        return data;
    }

    public LiveData<UserResponseRegister> getUser(String idUser){
        final MutableLiveData<UserResponseRegister> data = new MutableLiveData<>();

        service.getUser(idUser).enqueue(new Callback<UserResponseRegister>() {
            @Override
            public void onResponse(Call<UserResponseRegister> call, Response<UserResponseRegister> response) {
                if (response.isSuccessful()){
                    data.setValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<UserResponseRegister> call, Throwable t) {

            }
        });

        return data;
    }

    public LiveData<UserResponseRegister> getUserLogeado(){
        final MutableLiveData<UserResponseRegister> data = new MutableLiveData<>();

        service.getUserLogeado().enqueue(new Callback<UserResponseRegister>() {
            @Override
            public void onResponse(Call<UserResponseRegister> call, Response<UserResponseRegister> response) {
                if (response.isSuccessful()){
                    data.setValue(response.body());
                    SharedPreferencesManager.setStringValue(Constantes.ROLE_USER_LOGEADO,response.body().getRole());
                }
            }

            @Override
            public void onFailure(Call<UserResponseRegister> call, Throwable t) {

            }
        });
        return data;
    }


}
