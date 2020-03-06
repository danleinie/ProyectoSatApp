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
    MutableLiveData<String> idEquipoSeleccionado;

    public EquipoViewModel(@NonNull Application application) {
        super(application);
        equipoRepository = new EquipoRepository();
        this.idEquipoSeleccionado =new MutableLiveData<>();
        this.idEquipoSeleccionado.setValue(null);
    }

    public MutableLiveData<List<EquipoResponse>> getEquipos() {
        equipos = equipoRepository.getEquipos();
        return equipos;
    }

    public void setIdEquipoSeleccionado(String idSerieSeleccionada) {
        this.idEquipoSeleccionado.setValue(idSerieSeleccionada);
    }

    public MutableLiveData<String> getIdEquipoSeleccionado() {
        return idEquipoSeleccionado;
    }

    public List<String> getTipos(){
        List<String> listaTipos = equipoRepository.getTipos();
        return listaTipos;
    }






}
