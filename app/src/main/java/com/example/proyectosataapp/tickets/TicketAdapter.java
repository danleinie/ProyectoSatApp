package com.example.proyectosataapp.tickets;

import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.RecyclerView;

import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.proyectosataapp.R;
import com.example.proyectosataapp.models.Ticket;
import com.example.proyectosataapp.models.TicketAsignacion;
import com.example.proyectosataapp.models.UserResponseRegister;
import com.example.proyectosataapp.tickets.TicketFragment.OnListFragmentInteractionListener;
import com.example.proyectosataapp.viewModel.TicketViewModel;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import okhttp3.ResponseBody;

public class TicketAdapter extends RecyclerView.Adapter<TicketAdapter.ViewHolder> {

    private final List<Ticket> mValues;
    private final OnListFragmentInteractionListener mListener;
    private TicketViewModel viewModel;
    private View view;
    private Date fechaOriginal = new Date();

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
        holder.tvCreadoPor.setText(holder.mItem.getCreadoPor().getName());
        holder.tvDescripcion.setText(holder.mItem.getDescripcion());
        SimpleDateFormat formatoOriginal = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
        SimpleDateFormat formatoFinal = new SimpleDateFormat("dd-MM-yyyy");
        try {
            fechaOriginal = formatoOriginal.parse(holder.mItem.getFechaCreacion());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        String fechaFinal = formatoFinal.format(fechaOriginal);

        holder.tvFecha.setText(fechaFinal);

        List<TicketAsignacion> asignaciones = holder.mItem.getAsignaciones();
        if (!asignaciones.isEmpty() && asignaciones != null) {
            viewModel.getTecnicoById(holder.mItem.getAsignaciones().get(holder.mItem.getAsignaciones().size() - 1).getTecnico_id()).observeForever(new Observer<UserResponseRegister>() {
                @Override
                public void onChanged(UserResponseRegister userResponseRegister) {
                    holder.tvAsignadoA.setText(userResponseRegister.getName());
                }
            });
        }

        List<String> fotos = holder.mItem.getFotos();

        if (!fotos.isEmpty() && fotos!= null) {
            String url = holder.mItem.getFotos().get(0);
            String params[] = url.split("/");


            viewModel.getTicketImg(params[params.length - 2], params[params.length - 1]).observeForever(new Observer<ResponseBody>() {
                @Override
                public void onChanged(ResponseBody responseBody) {
                    Glide
                            .with(view)
                            .load(
                                    BitmapFactory
                                            .decodeStream(
                                                    responseBody.byteStream()
                                            )
                            )
                            //.load(responseBody)
                            .centerCrop()
                            .into(holder.ivFotoTicket);
                    holder.pbFoto.setVisibility(View.GONE);
                }
            });
        } else
            holder.pbFoto.setVisibility(View.GONE);

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
        public final TextView tvDescripcion;
        public final TextView tvFecha;
        public final ImageView ivFotoTicket;
        public final ProgressBar pbFoto;

        public Ticket mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            tvTitulo = view.findViewById(R.id.fragmentTicketTitulo);
            tvCreadoPor = view.findViewById(R.id.fragmentTicketCreadoPor);
            tvAsignadoA = view.findViewById(R.id.fragmentTicketAsignadoA);
            tvDescripcion = view.findViewById(R.id.fragmentTicketDescripcion);
            tvFecha = view.findViewById(R.id.fragmentTicketFechaCreacion);
            ivFotoTicket = view.findViewById(R.id.fragmentTicketImagenTicket);
            pbFoto = view.findViewById(R.id.fragmentTicketProgressBar);
        }

    }
}
