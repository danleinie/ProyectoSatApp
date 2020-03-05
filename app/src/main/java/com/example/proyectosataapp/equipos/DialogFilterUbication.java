package com.example.proyectosataapp.equipos;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.proyectosataapp.IFiltroListener;
import com.example.proyectosataapp.R;
import com.example.proyectosataapp.common.Constantes;
import com.example.proyectosataapp.ui.home.HomeFragment;
import com.example.proyectosataapp.viewModel.EquipoViewModel;

import java.util.ArrayList;
import java.util.List;

public class DialogFilterUbication extends DialogFragment {

    View v;
    ListView lv;
    ArrayAdapter<String> adapter;
    List<String> listaUbicaciones = new ArrayList<>();
    private EquipoViewModel equipoViewModel;

    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        v = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_filter_ubicacion, null);
        builder.setView(v);
        lv = v.findViewById(R.id.ListViewOpcionesFiltro);

        equipoViewModel = new ViewModelProvider(getActivity()).get(EquipoViewModel.class);
        equipoViewModel.getUbicaciones().observe(this, new Observer<List<String>>() {
            @Override
            public void onChanged(List<String> strings) {
                listaUbicaciones=strings;
                adapter = new ArrayAdapter<String>(
                        getActivity(),
                        android.R.layout.simple_list_item_1,
                        listaUbicaciones
                );
                lv.setAdapter(adapter);

            }
        });

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            IFiltroListener iFiltroListener = (IFiltroListener) getTargetFragment();
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                //IFiltroListener mHost = (IFiltroListener)getTargetFragment();
                //mHost.onClickFiltros(listaUbicaciones.get(position));
                iFiltroListener.onClickFiltros(listaUbicaciones.get(position));

                dismiss();
            }
        });
        return builder.create();
    }
}
