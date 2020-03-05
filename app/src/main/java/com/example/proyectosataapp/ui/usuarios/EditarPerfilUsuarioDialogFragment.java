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
import com.example.proyectosataapp.models.PasswordRequest;
import com.example.proyectosataapp.models.UserResponseRegister;
import com.example.proyectosataapp.usuarios.UsuarioViewModel;

public class EditarPerfilUsuarioDialogFragment extends DialogFragment {

    EditText actualPassword, confirmNewPassword;
    View v;
    UsuarioViewModel usuarioViewModel;
    UserResponseRegister usuarioLogeado;

    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        v = LayoutInflater.from(getActivity()).inflate(R.layout.dialog_fragment_edit_profile_user, null);
        builder.setView(v);
        usuarioViewModel = new ViewModelProvider(getActivity()).get(UsuarioViewModel.class);

        actualPassword = v.findViewById(R.id.editNewPassword);
        confirmNewPassword = v.findViewById(R.id.editConfirmNewPassword);

        usuarioViewModel.getUserLogeado().observe(getActivity(),new Observer<UserResponseRegister>() {
            @Override
            public void onChanged(UserResponseRegister userResponseRegister) {
                usuarioLogeado = userResponseRegister;
            }
        });

        builder.setTitle("Cambiar contraseña");
        builder.setPositiveButton("Confirmar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String base = usuarioLogeado.getEmail() + ":" + actualPassword.getText().toString();
                String authHeader = "Basic " + Base64.encodeToString(base.getBytes(), Base64.NO_WRAP);
                usuarioViewModel.updatePassword(authHeader,usuarioLogeado.getId(),new PasswordRequest(confirmNewPassword.getText().toString())).observeForever(new Observer<UserResponseRegister>() {
                    @Override
                    public void onChanged(UserResponseRegister userResponseRegister) {
                        Toast.makeText(MyApp.getCtx(), "Contraseña cambiada", Toast.LENGTH_SHORT).show();
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
