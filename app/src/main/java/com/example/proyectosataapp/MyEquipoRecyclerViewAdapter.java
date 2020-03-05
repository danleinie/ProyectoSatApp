package com.example.proyectosataapp;

import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.proyectosataapp.common.Constantes;
import com.example.proyectosataapp.common.MyApp;
import com.example.proyectosataapp.common.SharedPreferencesManager;
import com.example.proyectosataapp.models.EquipoResponse;
import com.example.proyectosataapp.services.EquipoService;
import com.example.proyectosataapp.servicesGenerators.ServiceGenerator;
import com.example.proyectosataapp.viewModel.EquipoViewModel;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;


public class MyEquipoRecyclerViewAdapter extends RecyclerView.Adapter<MyEquipoRecyclerViewAdapter.ViewHolder> {

    private List<EquipoResponse> mValues;
    EquipoViewModel equipoViewModel;
    ServiceGenerator serviceGenerator;
    Context context;
    EquipoService service;


    public MyEquipoRecyclerViewAdapter(Context ctx, List<EquipoResponse> equipoResponses, EquipoViewModel equipoViewModel) {
        this.context = ctx;
        mValues = equipoResponses;
        this.equipoViewModel = equipoViewModel;
        service = serviceGenerator.createService(EquipoService.class);

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

        holder.tvNombre.setText(holder.mItem.getNombre());
        holder.tvUbicacion.setText(holder.mItem.getUbicacion());


        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (null != equipoViewModel) {
                    equipoViewModel.setIdEquipoSeleccionado(holder.mItem.getId());
                    SharedPreferencesManager.setStringValue(Constantes.EXTRA_ID_EQUIPO, holder.mItem.getId());
                }

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
/*
    @Override
    public Filter getFilter() {
        return filter;
    }

    Filter filter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence charSequence) {

            List<EquipoResponse> filteredList = new ArrayList<>();

            if (charSequence.toString().isEmpty()){
                filteredList.addAll(listAll);
            }else{
                for (EquipoResponse equipo : listAll){
                    if (equipo.getUbicacion().toLowerCase().contains(charSequence.toString().toLowerCase())){
                        filteredList.add(equipo);
                    }
                }
            }
            FilterResults filterResults = new FilterResults();
            filterResults.values = filteredList;
            return filterResults;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults filterResults) {
            mValues.clear();
            mValues.addAll((Collection<? extends EquipoResponse>) filterResults.values);
            notifyDataSetChanged();

        }
    };*/

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView tvNombre;
        public final TextView tvUbicacion;
        public EquipoResponse mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            tvNombre = view.findViewById(R.id.equipo_nombre);
            tvUbicacion = view.findViewById(R.id.equipo_ubicacion);

        }
    }
}