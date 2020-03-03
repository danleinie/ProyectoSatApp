package com.example.proyectosataapp.equipos;


import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;

import com.bumptech.glide.Glide;
import com.example.proyectosataapp.R;
import com.example.proyectosataapp.common.Constantes;
import com.example.proyectosataapp.common.MyApp;
import com.example.proyectosataapp.common.SharedPreferencesManager;
import com.example.proyectosataapp.models.EquipoResponse;
import com.example.proyectosataapp.services.EquipoService;
import com.example.proyectosataapp.servicesGenerators.ServiceGenerator;
import com.example.proyectosataapp.viewModel.DetalleEquipoViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetalleEquipoActivity extends AppCompatActivity {
    String idEquipo;
    EquipoService service;
    Uri data;
    ServiceGenerator serviceGenerator;
    DetalleEquipoViewModel detalleEquipoViewModel;
    @BindView(R.id.textView_detalle_equipo_nombre) TextView tvNombre;
    @BindView(R.id.textView_detalle_equipo_tipo) TextView tvTipo;
    @BindView(R.id.textView_detalle_equipo_ubicacion) TextView tvUbicacion;
    @BindView(R.id.editText_detalle_equipo_descripcion) TextView tvDescripcion;
    @BindView(R.id.imageView_detalle_equipo) ImageView ivFoto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detalle_equipo);

        ButterKnife.bind(this);

        Bundle extras = getIntent().getExtras();
        idEquipo = extras.getString(Constantes.EXTRA_ID_EQUIPO);

        Toast.makeText(this, "Id: " + idEquipo, Toast.LENGTH_SHORT).show();
        service = serviceGenerator.createService(EquipoService.class);

        Call<Uri> call = service.imagenEquipo( idEquipo, SharedPreferencesManager.getStringValue(Constantes.LABEL_TOKEN));
        call.enqueue(new Callback<Uri>() {
            @Override
            public void onResponse(Call<Uri> call, Response<Uri> response) {

                data = response.body();

            }

            @Override
            public void onFailure(Call<Uri> call, Throwable t) {

            }
        });

        detalleEquipoViewModel = new ViewModelProvider(this).get(DetalleEquipoViewModel.class);

        detalleEquipoViewModel.getEquipo(idEquipo).observe(this, new Observer<EquipoResponse>() {
            @Override
            public void onChanged(EquipoResponse equipo) {
                if(equipo != null) {

                    Glide
                            .with(MyApp.getCtx())
                            .load(data).centerCrop()
                            .into(ivFoto);

                    tvNombre.setText(equipo.getNombre());
                    tvDescripcion.setText(equipo.getDescripcion());
                    tvTipo.setText(equipo.getTipo());
                    tvUbicacion.setText(equipo.getUbicacion());
                }
            }
        });

    }

}

