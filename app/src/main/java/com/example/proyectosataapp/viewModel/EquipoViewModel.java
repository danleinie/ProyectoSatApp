package com.example.proyectosataapp.viewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.example.proyectosataapp.models.ResponseEquipo;
import com.example.proyectosataapp.repository.EquipoRepository;


import java.util.List;

public class EquipoViewModel extends AndroidViewModel {

    MutableLiveData<List<ResponseEquipo>> series;
    EquipoRepository equipoRepository;
    MutableLiveData<Integer> idEquipoSeleccionado;

    public EquipoViewModel(@NonNull Application application) {
        super(application);
        equipoRepository = new EquipoRepository();
        this.idEquipoSeleccionado =new MutableLiveData<>();
        this.idEquipoSeleccionado.setValue(null);
    }

    public MutableLiveData<List<ResponseEquipo>> getEquipos() {
        series = equipoRepository.getSeriesPopulares();
        return series;
    }



}
