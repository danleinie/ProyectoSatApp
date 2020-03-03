package com.example.proyectosataapp.viewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;


import com.example.proyectosataapp.models.EquipoResponse;
import com.example.proyectosataapp.models.RequestEquipo;
import com.example.proyectosataapp.repository.EquipoRepository;


import java.util.List;

public class EquipoViewModel extends AndroidViewModel {

    MutableLiveData<List<EquipoResponse>> equipos;
    EquipoRepository equipoRepository;
    MutableLiveData<Integer> idEquipoSeleccionado;

    public EquipoViewModel(@NonNull Application application) {
        super(application);
        equipoRepository = new EquipoRepository();
        this.idEquipoSeleccionado =new MutableLiveData<>();
        this.idEquipoSeleccionado.setValue(null);
    }

    public MutableLiveData<List<EquipoResponse>> getEquipos() {
        equipos = equipoRepository.getSeriesPopulares();
        return equipos;
    }
    public void insertEquipo(String url,String ubicacion,String nombre, String tipo,String descripcion){
        equipoRepository.createEquipo(url,ubicacion,nombre,tipo,descripcion);
    }
    public void deleteEquipo(int id){

        equipoRepository.deleteEquipo(id);
    }


    public void insertEquipo(String ubicacion, String nombre, String tipo, String descripcion) {
        equipoRepository.createEquipo(ubicacion,nombre,tipo,descripcion);
    }
    public void editarEquipo(RequestEquipo requestEquipo){
        equipoRepository.editarEquipo(requestEquipo);
    }
}
