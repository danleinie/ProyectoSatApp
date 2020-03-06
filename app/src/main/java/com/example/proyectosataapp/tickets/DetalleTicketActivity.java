package com.example.proyectosataapp.tickets;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.proyectosataapp.R;
import com.example.proyectosataapp.common.Constantes;
import com.example.proyectosataapp.services.TicketService;
import com.example.proyectosataapp.servicesGenerators.ServiceGenerator;

import butterknife.ButterKnife;

public class DetalleTicketActivity extends AppCompatActivity {

    String idTicket;
    TicketService service;
    ServiceGenerator serviceGenerator;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle_ticket);
        ButterKnife.bind(this);
        Bundle extras = getIntent().getExtras();

        idTicket = extras.getString(Constantes.EXTRA_ID_TICKET);
        service = serviceGenerator.createService(TicketService.class);
        


    }
}
