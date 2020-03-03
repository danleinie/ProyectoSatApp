package com.example.proyectosataapp.repository;

import android.widget.Toast;

import androidx.lifecycle.MutableLiveData;

import com.example.proyectosataapp.common.Constantes;
import com.example.proyectosataapp.common.MyApp;
import com.example.proyectosataapp.common.SharedPreferencesManager;
import com.example.proyectosataapp.models.EquipoResponse;
import com.example.proyectosataapp.models.RequestEquipo;
import com.example.proyectosataapp.services.EquipoService;
import com.example.proyectosataapp.servicesGenerators.ServiceGenerator;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EquipoRepository {

    EquipoService service;
    ServiceGenerator serviceGenerator;
    MutableLiveData<List<EquipoResponse>> equipoList;


    public EquipoRepository(){
        service = serviceGenerator.createService(EquipoService.class);
        equipoList = null;
    }


    public MutableLiveData<List<EquipoResponse>> getSeriesPopulares(){
        final MutableLiveData<List<EquipoResponse>> data = new MutableLiveData<>();

        Call<List<EquipoResponse>> call = service.listEquipo(SharedPreferencesManager.getStringValue(Constantes.LABEL_TOKEN));

        call.enqueue(new Callback<List<EquipoResponse>>() {
            @Override
            public void onResponse(Call<List<EquipoResponse>> call, Response<List<EquipoResponse>> response) {
                if (response.isSuccessful()) {
                    data.setValue(response.body());

                } else {
                    Toast.makeText(MyApp.getCtx(), "Error on the response from the Api", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<EquipoResponse>> call, Throwable t) {
                Toast.makeText(MyApp.getCtx(), "Error in the connection", Toast.LENGTH_SHORT).show();
            }
        });
        return data;
    }

    public void deleteEquipo(final int id){
        Call<EquipoResponse> call = service.eliminarEquipo(SharedPreferencesManager.getStringValue(Constantes.LABEL_TOKEN),id);

        call.enqueue(new Callback<EquipoResponse>() {
            @Override
            public void onResponse(Call<EquipoResponse> call, Response<EquipoResponse> response) {
                if(response.isSuccessful()){
                    List<EquipoResponse> clonedEquipos = new ArrayList<>();
                    for(int i = 0; i<equipoList.getValue().size(); i++){
                            if(equipoList.getValue().get(i).getId()  != id){

                                clonedEquipos.add(new EquipoResponse(equipoList.getValue().get(i)));
                            }
                    }

                    equipoList.setValue(clonedEquipos);
                }

                else{
                    Toast.makeText(MyApp.getCtx(),"Algo ha ido mal)",Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<EquipoResponse> call, Throwable t) {
                Toast.makeText(MyApp.getCtx(),"Error ocurrido!)",Toast.LENGTH_SHORT).show();
            }
        });


    }


    public void createEquipo(String url,String ubicacion,String nombre, String tipo,String descripcion){

        RequestEquipo requestEquipo = new RequestEquipo(url,ubicacion,nombre,descripcion,tipo);
        Call<EquipoResponse> call = service.crearEquipo(requestEquipo);

        call.enqueue(new Callback<EquipoResponse>() {
            @Override
            public void onResponse(Call<EquipoResponse> call, Response<EquipoResponse> response) {
                if(response.isSuccessful()){
                    List<EquipoResponse> listaClonada = new ArrayList<>();
                    listaClonada.add(response.body());
                    for(int  i = 0; i< equipoList.getValue().size(); i++){
                        listaClonada.add(new EquipoResponse(equipoList.getValue().get(i)));
                    }
                    equipoList.setValue(listaClonada);
                }else{
                    Toast.makeText(MyApp.getCtx(),"Algo ha ido mal",Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<EquipoResponse> call, Throwable t) {
                Toast.makeText(MyApp.getCtx(),"Error ocurrido!)",Toast.LENGTH_SHORT).show();
            }
        });

    }

    public void createEquipo(String ubicacion,String nombre, String tipo,String descripcion){

        RequestEquipo requestEquipo = new RequestEquipo(ubicacion,nombre,descripcion,tipo);
        Call<EquipoResponse> call = service.crearEquipo(requestEquipo);

        call.enqueue(new Callback<EquipoResponse>() {
            @Override
            public void onResponse(Call<EquipoResponse> call, Response<EquipoResponse> response) {
                if(response.isSuccessful()){
                    List<EquipoResponse> listaClonada = new ArrayList<>();
                    listaClonada.add(response.body());
                    for(int  i = 0; i< equipoList.getValue().size(); i++){
                        listaClonada.add(new EquipoResponse(equipoList.getValue().get(i)));
                    }
                    equipoList.setValue(listaClonada);
                }else{
                    Toast.makeText(MyApp.getCtx(),"Algo ha ido mal",Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<EquipoResponse> call, Throwable t) {
                Toast.makeText(MyApp.getCtx(),"Error ocurrido!)",Toast.LENGTH_SHORT).show();
            }
        });

    }
    public void editarEquipo(RequestEquipo requestEquipo){
        Call<EquipoResponse> call = service.editarEquipo(requestEquipo);
        call.enqueue(new Callback<EquipoResponse>() {
            @Override
            public void onResponse(Call<EquipoResponse> call, Response<EquipoResponse> response) {
                if (response.isSuccessful()) {
                    List<EquipoResponse> listaClonada = new ArrayList<>();
                    listaClonada.add(response.body());
                    for (int i = 0; i < equipoList.getValue().size(); i++) {
                        listaClonada.add(new EquipoResponse(equipoList.getValue().get(i)));
                    }
                    equipoList.setValue(listaClonada);
                } else {
                    Toast.makeText(MyApp.getCtx(), "Algo ha ido mal", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<EquipoResponse> call, Throwable t) {
                Toast.makeText(MyApp.getCtx(),"Error ocurrido!)",Toast.LENGTH_SHORT).show();
            }
        });
    }



}
