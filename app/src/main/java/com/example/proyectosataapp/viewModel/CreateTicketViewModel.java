package com.example.proyectosataapp.viewModel;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;

import com.example.proyectosataapp.models.Ticket;
import com.example.proyectosataapp.models.User;
import com.example.proyectosataapp.models.UserResponseRegister;
import com.example.proyectosataapp.repository.EquipoRepository;
import com.example.proyectosataapp.repository.TicketRepository;
import com.example.proyectosataapp.usuarios.UsuarioRepository;

import java.util.ArrayList;
import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;

public class CreateTicketViewModel extends AndroidViewModel {

    private EquipoRepository equipoRepository;
    private UsuarioRepository usuarioRepository;
    private TicketRepository ticketRepository;

    public CreateTicketViewModel(@NonNull Application application) {
        super(application);
        equipoRepository = new EquipoRepository();
        usuarioRepository = new UsuarioRepository();
        ticketRepository = new TicketRepository();
    }

    public LiveData<List<UserResponseRegister>> getUsers() { return usuarioRepository.getUsers();}


    public LiveData<Ticket> postTicket(
            RequestBody titulo,
            RequestBody descripcion,
            RequestBody tecnico_id,
            RequestBody inventariable,
            MultipartBody.Part fotos
    ) {
        return ticketRepository.postTicket(
                        titulo,
                        descripcion,
                        tecnico_id,
                        inventariable,
                        fotos
                );
    }

}
