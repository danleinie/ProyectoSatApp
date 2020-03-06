package com.example.proyectosataapp.tickets;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.telecom.Call;
import android.widget.TextView;

import com.example.proyectosataapp.R;
import com.example.proyectosataapp.common.Constantes;
import com.example.proyectosataapp.common.SharedPreferencesManager;
import com.example.proyectosataapp.models.Ticket;
import com.example.proyectosataapp.services.TicketService;
import com.example.proyectosataapp.servicesGenerators.ServiceGenerator;
import com.example.proyectosataapp.viewModel.EquipoViewModel;
import com.example.proyectosataapp.viewModel.TicketViewModel;
import com.mikhaellopez.circularimageview.CircularImageView;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.ResponseBody;

public class DetalleTicketActivity extends AppCompatActivity {

    String idTicket;
    String ubicacionEquipo;
    String nombreEquipo;
    Bitmap fotoEquipo;
    TicketViewModel ticketViewModel;
    TicketService service;
    ServiceGenerator serviceGenerator;
    @BindView(R.id.textView_tick_estado) TextView tvEstado;
    @BindView(R.id.textView_tick_creacion) TextView tvfechaCreacion;
    @BindView(R.id.textView_tick_creador) TextView tvCreadoPor;
    @BindView(R.id.textView_tick_tecnico_asig) TextView tvTecnico;
    @BindView(R.id.textView_tick_titulo) TextView tvTitulo;
    @BindView(R.id.textView_tick_descripcion) TextView tvDescripcion;
    @BindView(R.id.textView_tick_nombre_eq) TextView tvNombreEquipo;
    @BindView(R.id.textView_tick_ubi_eq) TextView tvUbicacion;
    @BindView(R.id.imageView_detalle_ticket) CircularImageView ivFoto;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle_ticket);
        ButterKnife.bind(this);
        Bundle extras = getIntent().getExtras();

        idTicket = extras.getString(Constantes.EXTRA_ID_TICKET);
        ubicacionEquipo = extras.getString("UbicacionEq");
        nombreEquipo = extras.getString("nombreEq");

        service = serviceGenerator.createService(TicketService.class);
        ticketViewModel = new ViewModelProvider(this).get(TicketViewModel.class);
        ticketViewModel.getTicketById(idTicket).observeForever(new Observer<Ticket>() {
            @Override
            public void onChanged(Ticket ticket) {
               tvEstado.setText(ticket.getEstado());
               tvfechaCreacion.setText(ticket.getFechaCreacion());
               tvCreadoPor.setText(ticket.getCreadoPor().getName());
               if (ticket.getAsignaciones().isEmpty()){
                   tvTecnico.setText("No hay tecnico asignado");
               }else{
                   tvTecnico.setText(ticket.getAsignaciones().get(0).getTecnico_id());
               }

               tvTitulo.setText(ticket.getTitulo());
               tvDescripcion.setText(ticket.getDescripcion());
               tvNombreEquipo.setText(nombreEquipo);
               tvUbicacion.setText(ubicacionEquipo);
            }
        });







    }
}
