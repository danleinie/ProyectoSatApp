package com.example.proyectosataapp.equipos;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.proyectosataapp.DetalleEquipoActivity;
import com.example.proyectosataapp.MainActivity;
import com.example.proyectosataapp.R;
import com.example.proyectosataapp.viewModel.DetalleEquipoViewModel;

public class EditarEquipoActivity extends AppCompatActivity {

    String idEquipo,subicacion,sdescripcion,stipo,snombre;
    EditText nombre,ubicacion,descripcion,tipo;
    Button aceptar,cancelar;
    DetalleEquipoViewModel viewModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar_equipo);

        Bundle bundle = getIntent().getExtras();



        nombre = findViewById(R.id.nombre);
        ubicacion = findViewById(R.id.Ubicacion);
        descripcion = findViewById(R.id.Descripcion);
        tipo = findViewById(R.id.Tipo);
        cancelar = findViewById(R.id.Cancelar);
        aceptar = findViewById(R.id.Aceptar);

        idEquipo = bundle.getString("ID");
        nombre.setText(bundle.getString("NOMBRE"));
        ubicacion.setText(bundle.getString("UBICACION"));
        descripcion.setText(bundle.getString("DESCRIPCION"));
        tipo.setText(bundle.getString("TIPO"));
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
                viewModel = new ViewModelProvider(EditarEquipoActivity.this).get(DetalleEquipoViewModel.class);

                viewModel.editEquipo(idEquipo,ubicacion.getText().toString(),nombre.getText().toString(),tipo.getText().toString(),descripcion.getText().toString());

                Intent intent = new Intent(EditarEquipoActivity.this,MainActivity.class);
            }
        });






    }
}
