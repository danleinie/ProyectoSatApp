package com.example.proyectosataapp.equipos;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.example.proyectosataapp.MainActivity;
import com.example.proyectosataapp.R;
import com.example.proyectosataapp.common.MyApp;
import com.example.proyectosataapp.models.EquipoResponse;
import com.example.proyectosataapp.models.RequestEquipo;
import com.example.proyectosataapp.viewModel.DetalleEquipoViewModel;


public class EditEquipoFragment extends DialogFragment {
    String idEquipo;
    EditText etnombre,etubicacion,etdescripcion,ettipo;
    Button aceptar,cancelar;
    DetalleEquipoViewModel viewModel;
    Dialog editEquipo;


    public static EditEquipoFragment newInstace(){
        return new EditEquipoFragment();
    }
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NORMAL,R.style.FullScreenDialogStyle);
        editEquipo = getDialog();
        viewModel = ViewModelProviders.of(this).get(DetalleEquipoViewModel.class);
        idEquipo = String.valueOf(getArguments().get("ID"));

    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,@Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_edit_equipo, container, false);
        etnombre = view.findViewById(R.id.EditNombre);
        etubicacion = view.findViewById(R.id.Ubicacion);
        etdescripcion = view.findViewById(R.id.Descripcion);
        ettipo = view.findViewById(R.id.Tipo);
        cancelar = view.findViewById(R.id.Cancelar);
        aceptar = view.findViewById(R.id.Aceptar);

        //Toast.makeText(getActivity(), "Id: " + idEquipo, Toast.LENGTH_SHORT).show();
        viewModel.getEquipo(idEquipo).observe(getActivity(), new Observer<EquipoResponse>() {
            @Override
            public void onChanged(EquipoResponse equipo) {

                etnombre.setText(equipo.getNombre());
                etdescripcion.setText(equipo.getDescripcion());
                etubicacion.setText(equipo.getUbicacion());

                cancelar.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        getDialog().dismiss();
                    }
                });

                aceptar.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String nombre = etnombre.getText().toString();
                        String descripcion = etdescripcion.getText().toString();
                        String ubicacion = etubicacion.getText().toString().toUpperCase();
                        if (nombre.isEmpty()) {
                            etnombre.setError("El nombre del equipo es requerido");
                        } else if (descripcion.isEmpty()) {
                            etdescripcion.setError("La descripcion es requerida");
                        } else if (ubicacion.isEmpty()) {
                            etubicacion.setError("La descripcion es requerida");
                        }
                         else {
                            RequestEquipo requestEquipo = new RequestEquipo(nombre, descripcion, ubicacion);
                            viewModel.editEquipo(idEquipo, requestEquipo);
                            getDialog().dismiss();
                            Intent i = new Intent(MyApp.getCtx(), MainActivity.class);
                            startActivity(i);
                        }


                    }
                });
            }

        });
        return view;
    }
}
