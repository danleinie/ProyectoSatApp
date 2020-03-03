package com.example.proyectosataapp.ui.usuarios;

import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.RecyclerView;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.proyectosataapp.R;
import com.example.proyectosataapp.common.MyApp;
import com.example.proyectosataapp.models.UserResponseRegister;
import com.example.proyectosataapp.usuarios.UsuarioViewModel;

import java.util.List;

import okhttp3.ResponseBody;

public class MyUserListRecyclerViewAdapter extends RecyclerView.Adapter<MyUserListRecyclerViewAdapter.ViewHolder> {

    private final List<UserResponseRegister> mValues;
    private final UsuarioViewModel usuarioViewModel;
    private final boolean isValidated;

    public MyUserListRecyclerViewAdapter(List<UserResponseRegister> items, UsuarioViewModel usuarioViewModel,boolean isValidated) {
        mValues = items;
        this.usuarioViewModel = usuarioViewModel;
        this.isValidated = isValidated;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_users, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = mValues.get(position);
        holder.textNombre.setText(holder.mItem.getName());
        holder.textRole.setText(holder.mItem.getRole());

        if(isValidated){
            holder.buttonCancelar.setVisibility(View.GONE);
            holder.buttonAceptar.setVisibility(View.GONE);
        } else {
            holder.imgFlechaToDetalle.setVisibility(View.GONE);
        }

        if (holder.mItem.getPicture() == null){
            holder.mItem.setPicture("https://recursospracticos.com/wp-content/uploads/2017/10/Sin-foto-de-perfil-en-Facebook.jpg");
            Glide
                    .with(MyApp.getCtx())
                    .load(holder.mItem.getPicture())
                    .circleCrop()
                    .into(holder.imgFoto);
        }else {
            usuarioViewModel.getImg(holder.mItem.getId()).observeForever(new Observer<ResponseBody>() {
                @Override
                public void onChanged(ResponseBody responseBody) {
                    Bitmap fotoBitMap = BitmapFactory.decodeStream(responseBody.byteStream());
                    Glide
                            .with(MyApp.getCtx())
                            .load(fotoBitMap)
                            .circleCrop()
                            .into(holder.imgFoto);
                }
            });
        }
        holder.buttonAceptar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                usuarioViewModel.validarUsuario(holder.mItem.getId()).observeForever(new Observer<UserResponseRegister>() {
                    @Override
                    public void onChanged(UserResponseRegister userResponseRegister) {
                        usuarioViewModel.isRefreshListUsers(true);
                    }
                });
            }
        });
        holder.buttonCancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                usuarioViewModel.isRefreshListUsers(false);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final ImageView imgFoto;
        public final TextView textNombre;
        public final TextView textRole;
        public final Button buttonAceptar;
        public final Button buttonCancelar;
        public final ImageView imgFlechaToDetalle;
        public UserResponseRegister mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            this.imgFoto = view.findViewById(R.id.imgFoto);
            this.textRole = view.findViewById(R.id.textRole);
            this.textNombre = view.findViewById(R.id.textNombre);
            this.buttonAceptar = view.findViewById(R.id.buttonAceptar);
            this.buttonCancelar = view.findViewById(R.id.buttonCancelar);
            this.imgFlechaToDetalle = view.findViewById(R.id.imgFlechaToDetalle);
        }

    }
}
