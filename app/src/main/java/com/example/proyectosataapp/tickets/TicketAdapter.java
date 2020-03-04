package com.example.proyectosataapp.tickets;

import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.proyectosataapp.R;
import com.example.proyectosataapp.models.Ticket;
import com.example.proyectosataapp.models.UserResponseRegister;
import com.example.proyectosataapp.tickets.TicketFragment.OnListFragmentInteractionListener;
import com.example.proyectosataapp.viewModel.TicketViewModel;

import java.util.List;

public class TicketAdapter extends RecyclerView.Adapter<TicketAdapter.ViewHolder> {

    private final List<Ticket> mValues;
    private final OnListFragmentInteractionListener mListener;
    private TicketViewModel viewModel;
    private View view;

    public TicketAdapter(List<Ticket> items, OnListFragmentInteractionListener listener, TicketViewModel viewModel) {
        mValues = items;
        mListener = listener;
        this.viewModel = viewModel;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_ticket, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = mValues.get(position);
        holder.tvTitulo.setText(holder.mItem.getTitulo());
        holder.tvCreadoPor.setText(holder.mItem.getCreadoPor().getEmail());
        List<Object> asignaciones = holder.mItem.getAsignaciones();
        if (!asignaciones.isEmpty())
            holder.tvAsignadoA.setText(holder.mItem.getAsignaciones().get(asignaciones.size() - 1).getClass().getName());

        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mListener) {
                    // Notify the active callbacks interface (the activity, if the
                    // fragment is attached to one) that an item has been selected.
                    mListener.onListFragmentInteraction(holder.mItem);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView tvTitulo;
        public final TextView tvCreadoPor;
        public final TextView tvAsignadoA;
        public Ticket mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            tvTitulo = view.findViewById(R.id.fragmentTicketTitulo);
            tvCreadoPor = view.findViewById(R.id.fragmentTicketCreadoPor);
            tvAsignadoA = view.findViewById(R.id.fragmentTicketAsignadoA);
        }

    }
}
