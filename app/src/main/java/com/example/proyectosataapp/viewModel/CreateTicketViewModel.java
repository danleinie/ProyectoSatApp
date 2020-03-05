package com.example.proyectosataapp.viewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.proyectosataapp.models.User;
import com.example.proyectosataapp.models.UserResponseRegister;
import com.example.proyectosataapp.repository.EquipoRepository;
import com.example.proyectosataapp.repository.TicketRepository;
import com.example.proyectosataapp.usuarios.UsuarioRepository;

import java.util.ArrayList;
import java.util.List;

public class CreateTicketViewModel extends AndroidViewModel {

    private EquipoRepository equipoRepository;
    private UsuarioRepository usuarioRepository;

    public CreateTicketViewModel(@NonNull Application application) {
        super(application);
        equipoRepository = new EquipoRepository();
        usuarioRepository = new UsuarioRepository();
    }

    public List<UserResponseRegister> getTecnicos() {
        List<UserResponseRegister> todos = usuarioRepository.getUsers().getValue();
        List<UserResponseRegister> tecnicos = new ArrayList<>();

        for (UserResponseRegister user : todos) {
            if (user.getRole() == "tecnico")
                tecnicos.add(user);
        }

        return tecnicos;
    }
}
