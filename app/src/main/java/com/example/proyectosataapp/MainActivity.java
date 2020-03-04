package com.example.proyectosataapp;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.SearchView;

import android.view.View;

import com.example.proyectosataapp.equipos.NuevoEquipoDialogFragMent;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import com.example.proyectosataapp.models.UserResponseRegister;
import com.example.proyectosataapp.usuarios.UsuarioViewModel;
import com.google.android.material.bottomnavigation.BottomNavigationItemView;
import com.google.android.material.bottomnavigation.BottomNavigationMenuView;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.view.MenuItemCompat;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    FloatingActionButton nuevoEquipo;


    TextView txNotificationsBadgeUsers;
    UsuarioViewModel usuarioViewModel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        nuevoEquipo = findViewById(R.id.nuevoEquipo);
        BottomNavigationView navView = findViewById(R.id.nav_view);
        usuarioViewModel = new ViewModelProvider(this).get(UsuarioViewModel.class);

        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_home, R.id.navigation_dashboard, R.id.navigation_notifications)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(navView, navController);



        nuevoEquipo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NuevoEquipoDialogFragMent dialog = new NuevoEquipoDialogFragMent();
                dialog.show(getSupportFragmentManager(), "NuevoEquipoDialogFragMent");
            }
        });

        //Código para poner un badge de notificación
        BottomNavigationMenuView bottomNavigationMenuView =
                (BottomNavigationMenuView) navView.getChildAt(0);
        View v = bottomNavigationMenuView.getChildAt(2);
        BottomNavigationItemView itemView = (BottomNavigationItemView) v;

        View badge = LayoutInflater.from(this)
                .inflate(R.layout.notification_badge, itemView, true);
        txNotificationsBadgeUsers = badge.findViewById(R.id.txNotificationsBadge);
        txNotificationsBadgeUsers.setVisibility(View.GONE);

        usuarioViewModel.getUsersNoValidated().observe(this, new Observer<List<UserResponseRegister>>() {
            @Override
            public void onChanged(List<UserResponseRegister> userResponseRegisters) {
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
        /*AHBottomNavigation bottomNavigation = findViewById(R.id.nav_view);
        AHBottomNavigationItem item1 = new AHBottomNavigationItem(R.string.title_home,R.drawable.ic_home_black_24dp,R.color.colorPrimary);
        AHBottomNavigationItem item2 = new AHBottomNavigationItem(R.string.title_dashboard,R.drawable.ic_dashboard_black_24dp,R.color.colorPrimary);
        AHBottomNavigationItem item3 = new AHBottomNavigationItem(R.string.title_usuarios,R.drawable.ic_supervisor_account_black_24dp,R.color.colorPrimary);

        bottomNavigation.addItem(item1);
        bottomNavigation.addItem(item2);
        bottomNavigation.addItem(item3);


        AHNotification notification = new AHNotification.Builder()
                .setText("1")
                .setBackgroundColor(ContextCompat.getColor(this,R.color.colorPrimary))
                .setTextColor(ContextCompat.getColor(this,R.color.colorAccent))
                .build();

        bottomNavigation.setNotification(notification,2);

        // Set listeners
        bottomNavigation.setOnTabSelectedListener(new AHBottomNavigation.OnTabSelectedListener() {
            @Override
            public boolean onTabSelected(int position, boolean wasSelected) {
                // Do something cool here...
                return true;
            }
        });
        bottomNavigation.setOnNavigationPositionListener(new AHBottomNavigation.OnNavigationPositionListener() {
            @Override public void onPositionChange(int y) {
                // Manage the new y position
            }
        });*/

    }

    @Override
    public boolean onCreateOptionsMenu(@NonNull Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.equipo_menu, menu);
        MenuItem buscador = menu.findItem(R.id.menu_equipo_buscador);
        SearchView searchView = (SearchView) buscador.getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
        return true;
    }

}
