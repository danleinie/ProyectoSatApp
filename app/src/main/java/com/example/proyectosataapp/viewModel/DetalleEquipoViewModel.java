package com.example.proyectosataapp.viewModel;



import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.example.proyectosataapp.models.EquipoResponse;
import com.example.proyectosataapp.models.RequestEquipo;
import com.example.proyectosataapp.repository.EquipoRepository;

public class DetalleEquipoViewModel extends AndroidViewModel {

    MutableLiveData<EquipoResponse> equipo;
    EquipoRepository equipoRepository;

    public DetalleEquipoViewModel(@NonNull Application application) {
        super(application);
        equipoRepository = new EquipoRepository();
    }

    public MutableLiveData<EquipoResponse> getEquipo(String idEquipo) {
        equipo = equipoRepository.getEquipo(idEquipo);
        return equipo;
    }

    public void deleteEquipo(String idEquipo){

        equipoRepository.deleteEquipo(idEquipo);
    }

    public void editEquipo(String id,String ubicacion, String nombre, String tipo, String descripcion){
        RequestEquipo equipo = new RequestEquipo(ubicacion,nombre,tipo,descripcion);
        equipoRepository.editarEquipo(id,equipo);
    }



}

