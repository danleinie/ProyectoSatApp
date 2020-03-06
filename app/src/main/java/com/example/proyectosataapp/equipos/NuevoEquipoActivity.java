package com.example.proyectosataapp.equipos;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.annotation.Nullable;
import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;
import com.bumptech.glide.Glide;
import com.example.proyectosataapp.R;
import com.example.proyectosataapp.common.Constantes;
import com.example.proyectosataapp.common.MyApp;
import com.example.proyectosataapp.common.SharedPreferencesManager;
import com.example.proyectosataapp.services.EquipoService;
import com.example.proyectosataapp.servicesGenerators.ServiceGenerator;
import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NuevoEquipoActivity extends AppCompatActivity {

    private static final int READ_REQUEST_CODE = 42;
    ImageView imagen;
    Button aceptar;
    EditText etNombre, etUbi, etDescripcion;
    Spinner etTipo;
    Uri UriSeleccionada;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nuevo_equipo);

        aceptar = findViewById(R.id.crearEquipo);
        etNombre = findViewById(R.id.NewNombre);
        etUbi = findViewById(R.id.NewUbicacion);
        etDescripcion = findViewById(R.id.NewDescripcion);
        imagen = findViewById(R.id.Newimagen);
        etTipo = (Spinner) findViewById(R.id.NewTipo);

        List<String> lista = new ArrayList<>();
        lista.add("PC");
        lista.add("MONITOR");
        lista.add("RED");
        lista.add("PERIFERICO");
        lista.add("OTRO");
        lista.add("IMPRESORA");

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(MyApp.getCtx(),android.R.layout.simple_spinner_item,lista);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        etTipo.setAdapter(arrayAdapter);
        final String texto = etTipo.getSelectedItem().toString();

        UriSeleccionada = null;


        aceptar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (UriSeleccionada != null) {

                    EquipoService service = ServiceGenerator.createService(EquipoService.class);

                    try {
                        InputStream inputStream = getContentResolver().openInputStream(UriSeleccionada);
                        ByteArrayOutputStream baos = new ByteArrayOutputStream();
                        BufferedInputStream bufferedInputStream = new BufferedInputStream(inputStream);
                        int cantBytes;
                        byte[] buffer = new byte[1024 * 4];

                        while ((cantBytes = bufferedInputStream.read(buffer, 0, 1024 * 4)) != -1) {
                            baos.write(buffer, 0, cantBytes);
                        }


                        RequestBody requestFile =
                                RequestBody.create(
                                        MediaType.parse(getContentResolver().getType(UriSeleccionada)), baos.toByteArray());


                        MultipartBody.Part body =
                                MultipartBody.Part.createFormData("imagen", "imagen", requestFile);


                        RequestBody nombre = RequestBody.create(etNombre.getText().toString(), MultipartBody.FORM);
                        RequestBody descripcion = RequestBody.create(etDescripcion.getText().toString(), MultipartBody.FORM);
                        RequestBody ubicacion = RequestBody.create(etUbi.getText().toString().toUpperCase(), MultipartBody.FORM);
                        RequestBody tipo = RequestBody.create(texto, MultipartBody.FORM);

                        Call<ResponseBody> call = service.crearEquipo(body, ubicacion, tipo, nombre, descripcion, SharedPreferencesManager.getStringValue(Constantes.LABEL_TOKEN));

                        call.enqueue(new Callback<ResponseBody>() {
                            @Override
                            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                                if (response.isSuccessful()) {
                                    Toast.makeText(MyApp.getCtx(), "El Equipo ha sido registrado", Toast.LENGTH_SHORT).show();


                                    onBackPressed();
                                } else {
                                    Toast.makeText(MyApp.getCtx(), "Ha ocurrido un error", Toast.LENGTH_SHORT).show();
                                }
                            }

                            @Override
                            public void onFailure(Call<ResponseBody> call, Throwable t) {
                                Toast.makeText(MyApp.getCtx(), "Error de conexion", Toast.LENGTH_SHORT).show();
                            }
                        });
                        //catch de filenotfound e input output
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }


                }
            }

        });

        imagen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buscar();
            }
        });


    }

    public void buscar() {

        Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        intent.setType("image/*");
        startActivityForResult(intent, READ_REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == READ_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            Uri uri = null;
            if (data != null) {
                uri = data.getData();
                Glide
                        .with(this)
                        .load(uri)
                        .into(imagen);
                UriSeleccionada = uri;
                imagen.setEnabled(true);
            }
        }
    }
}
