package com.example.proyectosataapp.ui.home;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.proyectosataapp.DetalleEquipoActivity;
import com.example.proyectosataapp.MyEquipoRecyclerViewAdapter;
import com.example.proyectosataapp.R;
import com.example.proyectosataapp.common.Constantes;
import com.example.proyectosataapp.common.MyApp;
import com.example.proyectosataapp.models.EquipoResponse;
import com.example.proyectosataapp.viewModel.EquipoViewModel;

import java.util.List;

public class HomeFragment extends Fragment {

    Integer numero = 0;

     // TODO: Customize parameter argument names
        private static final String ARG_COLUMN_COUNT = "column-count";
        // TODO: Customize parameters
        private int mColumnCount = 1;
        private EquipoViewModel equipoViewModel;
        MyEquipoRecyclerViewAdapter adapter;
        RecyclerView recyclerView;


    public HomeFragment() {
    }

    // TODO: Customize parameter initialization
    @SuppressWarnings("unused")
    public static HomeFragment newInstance(int columnCount) {
        HomeFragment fragment = new HomeFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_COLUMN_COUNT, columnCount);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);



        if (getArguments() != null) {
            mColumnCount = getArguments().getInt(ARG_COLUMN_COUNT);
        }

        equipoViewModel = new ViewModelProvider(getActivity()).get(EquipoViewModel.class);

        equipoViewModel.getIdEquipoSeleccionado().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String idEquipo) {
                if((idEquipo != null) && (idEquipo != " ")) {
                    Intent i = new Intent(MyApp.getCtx(), DetalleEquipoActivity.class);
                    i.putExtra(Constantes.EXTRA_ID_EQUIPO, idEquipo);
                    startActivity(i);
                }
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_equipo_list, container, false);

        // Set the adapter
        if (view instanceof RecyclerView) {
            Context context = view.getContext();
            RecyclerView recyclerView = (RecyclerView) view;
            if (mColumnCount <= 1) {
                recyclerView.setLayoutManager(new LinearLayoutManager(context));
            } else {
                recyclerView.setLayoutManager(new GridLayoutManager(context, mColumnCount));
            }

            adapter = new MyEquipoRecyclerViewAdapter(
                    getActivity(),
                    null,
                    equipoViewModel);

            recyclerView.setAdapter(adapter);
        }

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        Toast.makeText(getActivity(), "onResume()", Toast.LENGTH_SHORT).show();

        //TODO IMPORTANTE
        equipoViewModel.getEquipos().observe(getActivity(), new Observer<List<EquipoResponse>>() {
            @Override
            public void onChanged(List<EquipoResponse> equipoResponses) {
                adapter.setData(equipoResponses);
            }
        });

    }

    @Override
    public void onPause() {
        super.onPause();
        Toast.makeText(getActivity(), "onPause()", Toast.LENGTH_SHORT).show();
    }


    /*private HomeViewModel homeViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                ViewModelProviders.of(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);

        return root;
    }*/
}