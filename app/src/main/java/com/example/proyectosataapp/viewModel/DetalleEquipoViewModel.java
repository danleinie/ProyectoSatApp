package com.example.proyectosataapp.viewModel;



import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.proyectosataapp.models.EquipoResponse;
import com.example.proyectosataapp.models.Ticket;
import com.example.proyectosataapp.repository.EquipoRepository;
import com.example.proyectosataapp.repository.TicketRepository;

import java.util.List;

public class DetalleEquipoViewModel extends AndroidViewModel {

    MutableLiveData<EquipoResponse> equipo;
    EquipoRepository equipoRepository;
    TicketRepository ticketRepository;

    public DetalleEquipoViewModel(@NonNull Application application) {
        super(application);
        equipoRepository = new EquipoRepository();
    }

    public MutableLiveData<EquipoResponse> getEquipo(String idEquipo) {
        equipo = equipoRepository.getEquipo(idEquipo);
        return equipo;
    }

    // By Álvaro Márquez
    public LiveData<List<Ticket>> getTicketsByInventariable(String id) {
        return ticketRepository.getTicketsByInventariable(id);
    }


}

