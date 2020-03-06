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

import java.io.IOException;
import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
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

    public LiveData<List<Ticket>> getTicketsByInventariable(String id) {
        final MutableLiveData<List<Ticket>> data = new MutableLiveData<>();

        Call<List<Ticket>> call = servicio.getTicketsByInventariable(id);
        call.enqueue(new Callback<List<Ticket>>() {
            @Override
            public void onResponse(Call<List<Ticket>> call, Response<List<Ticket>> response) {
                if (response.isSuccessful()) {
                    data.setValue(response.body());
                    Toast.makeText(MyApp.getCtx(), "Flama brother", Toast.LENGTH_SHORT).show();
                } else {
                    Log.e("Response", "Respuesta incorrecta");
                    Toast.makeText(MyApp.getCtx(), "Algo fue mal", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<Ticket>> call, Throwable t) {
                Log.e("Response", "Respuesta no realizada");
                Toast.makeText(MyApp.getCtx(), "Algo fue MUY mal", Toast.LENGTH_SHORT).show();
            }
        });

        return data;
    }

    public LiveData<Ticket> postTicket(RequestBody titulo, RequestBody descripcion, RequestBody tecnico_id, RequestBody inventariable, MultipartBody.Part fotos) {

        final MutableLiveData<Ticket> data = new MutableLiveData<>();

        try {
            if (tecnico_id.contentLength() == 0)
                tecnico_id = null;

            if (inventariable.contentLength() == 0)
                inventariable = null;

        } catch (IOException e) {e.printStackTrace();}

        Call<Ticket> call = servicio.postTicket(titulo, descripcion, tecnico_id, inventariable, fotos);

        call.enqueue(new Callback<Ticket>() {
            @Override
            public void onResponse(Call<Ticket> call, Response<Ticket> response) {
                if (response.isSuccessful()) {
                    data.setValue(response.body());
                    Toast.makeText(MyApp.getCtx(), "Perfecto bro \n" + data.getValue().toString(), Toast.LENGTH_SHORT).show();
                } else {
                    Log.e("Response", "Respuesta incorrecta");
                    Toast.makeText(MyApp.getCtx(), "Algo ha ido mal", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Ticket> call, Throwable t) {
                Log.e("Response", "Respuesta no realizada" + t.getMessage());
                Toast.makeText(MyApp.getCtx(), "Algo fue MUY mal" + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        return data;
    }

    public LiveData<List<Ticket>> getTicketsAsignadosAMi() {
        final MutableLiveData<List<Ticket>> data = new MutableLiveData<>();

        Call<List<Ticket>> call = servicio.getTicketsAsignadosAMi();
        call.enqueue(new Callback<List<Ticket>>() {
            @Override
            public void onResponse(Call<List<Ticket>> call, Response<List<Ticket>> response) {
                if (response.isSuccessful()) {
                    data.setValue(response.body());
                } else {
                    Toast.makeText(MyApp.getCtx(), "Algo ha ido mal.", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<Ticket>> call, Throwable t) {
            }
        });

        return data;
    }

    public LiveData<Ticket> getTicketById(String id) {
        final MutableLiveData<Ticket> data = new MutableLiveData<>();

        Call<Ticket> call = servicio.getTicketById(id);

        call.enqueue(new Callback<Ticket>() {
            @Override
            public void onResponse(Call<Ticket> call, Response<Ticket> response) {
                if (response.isSuccessful()) {
                    data.setValue(response.body());
                } else {
                }
            }

            @Override
            public void onFailure(Call<Ticket> call, Throwable t) {
            }
        });

        return data;
    }

    public LiveData<ResponseBody> getImgTicket(String id, String img) {
        final MutableLiveData<ResponseBody> data = new MutableLiveData<>();

        Call<ResponseBody> call = servicio.getImgTicket(id, img);

        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    data.setValue(response.body());
                } else {

                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });

        return data;
    }

    public LiveData<List<Ticket>> getTicketsCreadosPorMi() {
        final MutableLiveData<List<Ticket>> data = new MutableLiveData<>();

        Call<List<Ticket>> call = servicio.getTicketsCreadosPorMi();
        call.enqueue(new Callback<List<Ticket>>() {
            @Override
            public void onResponse(Call<List<Ticket>> call, Response<List<Ticket>> response) {
                if (response.isSuccessful()) {
                    data.setValue(response.body());
                } else {
                    Toast.makeText(MyApp.getCtx(), "Algo ha ido mal.", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<Ticket>> call, Throwable t) {
            }
        });

        return data;
    }



}
