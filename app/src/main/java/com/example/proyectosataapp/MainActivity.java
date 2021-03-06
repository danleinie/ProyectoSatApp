package com.example.proyectosataapp;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.SearchView;

import android.view.View;

import com.example.proyectosataapp.equipos.NuevoEquipoDialogFragMent;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.proyectosataapp.models.Ticket;
import com.example.proyectosataapp.common.Constantes;
import com.example.proyectosataapp.common.SharedPreferencesManager;
import com.example.proyectosataapp.models.UserResponseRegister;
import com.example.proyectosataapp.tickets.TicketFragment;
import com.example.proyectosataapp.ui.dashboard.DashboardFragment;
import com.example.proyectosataapp.usuarios.UsuarioViewModel;
import com.google.android.material.bottomnavigation.BottomNavigationItemView;
import com.google.android.material.bottomnavigation.BottomNavigationMenuView;
import com.example.proyectosataapp.common.MyApp;
import com.example.proyectosataapp.viewModel.EquipoViewModel;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class MainActivity extends AppCompatActivity implements TicketFragment.OnListFragmentInteractionListener {

    FloatingActionButton nuevoEquipo;


    TextView txNotificationsBadgeUsers;
    UsuarioViewModel usuarioViewModel;
    BottomNavigationView navView;
    AppBarConfiguration appBarConfiguration;
    NavController navController;
    String roleUserLogeado = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pantalla_loading);

        usuarioViewModel = new ViewModelProvider(this).get(UsuarioViewModel.class);
        usuarioViewModel.getUserLogeadoFromRepo().observe(this, new Observer<UserResponseRegister>() {
            @Override
            public void onChanged(UserResponseRegister userResponseRegister) {
                usuarioViewModel.setUserLogeado(userResponseRegister);
                roleUserLogeado = userResponseRegister.getRole();
                if (SharedPreferencesManager.getStringValue(Constantes.ROLE_USER_LOGEADO).equals("admin")){
                    loadiuAdmin();
                }else {
                    loadiuUser();
                }
            }
        });

    }

    private void loadiuUser() {
        setContentView(R.layout.activity_main_user);
        navView = findViewById(R.id.nav_view_user);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_home, R.id.navigation_dashboard, R.id.navigation_profile)
                .build();
        navController = Navigation.findNavController(this, R.id.nav_host_fragment_user);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(navView, navController);
    }

    private void loadiuAdmin() {
        setContentView(R.layout.activity_main);
        navView = findViewById(R.id.nav_view_admin);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_home, R.id.navigation_dashboard, R.id.navigation_notifications)
                .build();
        navController = Navigation.findNavController(this, R.id.nav_host_fragment_admin);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(navView, navController);

        //Código para poner un badge de notificación
        BottomNavigationMenuView bottomNavigationMenuView =
                (BottomNavigationMenuView) navView.getChildAt(0);
        View v = bottomNavigationMenuView.getChildAt(2);
        BottomNavigationItemView itemView = (BottomNavigationItemView) v;

        View badge = LayoutInflater.from(this)
                .inflate(R.layout.notification_badge, itemView, true);
        txNotificationsBadgeUsers = badge.findViewById(R.id.txNotificationsBadge);
        txNotificationsBadgeUsers.setVisibility(View.GONE);
        loadData();
    }

    private void loadData() {
        usuarioViewModel.getUsersFromRepo().observe(this, new Observer<List<UserResponseRegister>>() {
            @Override
            public void onChanged(final List<UserResponseRegister> userResponseRegisters) {
                usuarioViewModel.setListUsers(userResponseRegisters);
            }
        });

        usuarioViewModel.getUsersNoValidatedFromRepo().observe(this, new Observer<List<UserResponseRegister>>() {
            @Override
            public void onChanged(List<UserResponseRegister> userResponseRegisters) {
                usuarioViewModel.setListUsersNoValidated(userResponseRegisters);
                usuarioViewModel.setSizeListUsersNoValidated(userResponseRegisters.size());
            }
        });

        usuarioViewModel.getSizeListUsersNoValidated().observe(this, new Observer<Integer>() {
            @Override
            public void onChanged(Integer integer) {
                if (integer>0){
                    txNotificationsBadgeUsers.setText(String.valueOf(integer));
                    txNotificationsBadgeUsers.setVisibility(View.VISIBLE);
                }else {
                    txNotificationsBadgeUsers.setVisibility(View.GONE);
                }

            }
        });

    }
    @Override
    protected void onResume() {
        super.onResume();
        if (roleUserLogeado.equals("admin")){
            loadData();
        }
    }

    @Override
    public void onListFragmentInteraction(Ticket item) {

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.activity_main_menu, menu);
        final MenuItem settingsItem = menu.findItem(R.id.profile);

        /*Glide
                .with(this)
                .asBitmap()
                .load(account.getPhotoUrl().toString())
                .circleCrop()
                .into(new CustomTarget<Bitmap>(100,100) {
                    @Override
                    public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                        settingsItem.setIcon(new BitmapDrawable(getResources(), resource));
                    }

                    @Override
                    public void onLoadCleared(@Nullable Drawable placeholder) {

                    }
                });*/

        return true;
    }
}

