package com.example.proyectosataapp.viewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.proyectosataapp.models.Ticket;
import com.example.proyectosataapp.repository.TicketRepository;
import com.example.proyectosataapp.tickets.TicketAdapter;

import java.util.List;

import okhttp3.RequestBody;

public class TicketViewModel extends AndroidViewModel {

    private TicketRepository repositorio;

    public TicketViewModel(@NonNull Application application) {
        super(application);
        repositorio = new TicketRepository();
    }

    public LiveData<Ticket> postTicket(RequestBody titulo, RequestBody descripcion) {return repositorio.postTicket(titulo, descripcion);}

    public LiveData<List<Ticket>> getTickets() {return repositorio.getTickets();}


}
