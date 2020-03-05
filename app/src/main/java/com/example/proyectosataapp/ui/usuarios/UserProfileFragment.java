package com.example.proyectosataapp.ui.usuarios;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.provider.OpenableColumns;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
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

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserProfileFragment extends Fragment {

    private static final int READ_REQUEST_CODE = 42;
    ImageView imgPerfil;
    TextView email, nombre, createdAt, role;
    UsuarioViewModel usuarioViewModel;
    ProgressBar progressBar;
    UserResponseRegister usuarioLogeado;
    Uri uriSelected;
    String nombreFichero;
    View v;
    MenuItem cambiarPassword;

    public UserProfileFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(final LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        v = inflater.inflate(R.layout.pantalla_loading, container, false);
        usuarioViewModel = new ViewModelProvider(getActivity()).get(UsuarioViewModel.class);

        usuarioViewModel.getUserLogeado().observeForever(new Observer<UserResponseRegister>() {
            @Override
            public void onChanged(UserResponseRegister userResponseRegister) {
                v = inflater.inflate(R.layout.fragment_profile_user, container, false);
                usuarioLogeado = userResponseRegister;
                loadData();
            }
        });

        return v;
    }

    private void loadData() {
        imgPerfil = v.findViewById(R.id.imgPerfilDetalle);
        email = v.findViewById(R.id.txEmailDetalle);
        nombre = v.findViewById(R.id.txNombreDetalle);
        createdAt = v.findViewById(R.id.txCreatedAtDetalle);
        progressBar = v.findViewById(R.id.progressBarImgPerfil);
        role = v.findViewById(R.id.txRoleDetalle);
        if (usuarioLogeado.getEmail().length()>20){
            email.setText(usuarioLogeado.getEmail());
            email.setTextSize(12);
        }else {
            email.setText(usuarioLogeado.getEmail());
        }
        nombre.setText(usuarioLogeado.getName());
        role.setText(usuarioLogeado.getRole().toUpperCase());
        createdAt.setText(usuarioLogeado.getCreatedAt());

        if (usuarioLogeado.getPicture() == null){
            progressBar.setVisibility(View.GONE);
            Glide
                    .with(MyApp.getCtx())
                    .load("https://recursospracticos.com/wp-content/uploads/2017/10/Sin-foto-de-perfil-en-Facebook.jpg")
                    .centerCrop()
                    .into(imgPerfil);
        }else {
            usuarioViewModel.getImg(usuarioLogeado.getId()).observe(this, new Observer<ResponseBody>() {
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


        imgPerfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                performFileSearch();
            }
        });
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
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
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
                        .centerCrop()
                        .into(imgPerfil);
                uriSelected = uri;
                Cursor returnCursor =
                        getActivity().getContentResolver().query(uri, null, null, null, null);
                int nameIndex = returnCursor.getColumnIndex(OpenableColumns.DISPLAY_NAME);
                returnCursor.moveToFirst();
                nombreFichero = returnCursor.getString(nameIndex);

                if (uriSelected != null) {

                    try {
                        InputStream inputStream = getActivity().getContentResolver().openInputStream(uriSelected);
                        ByteArrayOutputStream baos = new ByteArrayOutputStream();
                        BufferedInputStream bufferedInputStream = new BufferedInputStream(inputStream);
                        int cantBytes;
                        byte[] buffer = new byte[1024*4];

                        while ((cantBytes = bufferedInputStream.read(buffer,0,1024*4)) != -1) {
                            baos.write(buffer,0,cantBytes);
                        }


                        RequestBody requestFile =
                                RequestBody.create(baos.toByteArray(),
                                        MediaType.parse(getActivity().getContentResolver().getType(uriSelected)));


                        MultipartBody.Part body =
                                MultipartBody.Part.createFormData("avatar", nombreFichero, requestFile);

                        usuarioViewModel.updatePhoto(usuarioLogeado.getId(),body).observe(getActivity(), new Observer<UserResponseRegister>() {
                            @Override
                            public void onChanged(UserResponseRegister userResponseRegister) {
                                Toast.makeText(MyApp.getCtx(), "Foto actualizada", Toast.LENGTH_SHORT).show();
                            }
                        });


                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }


                }
            }
        }
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        setHasOptionsMenu(true);
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.toolbar_profile_user_menu,menu);

        cambiarPassword = menu.findItem(R.id.cambiar_password);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.cambiar_password:
                DialogFragment dialogEditPassword = new EditarPerfilUsuarioDialogFragment();
                dialogEditPassword.show(getFragmentManager(),"EditPassword");
                break;
        }

        return super.onOptionsItemSelected(item);
    }
}
