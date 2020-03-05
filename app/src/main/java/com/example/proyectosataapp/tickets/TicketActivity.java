package com.example.proyectosataapp.tickets;

import android.content.Intent;
import android.os.Bundle;

import com.example.proyectosataapp.models.Ticket;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;

import com.example.proyectosataapp.R;

public class TicketActivity extends AppCompatActivity implements TicketFragment.OnListFragmentInteractionListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ticket);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(TicketActivity.this, CreateTicketActivity.class));
            }
        });
    }

    @Override
    public void onListFragmentInteraction(Ticket item) {

    }
}
