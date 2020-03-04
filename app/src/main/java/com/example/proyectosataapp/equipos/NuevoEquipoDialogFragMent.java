package com.example.proyectosataapp.equipos;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;

import com.bumptech.glide.Glide;
import com.example.proyectosataapp.R;
import com.example.proyectosataapp.common.Constantes;
import com.example.proyectosataapp.common.MyApp;
import com.example.proyectosataapp.common.SharedPreferencesManager;
import com.example.proyectosataapp.viewModel.EquipoViewModel;


public class NuevoEquipoDialogFragMent extends DialogFragment implements View.OnClickListener {

    ImageView cerrar, efoto;
    Button crear;
    EditText enombre, eubicacion, etipo, edescripcion;
    Dialog nuevoEquipo;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NORMAL,R.style.FullScreenDialogStyle);
        nuevoEquipo = getDialog();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.nuevo_equipo_full_dialog,container, false);

        cerrar = view.findViewById(R.id.cancelar);
        efoto = view.findViewById(R.id.imagen);
        crear = view.findViewById(R.id.enviar);
        enombre = view.findViewById(R.id.nombre);
        eubicacion = view.findViewById(R.id.ubicacion);
        etipo = view.findViewById(R.id.tipo);
        edescripcion = view.findViewById(R.id.descripcion);
        crear.setOnClickListener(this);
        cerrar.setOnClickListener(this);


        String urlPhoto = SharedPreferencesManager.getStringValue(Constantes.PREF_URL);

        if(urlPhoto.isEmpty()){
            Glide.with(getActivity())
                    .load(Constantes.APIPHOTO +urlPhoto)
                    .into(efoto);
        }

            return view;



    }
    @Override
    public void onClick(View v) {
        int id = v.getId();
        String ubicacion = eubicacion.getText().toString();
        String nombre = enombre.getText().toString();
        String tipo = etipo.getText().toString();
        String descripcion = edescripcion.getText().toString();
        String url = "";

        if (id == R.id.enviar) {
            if(ubicacion.isEmpty() || nombre.isEmpty() || tipo.isEmpty() || descripcion.isEmpty() || descripcion.isEmpty()){
                Toast.makeText(MyApp.getCtx(),"Rellena los campos",Toast.LENGTH_SHORT).show();
            }else {
                EquipoViewModel viewModel = ViewModelProviders.of(getActivity()).get(EquipoViewModel.class);
                viewModel.insertEquipo(url,ubicacion, nombre, tipo, descripcion);
                getDialog().dismiss();
            }
        } else if (id == R.id.cancelar) {
            if(ubicacion.isEmpty() || nombre.isEmpty() || tipo.isEmpty() || descripcion.isEmpty() || descripcion.isEmpty()) {
                showDialogConfirm();
            }
            else{
                getDialog().dismiss();
            }
        }

    }
    private void showDialogConfirm(){

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        builder.setMessage("¿Deseas cancelar el equipo?")
                .setTitle("Cancelar equipo");

        builder.setPositiveButton("Eliminar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                getDialog().dismiss();
            }
        });
        builder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();

    }
}
