package com.example.proyectosataapp.equipos;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.proyectosataapp.DetalleEquipoActivity;
import com.example.proyectosataapp.MainActivity;
import com.example.proyectosataapp.R;
import com.example.proyectosataapp.common.MyApp;
import com.example.proyectosataapp.models.EquipoResponse;
import com.example.proyectosataapp.models.RequestEquipo;
import com.example.proyectosataapp.viewModel.DetalleEquipoViewModel;

public class EditarEquipoActivity extends AppCompatActivity {

    String idEquipo,subicacion,sdescripcion,stipo,snombre;
    EditText etnombre,etubicacion,etdescripcion,ettipo;
    Button aceptar,cancelar;
    DetalleEquipoViewModel viewModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar_equipo);
        etnombre = findViewById(R.id.nombre);
        etubicacion = findViewById(R.id.Ubicacion);
        etdescripcion = findViewById(R.id.Descripcion);
        ettipo = findViewById(R.id.Tipo);
        cancelar = findViewById(R.id.Cancelar);
        aceptar = findViewById(R.id.Aceptar);

        Bundle bundle = getIntent().getExtras();
        idEquipo = bundle.getString("ID");

        viewModel = new ViewModelProvider(EditarEquipoActivity.this).get(DetalleEquipoViewModel.class);

        viewModel.getEquipo(idEquipo).observe(this, new Observer<EquipoResponse>() {
            @Override
            public void onChanged(EquipoResponse equipo) {

                    etnombre.setText(equipo.getNombre());
                    etdescripcion.setText(equipo.getDescripcion());
                    ettipo.setText(equipo.getTipo());
                    etubicacion.setText(equipo.getUbicacion());
                }
        });

        Toast.makeText(this, "Id: " + idEquipo, Toast.LENGTH_SHORT).show();

        cancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(EditarEquipoActivity.this, MainActivity.class);
                startActivity(intent);
                }
        });

        aceptar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nombre = etnombre.getText().toString();
                String descripcion = etdescripcion.getText().toString();
                String ubicacion = etubicacion.getText().toString();
                String tipo = ettipo.getText().toString();
                if(nombre.isEmpty()){
                    etnombre.setError("El nombre del equipo es requerido");
                }
                else if(descripcion.isEmpty()){
                    etdescripcion.setError("La descripcion es requerida");
                }
                else if(ubicacion.isEmpty()){
                    etubicacion.setError("La descripcion es requerida");
                }
                else if(tipo.isEmpty()){
                    ettipo.setError("La descripcion es requerida");
                }
                else {
                    RequestEquipo requestEquipo = new RequestEquipo(nombre,descripcion,ubicacion);
                    viewModel.editEquipo(idEquipo,requestEquipo);
                    Intent intent = new Intent(EditarEquipoActivity.this,MainActivity.class);
                }


            }
        });






    }
}
