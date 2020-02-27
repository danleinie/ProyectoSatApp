package com.example.proyectosataapp.repository;

import android.widget.Toast;

import androidx.lifecycle.MutableLiveData;

import com.example.proyectosataapp.common.Constantes;
import com.example.proyectosataapp.common.MyApp;
import com.example.proyectosataapp.common.SharedPreferencesManager;
import com.example.proyectosataapp.models.Equipo;
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
    MutableLiveData<List<Equipo>> equipoList;


    public EquipoRepository(){
        service = serviceGenerator.createService(EquipoService.class);
        equipoList = null;
    }


    public MutableLiveData<List<Equipo>> getSeriesPopulares(){
        final MutableLiveData<List<Equipo>> data = new MutableLiveData<>();

        Call<List<Equipo>> call = service.listEquipo(SharedPreferencesManager.getStringValue(Constantes.LABEL_TOKEN));

        call.enqueue(new Callback<List<Equipo>>() {
            @Override
            public void onResponse(Call<List<Equipo>> call, Response<List<Equipo>> response) {
                if (response.isSuccessful()) {
                    data.setValue(response.body());

                } else {
                    Toast.makeText(MyApp.getCtx(), "Error on the response from the Api", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<Equipo>> call, Throwable t) {
                Toast.makeText(MyApp.getCtx(), "Error in the connection", Toast.LENGTH_SHORT).show();
            }
        });
        return data;
    }

    public void deleteEquipo(final int id){
        Call<Equipo> call = service.eliminarEquipo(SharedPreferencesManager.getStringValue(Constantes.LABEL_TOKEN),id);

        call.enqueue(new Callback<Equipo>() {
            @Override
            public void onResponse(Call<Equipo> call, Response<Equipo> response) {
                if(response.isSuccessful()){
                    List<Equipo> clonedEquipos = new ArrayList<>();
                    for(int i = 0; i<equipoList.getValue().size(); i++){
                            if(equipoList.getValue().get(i).getId()  != id){

                                clonedEquipos.add(new Equipo(equipoList.getValue().get(i)));
                            }
                    }

                    equipoList.setValue(clonedEquipos);
                }

                else{
                    Toast.makeText(MyApp.getCtx(),"Algo ha ido mal)",Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Equipo> call, Throwable t) {

            }
        });


    }


    public void createEquipo(String url,String ubicacion,String nombre, String tipo,String descripcion){

        RequestEquipo requestEquipo = new RequestEquipo(url,ubicacion,nombre,descripcion,tipo);
        Call<Equipo> call = service.crearEquipo(requestEquipo);

        call.enqueue(new Callback<Equipo>() {
            @Override
            public void onResponse(Call<Equipo> call, Response<Equipo> response) {
                if(response.isSuccessful()){

                }else{
                    Toast.makeText(MyApp.getCtx(),"Algo ha ido mal",Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Equipo> call, Throwable t) {

            }
        });

    }


}
