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
import android.widget.Toast;

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
        loadAllUsers();
        loadUsersNoValidated();

        usuarioViewModel.isRefreshListUsers().observe(getActivity(), new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                if (aBoolean){
                    listUsers.clear();
                    listUsersNoValidated.clear();
                    adapter.notifyDataSetChanged();
                    adapter1.notifyDataSetChanged();
                    loadAllUsers();
                    loadUsersNoValidated();
                    usuarioViewModel.isRefreshListUsers(false);
                }else {

                }
            }
        });

        // Set the adapter
        /*if (view instanceof RecyclerView) {
            Context context = view.getContext();
            RecyclerView recyclerView = (RecyclerView) view;
            if (mColumnCount <= 1) {
                recyclerView.setLayoutManager(new LinearLayoutManager(context));
            } else {
                recyclerView.setLayoutManager(new GridLayoutManager(context, mColumnCount));
            }
            adapter = new MyUserListRecyclerViewAdapter(listUsers,usuarioViewModel);
            recyclerView.setAdapter(adapter);

            usuarioViewModel.getUsers().observe(getActivity(), new Observer<List<UserResponseRegister>>() {
                @Override
                public void onChanged(List<UserResponseRegister> userResponseRegisters) {
                    listUsers.addAll(userResponseRegisters);
                    adapter.notifyDataSetChanged();
                }
            });
        }*/
        return view;
    }

    private void loadUsersNoValidated() {
        recyclerViewUsersNoValidated = (RecyclerView) view.findViewById(R.id.recycler_view1);
        listUsersNoValidated = new ArrayList<>();
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
                listUsersNoValidated.addAll(userResponseRegisters);
                adapter1.notifyDataSetChanged();
            }
        });
    }

    private void loadAllUsers() {
        recyclerViewAllUsers = (RecyclerView) view.findViewById(R.id.recycler_view);
        listUsers = new ArrayList<>();
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
                listUsers.addAll(userResponseRegisters);
                adapter.notifyDataSetChanged();
            }
        });
    }
}
