package com.example.proyectosataapp.ui.dashboard;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;


import com.example.proyectosataapp.R;
import com.example.proyectosataapp.tickets.CreateTicketActivity;

public class DashboardFragment extends Fragment {

    private DashboardViewModel dashboardViewModel;
    private Button crearTicketButton;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        dashboardViewModel =
                ViewModelProviders.of(this).get(DashboardViewModel.class);
        View root = inflater.inflate(R.layout.fragment_dashboard, container, false);

        crearTicketButton = root.findViewById(R.id.fragmentDashboardBotonCrearTicket);

        crearTicketButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), CreateTicketActivity.class));
            }
        });

        return root;
    }






   /* // TODO: Customize parameter argument names
        private static final String ARG_COLUMN_COUNT = "column-count";
        // TODO: Customize parameters
        private int mColumnCount = 1;
        private EquipoViewModel equipoViewModel;
        MyEquipoRecyclerViewAdapter adapter;
        RecyclerView recyclerView;

    *//**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     *//*
    public DashboardFragment() {
    }

    // TODO: Customize parameter initialization
    @SuppressWarnings("unused")
    public static DashboardFragment newInstance(int columnCount) {
        DashboardFragment fragment = new DashboardFragment();
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
                if(idEquipo != null) {
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
        View view = inflater.inflate(R.layout.fragment_dashboard, container, false);

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
*/
}
