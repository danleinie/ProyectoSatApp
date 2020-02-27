package com.example.proyectosataapp.ui.auth;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.proyectosataapp.R;
import com.example.proyectosataapp.common.Constantes;
import com.example.proyectosataapp.common.SharedPreferencesManager;
import com.example.proyectosataapp.models.UserResponseLogin;
import com.example.proyectosataapp.services.UserService;
import com.example.proyectosataapp.servicesGenerators.ServiceGenerator;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    EditText name;
    EditText password;
    TextView buttonRegister;
    Button buttonLogin;
    UserService userService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        name = findViewById(R.id.username);
        password = findViewById(R.id.password);
        buttonLogin = findViewById(R.id.btnLogin);
        buttonRegister = findViewById(R.id.btnRegister);

        final Intent i = new Intent(this, RegisterActivity.class);

        userService = ServiceGenerator.createService(UserService.class);

        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String base = name.getText().toString() + ":" + password.getText().toString();
                String authHeader = "Basic " + Base64.encodeToString(base.getBytes(), Base64.NO_WRAP);

                Call<UserResponseLogin> call = userService.logIn(authHeader,Constantes.MASTER_KEY);

                call.enqueue(new Callback<UserResponseLogin>() {
                    @Override
                    public void onResponse(Call<UserResponseLogin> call, Response<UserResponseLogin> response) {
                        if (response.isSuccessful()){
                            SharedPreferencesManager.setStringValue(Constantes.LABEL_TOKEN,response.body().getToken());
                        } else {
                            Toast.makeText(LoginActivity.this, "Error name o password incorrectas", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<UserResponseLogin> call, Throwable t) {
                        Toast.makeText(LoginActivity.this, "ON FAILUREE", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

        buttonRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(i);
            }
        });
    }

}
