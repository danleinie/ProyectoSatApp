package com.example.proyectosataapp.ui.home;

import android.content.Context;
import android.content.Intent;
import android.media.audiofx.DynamicsProcessing;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.proyectosataapp.DetalleEquipoActivity;
import com.example.proyectosataapp.IFiltroListener;
import com.example.proyectosataapp.MyEquipoRecyclerViewAdapter;
import com.example.proyectosataapp.R;
import com.example.proyectosataapp.common.Constantes;
import com.example.proyectosataapp.common.MyApp;
import com.example.proyectosataapp.equipos.DialogFilterUbication;
import com.example.proyectosataapp.models.EquipoResponse;
import com.example.proyectosataapp.viewModel.EquipoViewModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class HomeFragment extends Fragment implements IFiltroListener {
     // TODO: Customize parameter argument names
        private static final String ARG_COLUMN_COUNT = "column-count";
        // TODO: Customize parameters
        private int mColumnCount = 1;
        private EquipoViewModel equipoViewModel;
        MyEquipoRecyclerViewAdapter adapter;
        RecyclerView recyclerView;
        MenuItem  busqueda;
        List<EquipoResponse> listadoEquipos;

    public HomeFragment() {
    }

    // TODO: Customize parameter initialization
    @SuppressWarnings("unused")
    public static HomeFragment newInstance(int columnCount) {
        HomeFragment fragment = new HomeFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_COLUMN_COUNT, columnCount);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);

        if (getArguments() != null) {
            mColumnCount = getArguments().getInt(ARG_COLUMN_COUNT);
        }

        equipoViewModel = new ViewModelProvider(getActivity()).get(EquipoViewModel.class);

        equipoViewModel.getIdEquipoSeleccionado().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String idEquipo) {
                if((idEquipo != null) && (idEquipo != " ")) {
                    Intent i = new Intent(MyApp.getCtx(), DetalleEquipoActivity.class);
                    i.putExtra(Constantes.EXTRA_ID_EQUIPO, idEquipo);
                    equipoViewModel.setIdEquipoSeleccionado(null);
                    startActivity(i);
                }
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_equipo_list, container, false);
        // Set the adapter
        if (view instanceof RecyclerView) {
            Context context = view.getContext();
            RecyclerView recyclerView = (RecyclerView) view;
            if (mColumnCount <= 1) {
                recyclerView.setLayoutManager(new LinearLayoutManager(context));
            } else {
                recyclerView.setLayoutManager(new GridLayoutManager(context, mColumnCount));
            }
            adapter = new MyEquipoRecyclerViewAdapter(
                    getActivity(),
                    null,
                    equipoViewModel);

            recyclerView.setAdapter(adapter);
        }
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        Toast.makeText(getActivity(), "onResume()", Toast.LENGTH_SHORT).show();

        //TODO IMPORTANTE
        equipoViewModel.getEquipos().observe(getActivity(), new Observer<List<EquipoResponse>>() {
            @Override
            public void onChanged(List<EquipoResponse> equipoResponses) {
                adapter.setData(equipoResponses);
                listadoEquipos =  equipoResponses;
            }
        });
    }

    @Override
    public void onPause() {
        super.onPause();
        Toast.makeText(getActivity(), "onPause()", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onClickFiltros(String filtro) {
        Log.i("filtro", filtro);
        llamadaBusqueda(filtro);

    }

    public List<EquipoResponse> llamadaBusqueda(String palabraClave){
        List<EquipoResponse> result = new ArrayList<>();
        for (EquipoResponse equipo : listadoEquipos ){
            for (String palabraClaveList : equipo.getPalabrasClaves()){
                if(palabraClaveList.equalsIgnoreCase(palabraClave) || palabraClaveList.toLowerCase().contains(palabraClave.toLowerCase())){
                    if (!result.contains(equipo)){
                        result.add(equipo);
                    }
                }
            }
        }
        adapter.setData(result); 
        return result;
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        menu.clear();
        super.onPrepareOptionsMenu(menu);
        getActivity().getMenuInflater().inflate(R.menu.equipo_menu, menu);
        busqueda = menu.findItem(R.id.app_bar_search);
        SearchView searchView = (SearchView) busqueda.getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                //itemLimpiarFiltro.setVisible(true);

                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                List<EquipoResponse> lista = llamadaBusqueda(newText);
                cargarBusqueda(lista);

                return false;
            }
        });
        super.onCreateOptionsMenu(menu, inflater);
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
            DialogFragment dialogoUbicacion =new DialogFilterUbication();
            dialogoUbicacion.setTargetFragment(this,0);
            dialogoUbicacion.show(Objects.requireNonNull(getFragmentManager()),"FiltroMonedaFragment");

        Toast.makeText( MyApp.getCtx(), "Filtro", Toast.LENGTH_SHORT).show();

        return super.onOptionsItemSelected(item);
    }


    public void cargarBusqueda (List<EquipoResponse> listaFiltrada){
        adapter.setData(listaFiltrada);
        adapter.notifyDataSetChanged();
    }
}