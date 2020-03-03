package com.example.proyectosataapp;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.example.proyectosataapp.models.UserResponseRegister;
import com.example.proyectosataapp.usuarios.UsuarioViewModel;
import com.google.android.material.bottomnavigation.BottomNavigationItemView;
import com.google.android.material.bottomnavigation.BottomNavigationMenuView;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import java.util.List;

import okhttp3.ResponseBody;

public class MainActivity extends AppCompatActivity {

    TextView txNotificationsBadgeUsers;
    UsuarioViewModel usuarioViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
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

        //Código para poner un badge de notificación
        BottomNavigationMenuView bottomNavigationMenuView =
                (BottomNavigationMenuView) navView.getChildAt(0);
        View v = bottomNavigationMenuView.getChildAt(2);
        BottomNavigationItemView itemView = (BottomNavigationItemView) v;

        View badge = LayoutInflater.from(this)
                .inflate(R.layout.notification_badge, itemView, true);
        txNotificationsBadgeUsers = badge.findViewById(R.id.txNotificationsBadge);
        txNotificationsBadgeUsers.setVisibility(View.GONE);

        usuarioViewModel.getUsersFromRepo().observe(this, new Observer<List<UserResponseRegister>>() {
            @Override
            public void onChanged(List<UserResponseRegister> userResponseRegisters) {
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


}
