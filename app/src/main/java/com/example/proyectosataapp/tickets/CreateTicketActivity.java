package com.example.proyectosataapp.tickets;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.ViewModelProvider;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.OpenableColumns;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.proyectosataapp.MainActivity;
import com.example.proyectosataapp.R;
import com.example.proyectosataapp.common.Constantes;
import com.example.proyectosataapp.viewModel.CreateTicketViewModel;
import com.example.proyectosataapp.viewModel.EquipoViewModel;
import com.example.proyectosataapp.viewModel.TicketViewModel;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.http.Multipart;

public class CreateTicketActivity extends AppCompatActivity {
    private static final int READ_REQUEST_CODE = 42;

    private EditText etTitulo, etDescripcion;
    private Button botonTecnico, botonEquipo, botonEnviar;
    private ImageView ivFoto;
    private Uri uriFoto;
    private String titulo, descripcion, nombreFichero;
    private CreateTicketViewModel createTicketViewModel;
    private EquipoViewModel equipoViewModel;
    private Bundle extraData;
    private RequestBody tituloRequest, descripcionRequest, idTecnicoRequest, idEquipoRequest;
    private MultipartBody.Part fotosPart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_ticket);

        extraData = getIntent().getExtras();

        Toast.makeText(this, "" + getCallingActivity().getClassName(), Toast.LENGTH_SHORT).show();
        Log.d("NombreClase", "" + getCallingActivity().getClassName());
        Log.d("nombreClaseLocal", "" + getLocalClassName());

        etTitulo = findViewById(R.id.createTicketEvTitulo);
        etDescripcion = findViewById(R.id.createTicketEvDescripcion);
        botonTecnico = findViewById(R.id.createTicketBotonTecnico);
        botonEquipo = findViewById(R.id.createTicketBotonEquipo);
        botonEnviar = findViewById(R.id.createTicketBotonSubmit);
        ivFoto = findViewById(R.id.createTicketIvPortada);
        uriFoto = null;

        tituloRequest = RequestBody.create("", MultipartBody.FORM);
        descripcionRequest = RequestBody.create("", MultipartBody.FORM);
        idTecnicoRequest = RequestBody.create("", MultipartBody.FORM);
        idEquipoRequest = RequestBody.create("", MultipartBody.FORM);

        if (getCallingActivity().getClassName().contains(Constantes.DETALLE_EQUIPO_ACTIVITY_CLASS_NAME)) {
            botonEquipo.setVisibility(View.GONE);
            idEquipoRequest = RequestBody.create(extraData.getString("idEquipo"), MultipartBody.FORM);
        }



        createTicketViewModel = new ViewModelProvider(this)
                .get(CreateTicketViewModel.class);

        equipoViewModel = new ViewModelProvider(this)
                .get(EquipoViewModel.class);

        botonEnviar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (etTitulo.getText().toString().isEmpty() || etDescripcion.getText().toString().isEmpty())
                    Toast.makeText(CreateTicketActivity.this, "Debes rellenar el título y la descripción", Toast.LENGTH_SHORT).show();
                else {
                    if (uriFoto != null) {
                        try {
                            InputStream inputStream = getContentResolver().openInputStream(uriFoto);
                            ByteArrayOutputStream baos = new ByteArrayOutputStream();
                            BufferedInputStream bufferedInputStream = new BufferedInputStream(inputStream);
                            int cantBytes;
                            byte[] buffer = new byte[1024*4];

                            while ((cantBytes = bufferedInputStream.read(buffer,0,1024*4)) != -1) {
                                baos.write(buffer,0,cantBytes);
                            }

                            RequestBody requestFile =
                                    RequestBody.create(baos.toByteArray(),
                                            MediaType.parse(getContentResolver().getType(uriFoto)));


                            fotosPart =
                                    MultipartBody.Part.createFormData("fotos", nombreFichero, requestFile);

                        } catch (FileNotFoundException e) {
                            e.printStackTrace();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }

                    tituloRequest = RequestBody.create(etTitulo.getText().toString(), MultipartBody.FORM);
                    descripcionRequest = RequestBody.create(etDescripcion.getText().toString(), MultipartBody.FORM);
                    createTicketViewModel.postTicket(tituloRequest, descripcionRequest, idTecnicoRequest, idEquipoRequest, fotosPart);
                    startActivity(new Intent(CreateTicketActivity.this, MainActivity.class));

                }
            }


        });

        ivFoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { performFileSearch(); }});

        botonTecnico.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment dialogSeleccion = new DialogSeleccionarTecnico();
                Bundle bundleTecnicos = new Bundle();

            }
        });

    }

    public RequestBody getIdTecnicoRequest() {
        return idTecnicoRequest;
    }

    public void setIdTecnicoRequest(RequestBody idTecnicoRequest) {
        this.idTecnicoRequest = idTecnicoRequest;
    }

    public RequestBody getIdEquipoRequest() {
        return idEquipoRequest;
    }

    public void setIdEquipoRequest(RequestBody idEquipoRequest) {
        this.idEquipoRequest = idEquipoRequest;
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
                        .into(ivFoto);
                uriFoto = uri;
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
