package com.example.proyectosataapp.repository;

import android.content.SharedPreferences;
import android.util.Log;
import android.widget.Toast;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.proyectosataapp.common.Constantes;
import com.example.proyectosataapp.common.MyApp;
import com.example.proyectosataapp.common.SharedPreferencesManager;
import com.example.proyectosataapp.models.Ticket;
import com.example.proyectosataapp.services.TicketService;
import com.example.proyectosataapp.servicesGenerators.ServiceGenerator;
import com.example.proyectosataapp.tickets.TicketAdapter;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TicketRepository {

    private TicketService servicio;
    private LiveData<List<Ticket>> listaTickets;

    public TicketRepository() {
        servicio = ServiceGenerator.createService(TicketService.class, SharedPreferencesManager.getStringValue(Constantes.LABEL_TOKEN));
    }

    public LiveData<List<Ticket>> getTickets() {
        final MutableLiveData<List<Ticket>> data = new MutableLiveData<>();

        Call<List<Ticket>> call = servicio.getAllTickets();
        call.enqueue(new Callback<List<Ticket>>() {
            @Override
            public void onResponse(Call<List<Ticket>> call, Response<List<Ticket>> response) {
                if (response.isSuccessful()) {
                    Log.d("Response", response.body() + "");
                    data.setValue(response.body());
                    Log.d("data.getValue()", data.getValue().toString() + "");
                } else {
                    Toast.makeText(MyApp.getCtx(), "Algo ha ido mal.", Toast.LENGTH_SHORT).show();
                    Log.d("Lista Tickets", "Respuesta mal recibida");
                }
            }

            @Override
            public void onFailure(Call<List<Ticket>> call, Throwable t) {
                Toast.makeText(MyApp.getCtx(), "Algo ha ido mal.", Toast.LENGTH_SHORT).show();
                Log.d("Lista Tickets", "Fallo en la peticion");
            }
        });

        return data;
    }


}
