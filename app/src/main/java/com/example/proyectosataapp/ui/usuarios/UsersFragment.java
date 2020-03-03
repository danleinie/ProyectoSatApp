package com.example.proyectosataapp.ui.usuarios;

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

import com.example.proyectosataapp.R;
import com.example.proyectosataapp.common.MyApp;
import com.example.proyectosataapp.models.UserResponseRegister;
import com.example.proyectosataapp.usuarios.UsuarioViewModel;

import java.util.ArrayList;
import java.util.List;

public class UsersFragment extends Fragment {

    private int mColumnCount = 1;
    private RecyclerView recyclerViewAllUsers, recyclerViewUsersNoValidated;
    private List<UserResponseRegister> listUsers,listUsersNoValidated;
    private UsuarioViewModel usuarioViewModel;
    private MyUserListRecyclerViewAdapter adapter,adapter1;
    private View view;

    public UsersFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        usuarioViewModel = new ViewModelProvider(getActivity()).get(UsuarioViewModel.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.prueba_dos_recyclerview, container, false);
        listUsers = new ArrayList<>();
        listUsersNoValidated = new ArrayList<>();
        loadAllUsers();
        loadUsersNoValidated();
        return view;
    }

    private void loadUsersNoValidated() {

        if (listUsersNoValidated.isEmpty()){
            recyclerViewUsersNoValidated = (RecyclerView) view.findViewById(R.id.recycler_view1);
            if (mColumnCount <= 1) {
                recyclerViewUsersNoValidated.setLayoutManager(new LinearLayoutManager(MyApp.getCtx()));
            } else {
                recyclerViewUsersNoValidated.setLayoutManager(new GridLayoutManager(MyApp.getCtx(), mColumnCount));
            }
            adapter1 = new MyUserListRecyclerViewAdapter(listUsersNoValidated,usuarioViewModel,false);
            recyclerViewUsersNoValidated.setAdapter(adapter1);
            usuarioViewModel.getUsersNoValidated().observe(getActivity(), new Observer<List<UserResponseRegister>>() {
                @Override
                public void onChanged(List<UserResponseRegister> userResponseRegisters) {
                    usuarioViewModel.setSizeListUsersNoValidated(userResponseRegisters.size());
                    if(userResponseRegisters.size()>listUsersNoValidated.size()){
                        listUsersNoValidated.addAll(userResponseRegisters);
                        adapter1.notifyDataSetChanged();
                    }
                }
            });

            usuarioViewModel.getNewUserValidated().observe(getActivity(), new Observer<UserResponseRegister>() {
                @Override
                public void onChanged(UserResponseRegister userResponseRegister) {
                    listUsersNoValidated.remove(userResponseRegister);
                    adapter1.notifyDataSetChanged();
                    usuarioViewModel.setListUsersNoValidated(listUsersNoValidated);
                }
            });
        }

    }

    private void loadAllUsers() {

        if (listUsers.isEmpty()){
            recyclerViewAllUsers = (RecyclerView) view.findViewById(R.id.recycler_view);
            if (mColumnCount <= 1) {
                recyclerViewAllUsers.setLayoutManager(new LinearLayoutManager(MyApp.getCtx()));
            } else {
                recyclerViewAllUsers.setLayoutManager(new GridLayoutManager(MyApp.getCtx(), mColumnCount));
            }
            adapter = new MyUserListRecyclerViewAdapter(listUsers,usuarioViewModel,true);
            recyclerViewAllUsers.setAdapter(adapter);
            usuarioViewModel.getUsers().observe(getActivity(), new Observer<List<UserResponseRegister>>() {
                        @Override
                        public void onChanged(List<UserResponseRegister> userResponseRegisters) {
                            if (listUsers.size()<userResponseRegisters.size()){
                                listUsers.addAll(userResponseRegisters);
                                adapter.notifyDataSetChanged();
                            }
                }
            });

            usuarioViewModel.getNewUserValidated().observe(getActivity(), new Observer<UserResponseRegister>() {
                @Override
                public void onChanged(UserResponseRegister userResponseRegister) {
                    listUsers.add(userResponseRegister);
                    adapter.notifyDataSetChanged();
                    usuarioViewModel.setListUsers(listUsers);
                }
            });
        }
    }
}
