package com.example.proyectosataapp.tickets;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.proyectosataapp.MainActivity;
import com.example.proyectosataapp.R;
import com.example.proyectosataapp.common.Constantes;
import com.example.proyectosataapp.models.Ticket;
import com.example.proyectosataapp.services.TicketService;
import com.example.proyectosataapp.servicesGenerators.ServiceGenerator;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TicketFragment extends Fragment {

    private static final String ARG_COLUMN_COUNT = "column-count";
    private int mColumnCount = 1;
    private OnListFragmentInteractionListener mListener;
    private List<Ticket> tickets;
    private View view;
    private Context context;
    RecyclerView recyclerView;
    TicketService servicio;

    public TicketFragment() {
    }

    public static TicketFragment newInstance(int columnCount) {
        TicketFragment fragment = new TicketFragment();
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
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_ticket_list, container, false);

        // Set the adapter
        if (view instanceof RecyclerView) {
            context = view.getContext();
            recyclerView = (RecyclerView) view;
            if (mColumnCount <= 1) {
                recyclerView.setLayoutManager(new LinearLayoutManager(context));
            } else {
                recyclerView.setLayoutManager(new GridLayoutManager(context, mColumnCount));
            }
            tickets = new ArrayList<>();
            servicio = ServiceGenerator.createService(TicketService.class);
            Call<List<Ticket>> call = servicio.getAllTickets(Constantes.MASTER_KEY);

            call.enqueue(new Callback<List<Ticket>>() {
                @Override
                public void onResponse(Call<List<Ticket>> call, Response<List<Ticket>> response) {
                    if (response.isSuccessful()) {
                        tickets = response.body();
                        recyclerView.setAdapter(new TicketAdapter(tickets, mListener, context, R.layout.fragment_ticket));
                    } else {
                        Toast.makeText(context, "Algo ha ido mal.", Toast.LENGTH_SHORT).show();
                        cambiarActividad(MainActivity.class);
                    }
                }

                @Override
                public void onFailure(Call<List<Ticket>> call, Throwable t) {
                    Toast.makeText(context, "Algo ha ido MUY mal.", Toast.LENGTH_SHORT).show();
                    cambiarActividad(MainActivity.class);
                }
            });

        }
        return view;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnListFragmentInteractionListener) {
            mListener = (OnListFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnListFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnListFragmentInteractionListener {
        // TODO: Update argument type and name
        void onListFragmentInteraction(Ticket item);
    }

    public void cambiarActividad(Class activity) {
        Intent i = new Intent(context, activity);
        startActivity(i);
    }
}
