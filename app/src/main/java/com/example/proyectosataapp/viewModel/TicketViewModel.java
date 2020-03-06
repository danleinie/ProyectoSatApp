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

    public LiveData<List<Ticket>> getTickets() {return repositorio.getTickets();}



    public LiveData<Ticket> getTicketById(String ticketId) {return repositorio.getTicketById(ticketId);}


}
