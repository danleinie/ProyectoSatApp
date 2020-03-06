package com.example.proyectosataapp.ui.usuarios;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.proyectosataapp.R;
import com.example.proyectosataapp.common.MyApp;
import com.example.proyectosataapp.models.DtoName;
import com.example.proyectosataapp.models.PasswordRequest;
import com.example.proyectosataapp.models.UserResponseRegister;
import com.example.proyectosataapp.usuarios.UsuarioViewModel;

public class EditarNombrePerfilUsuarioDialogFragment extends DialogFragment {

    View v;
    EditText editName;
    UsuarioViewModel usuarioViewModel;
    UserResponseRegister usuarioLogeado;

    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        v = LayoutInflater.from(getActivity()).inflate(R.layout.dialog_fragment_edit_name_user, null);
        builder.setView(v);
        usuarioViewModel = new ViewModelProvider(getActivity()).get(UsuarioViewModel.class);
        editName = v.findViewById(R.id.editTextName);

        usuarioViewModel.getUserLogeado().observeForever(new Observer<UserResponseRegister>() {
            @Override
            public void onChanged(UserResponseRegister userResponseRegister) {
                usuarioLogeado = userResponseRegister;
                editName.setText(usuarioLogeado.getName());
            }
        });

        builder.setTitle("Cambiar nombre");
        builder.setPositiveButton("Confirmar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                usuarioViewModel.changeName(usuarioLogeado.getId(),new DtoName(editName.getText().toString())).observeForever(new Observer<UserResponseRegister>() {
                    @Override
                    public void onChanged(UserResponseRegister userResponseRegister) {
                        usuarioLogeado.setName(editName.getText().toString());
                        usuarioViewModel.setUserLogeado(usuarioLogeado);
                        dismiss();
                    }
                });
            }
        });
        builder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dismiss();
            }
        });

        return builder.create();
    }
}
