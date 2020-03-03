package com.example.proyectosataapp;

import android.os.Bundle;
import android.view.View;

import com.example.proyectosataapp.equipos.NuevoEquipoDialogFragMent;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

public class MainActivity extends AppCompatActivity {
    FloatingActionButton nuevoEquipo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        nuevoEquipo = findViewById(R.id.nuevoEquipo);
        BottomNavigationView navView = findViewById(R.id.nav_view);
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
    }


}
