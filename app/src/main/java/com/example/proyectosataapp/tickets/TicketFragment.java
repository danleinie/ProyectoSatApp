package com.example.proyectosataapp.tickets;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
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
import com.example.proyectosataapp.repository.TicketRepository;
import com.example.proyectosataapp.services.TicketService;
import com.example.proyectosataapp.servicesGenerators.ServiceGenerator;
import com.example.proyectosataapp.viewModel.EquipoViewModel;
import com.example.proyectosataapp.viewModel.TicketViewModel;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TicketFragment extends Fragment {

    private static final String ARG_COLUMN_COUNT = "column-count";
    private int mColumnCount = 1;
    private OnListFragmentInteractionListener mListener;
    private List<Ticket> ticketList;
    private View view;
    private Context context;
    private RecyclerView recyclerView;
    private TicketViewModel ticketViewModel;
    private TicketAdapter adapter;

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

        ticketViewModel = new ViewModelProvider(getActivity()).get(TicketViewModel.class);
        // Set the adapter
        if (view instanceof RecyclerView) {
            context = view.getContext();
            recyclerView = (RecyclerView) view;
            if (mColumnCount <= 1) {
                recyclerView.setLayoutManager(new LinearLayoutManager(context));
            } else {
                recyclerView.setLayoutManager(new GridLayoutManager(context, mColumnCount));
            }
            ticketList = new ArrayList<>();

            adapter = new TicketAdapter(ticketList, mListener, ticketViewModel);
            recyclerView.setAdapter(adapter);

            ticketViewModel.getTickets().observe(getActivity(), new Observer<List<Ticket>>() {
                @Override
                public void onChanged(List<Ticket> tickets) {
                    ticketList.addAll(tickets);
                    adapter.notifyDataSetChanged();
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
