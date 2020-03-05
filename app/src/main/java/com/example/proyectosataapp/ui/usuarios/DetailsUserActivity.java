package com.example.proyectosataapp.ui.usuarios;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.DialogInterface;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.Menu;
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
    String idUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pantalla_loading);
        idUser = getIntent().getExtras().getString(Constantes.ID_USER_LOGEADO);
        usuarioViewModel = new ViewModelProvider(this).get(UsuarioViewModel.class);

        usuarioViewModel.getUser(idUser).observe(this, new Observer<UserResponseRegister>() {
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
        if (userResponseRegister.getEmail().length()>20){
            email.setText(userResponseRegister.getEmail());
            email.setTextSize(12);
        }else {
            email.setText(userResponseRegister.getEmail());
        }
        nombre.setText(userResponseRegister.getName());
        role.setText(userResponseRegister.getRole().toUpperCase());
        createdAt.setText(userResponseRegister.getCreatedAt());

        if (userResponseRegister.getPicture() == null){
            progressBar.setVisibility(View.GONE);
            Glide
                    .with(MyApp.getCtx())
                    .load("https://recursospracticos.com/wp-content/uploads/2017/10/Sin-foto-de-perfil-en-Facebook.jpg")
                    .centerCrop()
                    .into(imgPerfil);
        }else {
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
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.toolbar_details_user_menu, menu);
        return true;
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
        } else if(item.getItemId() == R.id.borrar_usuario){
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                    this);
            alertDialogBuilder.setTitle(R.string.title_dialog_borrar);
            alertDialogBuilder
                    .setMessage(R.string.message_dialog_borrar)
                    .setCancelable(true)
                    .setPositiveButton("Yes",new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog,int id) {
                            usuarioViewModel.deleteUserFromRepo(idUser).observeForever( new Observer<Boolean>() {
                                @Override
                                public void onChanged(Boolean aBoolean) {
                                    if (aBoolean){
                                        finish();
                                    }
                                }
                            });
                        }
                    })
                    .setNegativeButton("No",new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog,int id) {
                            dialog.cancel();
                            finish();
                        }
                    });
            AlertDialog alertDialog = alertDialogBuilder.create();
            alertDialog.show();
        } else if(item.getItemId() == R.id.cambiar_rol){
            usuarioViewModel.changeToTecnico(idUser).observeForever(new Observer<UserResponseRegister>() {
                @Override
                public void onChanged(UserResponseRegister userResponseRegister) {
                    role.setText(userResponseRegister.getRole().toUpperCase());
                }
            });
        }

        else {
            Toast.makeText(getApplicationContext(), item.getTitle(), Toast.LENGTH_SHORT).show();
        }
        return super.onOptionsItemSelected(item);
    }
}
