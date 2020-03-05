package com.example.proyectosataapp.ui.auth;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;


import androidx.appcompat.widget.Toolbar;
import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.OpenableColumns;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.proyectosataapp.R;
import com.example.proyectosataapp.common.Constantes;
import com.example.proyectosataapp.models.UserResponseRegister;
import com.example.proyectosataapp.services.UserService;
import com.example.proyectosataapp.servicesGenerators.ServiceGenerator;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterActivity extends AppCompatActivity {

    private static final int READ_REQUEST_CODE = 42;
    EditText email,username,password,confirmPassword;
    TextView backToSignIn;
    Button btnRegister;
    UserService servicio;
    ImageView imgFotoPerfil;
    Uri uriSelected;
    String nombreFichero;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        initToolbar();

        final Intent i = new Intent(this,LoginActivity.class);

        email = findViewById(R.id.email);
        username = findViewById(R.id.usernameRegister);
        password = findViewById(R.id.passwordRegister);
        confirmPassword = findViewById(R.id.confirmPasswordRegister);
        btnRegister = findViewById(R.id.btnRegisterInRegister);
        imgFotoPerfil = findViewById(R.id.imgFotoPerfil);
        backToSignIn = findViewById(R.id.txBackToSignIn);
        MultipartBody.Part body = null;
        uriSelected = null;

        backToSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        servicio = ServiceGenerator.createService(UserService.class);

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (
                        email.getText().toString().isEmpty() ||
                                username.getText().toString().isEmpty() ||
                                password.getText().toString().isEmpty()
                ) {

                    Toast.makeText(RegisterActivity.this, "Debe rellenar todos los campos", Toast.LENGTH_SHORT).show();

                } else if (!password.getText().toString().equals(confirmPassword.getText().toString())){
                    Toast.makeText(RegisterActivity.this, "Las contraseñas no coinciden", Toast.LENGTH_SHORT).show();

            }else if (password.getText().toString().length()<6){
                    Toast.makeText(RegisterActivity.this, "Las contraseña debe tener al menos 6 caracteres", Toast.LENGTH_SHORT).show();

                }else {
                    if (uriSelected != null) {
                        try {
                                InputStream inputStream = getContentResolver().openInputStream(uriSelected);
                                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                                BufferedInputStream bufferedInputStream = new BufferedInputStream(inputStream);
                                int cantBytes;
                                byte[] buffer = new byte[1024 * 4];

                                while ((cantBytes = bufferedInputStream.read(buffer, 0, 1024 * 4)) != -1) {
                                    baos.write(buffer, 0, cantBytes);
                                }


                                RequestBody requestFile =
                                        RequestBody.create(baos.toByteArray(),
                                                MediaType.parse(getContentResolver().getType(uriSelected)));


                                MultipartBody.Part body =
                                        MultipartBody.Part.createFormData("avatar", nombreFichero, requestFile);

                            RequestBody emailRequest = RequestBody.create(email.getText().toString(),MultipartBody.FORM);
                            RequestBody usernameRequest = RequestBody.create(username.getText().toString(),MultipartBody.FORM);
                            RequestBody passwordRequest = RequestBody.create(password.getText().toString(),MultipartBody.FORM);


                            Call<UserResponseRegister> callRegister = servicio.register(Constantes.MASTER_KEY, emailRequest,passwordRequest,usernameRequest,body);

                            callRegister.enqueue(new Callback<UserResponseRegister>() {
                                @Override
                                public void onResponse(Call<UserResponseRegister> call, Response<UserResponseRegister> response) {
                                    if (response.isSuccessful()) {
                                        Log.i("registrocorrecto", ""+response.body());
                                        finish();
                                    } else {
                                        Log.e("Upload error", response.errorBody().toString());
                                    }
                                }

                                @Override
                                public void onFailure(Call<UserResponseRegister> call, Throwable t) {
                                    Log.e("Upload error", t.getMessage());
                                }
                            });


                        } catch (FileNotFoundException e) {
                            e.printStackTrace();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }


                    } else {
                        RequestBody emailRequest = RequestBody.create(email.getText().toString(),MultipartBody.FORM);
                        RequestBody usernameRequest = RequestBody.create(username.getText().toString(),MultipartBody.FORM);
                        RequestBody passwordRequest = RequestBody.create(password.getText().toString(),MultipartBody.FORM);


                        Call<UserResponseRegister> callRegister = servicio.register(Constantes.MASTER_KEY, emailRequest,passwordRequest,usernameRequest,null);

                        callRegister.enqueue(new Callback<UserResponseRegister>() {
                            @Override
                            public void onResponse(Call<UserResponseRegister> call, Response<UserResponseRegister> response) {
                                if (response.isSuccessful()) {
                                    Log.i("registrocorrecto", ""+response.body());
                                    finish();
                                } else {
                                    Log.e("Upload error", response.errorBody().toString());
                                }
                            }

                            @Override
                            public void onFailure(Call<UserResponseRegister> call, Throwable t) {
                                Log.e("Upload error", t.getMessage());
                            }
                        });
                    }
                }
            }
        });

        imgFotoPerfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                performFileSearch();
            }
        });
    }

    private void initToolbar() {
        Toolbar toolbar = findViewById(R.id.toolbarRegister);
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

    public void performFileSearch() {

        // ACTION_OPEN_DOCUMENT is the intent to choose a file via the system's file
        // browser.
        Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);

        // Filter to only show results that can be "opened", such as a
        // file (as opposed to a list of contacts or timezones)
        intent.addCategory(Intent.CATEGORY_OPENABLE);

        // Filter to show only images, using the image MIME data type.
        // If one wanted to search for ogg vorbis files, the type would be "audio/ogg".
        // To search for all documents available via installed storage providers,
        // it would be "*/*".
        intent.setType("image/*");

        startActivityForResult(intent, READ_REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // The ACTION_OPEN_DOCUMENT intent was sent with the request code
        // READ_REQUEST_CODE. If the request code seen here doesn't match, it's the
        // response to some other intent, and the code below shouldn't run at all.

        if (requestCode == READ_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            // The document selected by the user won't be returned in the intent.
            // Instead, a URI to that document will be contained in the return intent
            // provided to this method as a parameter.
            // Pull that URI using resultData.getData().
            Uri uri = null;
            if (data != null) {
                uri = data.getData();
                //Log.i("Filechooser URI", "Uri: " + uri.toString());
                //showImage(uri);
                Glide
                        .with(this)
                        .load(uri)
                        .circleCrop()
                        .into(imgFotoPerfil);
                uriSelected = uri;
                Cursor returnCursor =
                        getContentResolver().query(uri, null, null, null, null);
                int nameIndex = returnCursor.getColumnIndex(OpenableColumns.DISPLAY_NAME);
                returnCursor.moveToFirst();
                nombreFichero = returnCursor.getString(nameIndex);
                //Toast.makeText(this, "" + nombreFichero, Toast.LENGTH_SHORT).show();
            }
        }
    }
}
