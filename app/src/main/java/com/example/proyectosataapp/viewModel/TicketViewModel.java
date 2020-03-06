package com.example.proyectosataapp.viewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.proyectosataapp.models.Ticket;
import com.example.proyectosataapp.models.UserResponseRegister;
import com.example.proyectosataapp.repository.TicketRepository;
import com.example.proyectosataapp.tickets.TicketAdapter;
import com.example.proyectosataapp.usuarios.UsuarioRepository;

import java.util.List;

import okhttp3.RequestBody;
import okhttp3.ResponseBody;

public class TicketViewModel extends AndroidViewModel {

    private TicketRepository repositorio;
    private UsuarioRepository usuarioRepository;
    private MutableLiveData<List<Ticket>> ticketsCreadosPorMi = new MutableLiveData<>();

    public TicketViewModel(@NonNull Application application) {
        super(application);
        repositorio = new TicketRepository();
        usuarioRepository = new UsuarioRepository();
    }

    public LiveData<List<Ticket>> getTickets() {return repositorio.getTickets();}

    public LiveData<List<Ticket>> getTicketsAsignadosAMi() {return repositorio.getTicketsAsignadosAMi();}

    public LiveData<Ticket> getTicketById(String ticketId) {return repositorio.getTicketById(ticketId);}

    public LiveData<UserResponseRegister> getTecnicoById(String tecnicoId) {return usuarioRepository.getUser(tecnicoId);}

    public LiveData<ResponseBody> getTicketImg(String id, String img) {return repositorio.getImgTicket(id, img);}

    public LiveData<List<Ticket>> getTicketsCreadosPorMi() {return repositorio.getTicketsCreadosPorMi();}

    public LiveData<List<Ticket>> getTicketsCreadosPorMiEnLocal() {return this.ticketsCreadosPorMi;}

    public void setTicketsCreadosPorMi(List<Ticket> listTickets) {this.ticketsCreadosPorMi.postValue(listTickets);}


}
