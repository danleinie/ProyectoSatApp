package com.example.proyectosataapp.equipos;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.proyectosataapp.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class EditEquipoFragment extends Fragment {

    public EditEquipoFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_edit_equipo, container, false);
    }
}
