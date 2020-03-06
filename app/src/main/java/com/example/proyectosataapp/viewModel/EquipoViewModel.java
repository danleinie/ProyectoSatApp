package com.example.proyectosataapp.viewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;


import com.example.proyectosataapp.models.EquipoResponse;
import com.example.proyectosataapp.models.RequestEquipo;
import com.example.proyectosataapp.models.Ticket;
import com.example.proyectosataapp.repository.EquipoRepository;
import com.example.proyectosataapp.repository.TicketRepository;


import java.util.List;

public class EquipoViewModel extends AndroidViewModel {

    MutableLiveData<List<EquipoResponse>> equipos;
    EquipoRepository equipoRepository;
    MutableLiveData<String> idEquipoSeleccionado;
    TicketRepository ticketRepository;
 //   MutableLiveData<String> idTicketSeleccionado;

    public EquipoViewModel(@NonNull Application application) {
        super(application);
        equipoRepository = new EquipoRepository();
        ticketRepository = new TicketRepository();
        this.idEquipoSeleccionado =new MutableLiveData<>();
        this.idEquipoSeleccionado.setValue(null);
      /*  this.idTicketSeleccionado = new MutableLiveData<>();
        this.idTicketSeleccionado.setValue(null);*/
    }

    public MutableLiveData<List<EquipoResponse>> getEquipos() {
        equipos = equipoRepository.getEquipos();
        return equipos;
    }
    public void insertEquipo(String url,String ubicacion,String nombre, String tipo,String descripcion){
        equipoRepository.createEquipo(url,ubicacion,nombre,tipo,descripcion);
    }
    public void deleteEquipo(String id){
        equipoRepository.deleteEquipo(id);
    }

    public void setIdEquipoSeleccionado(String idEquipoSeleccionado) {
        this.idEquipoSeleccionado.setValue(idEquipoSeleccionado);
    }

    public MutableLiveData<String> getIdEquipoSeleccionado() {
        return idEquipoSeleccionado;
    }

    public MutableLiveData<List<String>> getUbicaciones(){
        return equipoRepository.getUbicaciones();
    }

    public void insertEquipo(String ubicacion, String nombre, String tipo, String descripcion) {
        equipoRepository.createEquipo(ubicacion,nombre,tipo,descripcion);
    }

    // By Álvaro Márquez
    public LiveData<List<Ticket>> getTicketsByInventariable(String id) {
        return ticketRepository.getTicketsByInventariable(id);
    }

}
