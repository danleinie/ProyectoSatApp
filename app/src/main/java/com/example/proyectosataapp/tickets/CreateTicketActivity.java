package com.example.proyectosataapp.tickets;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.proyectosataapp.R;
import com.example.proyectosataapp.viewModel.EquipoViewModel;
import com.example.proyectosataapp.viewModel.TicketViewModel;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.http.Multipart;

public class CreateTicketActivity extends AppCompatActivity {

    private EditText etTitulo, etDescripcion;
    private Button botonImagen, botonTecnico, botonEquipo, botonEnviar;
    private Uri uriFoto;
    private String titulo, descripcion;
    private TicketViewModel ticketViewModel;
    private EquipoViewModel equipoViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_ticket);

        etTitulo = findViewById(R.id.createTicketEvTitulo);
        etDescripcion = findViewById(R.id.createTicketEvDescripcion);
        botonImagen = findViewById(R.id.createTicketBotonImagen);
        botonTecnico = findViewById(R.id.createTicketBotonTecnico);
        botonEquipo = findViewById(R.id.createTicketBotonEquipo);
        botonEnviar = findViewById(R.id.createTicketBotonSubmit);

        ticketViewModel = new ViewModelProvider(this)
                .get(TicketViewModel.class);

        equipoViewModel = new ViewModelProvider(this)
                .get(EquipoViewModel.class);

        botonEnviar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (etTitulo.getText().toString().isEmpty() || etDescripcion.getText().toString().isEmpty())
                    Toast.makeText(CreateTicketActivity.this, "Debes rellenar el título y la descripción", Toast.LENGTH_SHORT).show();
                else {
                    RequestBody tituloRequest = RequestBody.create(etTitulo.getText().toString(), MultipartBody.FORM);
                    RequestBody descripcionRequest = RequestBody.create(etDescripcion.getText().toString(), MultipartBody.FORM);
                    ticketViewModel.postTicket(tituloRequest, descripcionRequest);
                }
            }
        });


    }
}
