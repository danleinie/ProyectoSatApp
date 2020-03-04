package com.example.proyectosataapp.ui.usuarios;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.proyectosataapp.R;
import com.example.proyectosataapp.common.Constantes;
import com.example.proyectosataapp.common.MyApp;
import com.example.proyectosataapp.models.UserResponseRegister;
import com.example.proyectosataapp.usuarios.UsuarioViewModel;

import okhttp3.ResponseBody;

public class DetailsUserActivity extends AppCompatActivity {

    ImageView imgPerfil;
    TextView email,nombre,createdAt,role;
    UsuarioViewModel usuarioViewModel;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pantalla_loading);

        usuarioViewModel = new ViewModelProvider(this).get(UsuarioViewModel.class);

        usuarioViewModel.getUser(getIntent().getExtras().getString(Constantes.ID_USER_LOGEADO)).observe(this, new Observer<UserResponseRegister>() {
            @Override
            public void onChanged(UserResponseRegister userResponseRegister) {
                loadData(userResponseRegister);
            }
        });

    }

    private void loadData(UserResponseRegister userResponseRegister) {
        setContentView(R.layout.activity_details_user);
        initToolbar();
        imgPerfil = findViewById(R.id.imgPerfilDetalle);
        email = findViewById(R.id.txEmailDetalle);
        nombre = findViewById(R.id.txNombreDetalle);
        createdAt = findViewById(R.id.txCreatedAtDetalle);
        progressBar = findViewById(R.id.progressBarImgPerfil);
        role = findViewById(R.id.txRoleDetalle);
        email.setText(userResponseRegister.getEmail());
        nombre.setText(userResponseRegister.getName());
        role.setText(userResponseRegister.getRole());
        createdAt.setText(userResponseRegister.getCreatedAt());

        usuarioViewModel.getImg(userResponseRegister.getId()).observe(this, new Observer<ResponseBody>() {
            @Override
            public void onChanged(ResponseBody responseBody) {
                progressBar.setVisibility(View.GONE);
                Glide
                        .with(MyApp.getCtx())
                        .load(BitmapFactory.decodeStream(responseBody.byteStream()))
                        .centerCrop()
                        .into(imgPerfil);
            }
        });
    }

    private void initToolbar() {
        Toolbar toolbar = findViewById(R.id.toolbarDetailsUser);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(null);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        } else {
            Toast.makeText(getApplicationContext(), item.getTitle(), Toast.LENGTH_SHORT).show();
        }
        return super.onOptionsItemSelected(item);
    }
}
