package com.example.proyectosataapp.equipos;

import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.proyectosataapp.R;
import com.example.proyectosataapp.models.EquipoResponse;
import com.example.proyectosataapp.viewModel.EquipoViewModel;

import java.util.ArrayList;
import java.util.List;


public class MyEquipoRecyclerViewAdapter extends RecyclerView.Adapter<MyEquipoRecyclerViewAdapter.ViewHolder> {

    private List<EquipoResponse> mValues;
    EquipoViewModel equipoViewModel;
    Context context;


    public MyEquipoRecyclerViewAdapter(Context ctx, List<EquipoResponse> equipos, EquipoViewModel equipoViewModel) {
        this.context = ctx;
        mValues = equipos;
        this.equipoViewModel = equipoViewModel;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_equipo, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = mValues.get(position);

        Glide
                .with(context)
                .load(holder.mItem.getImagen())
                .centerCrop()
                .into(holder.ivImagen);

        holder.tvNombre.setText(holder.mItem.getNombre());
        holder.tvUbicacion.setText(holder.mItem.getUbicacion());


        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    public void setData(List<EquipoResponse> list){
        if(this.mValues != null) {
            this.mValues.clear();
        } else {
            this.mValues =  new ArrayList<>();
        }
        this.mValues.addAll(list);
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        if(mValues != null){
            return mValues.size();
        } else {
            return 0;
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView tvNombre;
        public final TextView tvUbicacion;
        public final ImageView ivImagen;
        public EquipoResponse mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            tvNombre = view.findViewById(R.id.equipo_nombre);
            tvUbicacion = view.findViewById(R.id.equipo_ubicacion);
            ivImagen= view.findViewById(R.id.equipo_img);
        }
    }
}
