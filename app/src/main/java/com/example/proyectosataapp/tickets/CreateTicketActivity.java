package com.example.proyectosataapp.tickets;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import com.example.proyectosataapp.R;
import com.example.proyectosataapp.viewModel.TicketViewModel;

public class CreateTicketActivity extends AppCompatActivity {

    private EditText etTitulo, etDescripcion;
    private Button botonImagen, botonTecnico, botonEquipo;
    private Uri uriFoto;
    private String titulo, descripcion;
    private TicketViewModel ticketViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_ticket);

        etTitulo = findViewById(R.id.createTicketEvTitulo);
        etDescripcion = findViewById(R.id.createTicketEvDescripcion);
        botonImagen = findViewById(R.id.createTicketBotonImagen);
        botonTecnico = findViewById(R.id.createTicketBotonTecnico);
        botonEquipo = findViewById(R.id.createTicketBotonEquipo);

        ticketViewModel = new ViewModelProvider(this)
                .get(TicketViewModel.class);



    }
}
