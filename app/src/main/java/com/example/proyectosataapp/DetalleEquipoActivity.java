package com.example.proyectosataapp;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;

import com.bumptech.glide.Glide;
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
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetalleEquipoActivity extends AppCompatActivity {
    String idEquipo;
    EquipoService service;

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
        detalleEquipoViewModel = new ViewModelProvider(this).get(DetalleEquipoViewModel.class);
        detalleEquipoViewModel.getEquipo(idEquipo).observeForever( new Observer<EquipoResponse>() {
            @Override
            public void onChanged(EquipoResponse equipo) {
                Call<ResponseBody> call = service.imagenEquipo( idEquipo, SharedPreferencesManager.getStringValue(Constantes.LABEL_TOKEN));
                call.enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                       Bitmap foto =  BitmapFactory.decodeStream(response.body().byteStream());
                        Glide
                                .with(MyApp.getCtx())
                                .load(foto).centerCrop().circleCrop()
                                .into(ivFoto);
                    }
                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                    }
                });
                if(equipo != null) {
                    tvNombre.setText(equipo.getNombre());
                    tvDescripcion.setText(equipo.getDescripcion());
                    tvTipo.setText(equipo.getTipo());
                    tvUbicacion.setText(equipo.getUbicacion());
                }
            }
        });

    }

}
