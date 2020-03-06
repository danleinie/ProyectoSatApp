package com.example.proyectosataapp.tickets;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.proyectosataapp.R;
import com.example.proyectosataapp.common.Constantes;
import com.example.proyectosataapp.models.EquipoResponse;
import com.example.proyectosataapp.models.UserResponseRegister;
import com.example.proyectosataapp.viewModel.CreateTicketViewModel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DialogSeleccionarTecnico extends DialogFragment {

    private View view;
    private ArrayAdapter<String> adapter;
    private List<String> listaElementos = new ArrayList<>();
    private ListView listView;
    private CreateTicketViewModel createTicketViewModel;

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        createTicketViewModel = new ViewModelProvider(this).get(CreateTicketViewModel.class);
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        view = LayoutInflater.from(getActivity()).inflate(R.layout.dialog_seleccionar_tecnico_create_ticket, null);
        builder.setView(view);
        listView = view.findViewById(R.id.dialogSeleccionarTecnicoListaOpciones);

        if (getArguments() != null) {
            if (getArguments().getString(Constantes.TIPO_SELECCION).equals(Constantes.TECNICO)) {
                builder.setTitle("Seleccione un técnico");
                createTicketViewModel.getUsers().observe(getActivity(), new Observer<List<UserResponseRegister>>() {
                    @Override
                    public void onChanged(List<UserResponseRegister> userResponseRegisters) {
                        for (UserResponseRegister user : userResponseRegisters)
                            if (user.getRole().equals("tecnico"))
                                listaElementos.add(user.getName());

                        adapter = new ArrayAdapter<String>(
                                getActivity(),
                                android.R.layout.simple_list_item_1,
                                listaElementos
                        );
                        listView.setAdapter(adapter);

                    }
                });
            } else if (getArguments().getString(Constantes.TIPO_SELECCION).equals(Constantes.EQUIPO)) {
                builder.setTitle("Seleccione un equipo");
                createTicketViewModel.getEquipos().observe(getActivity(), new Observer<List<EquipoResponse>>() {
                    @Override
                    public void onChanged(List<EquipoResponse> equipoResponses) {
                        for (EquipoResponse equipo : equipoResponses)
                            listaElementos.add(equipo.getNombre());

                        adapter = new ArrayAdapter<String>(
                                getActivity(),
                                android.R.layout.simple_list_item_1,
                                listaElementos
                        );
                        listView.setAdapter(adapter);
                    }
                });
            }
        }

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (getArguments() != null) {
                    if (getArguments().getString(Constantes.TIPO_SELECCION).equals(Constantes.TECNICO)){

                    } else if (getArguments().getString(Constantes.TIPO_SELECCION).equals(Constantes.EQUIPO)){

                    }
                }
                dismiss();
            }
        });

        return builder.create();
    }
}
