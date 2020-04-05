package com.fillikenesucn.petcare.activity.PETCARE;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.fillikenesucn.petcare.R;
import com.fillikenesucn.petcare.activity.PETCARE.models.Acontecimiento;
import com.fillikenesucn.petcare.activity.PETCARE.utils.AcontecimientoListAdapter;

import java.util.ArrayList;

/**
 * Esta clase representa a la actividad que se desplegará el listado de acontecimientos asociado a una mascota en especifico
 * @author: Marcelo Lazo Chavez
 * @version: 29/03/2020
 */
public class EventListFragmentActivity extends FragmentActivity {
    // VARIABLES
    Button btnAgregarEvento;
    String txtEPC;

    /**
     * Constructor de la Actividad
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_list_fragment);
        btnAgregarEvento = (Button) findViewById(R.id.btnAgregarEvento);
        LoadInfoPet();
        // INICIA LA ACTIVIDAD PARA AGREGAR UN NUEVO ACONTECIMIENTO PARA UNA MASCOTA, YA SABIENDO SU EPC ASOCIADO
        btnAgregarEvento.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent( EventListFragmentActivity.this, AddEventOldPetFragmentActivity.class);
                        intent.putExtra("EPC",txtEPC);
                        startActivity(intent);
                    }
                }
        );
        // OBTIENE LA INFORMACIÓN DE LOS ACONTECIMIENTOS DE LA MASCOTA
        InitAcontecimientos();
    }

    /**
     * Método que Obtiene el listado de acontecimientos pertenecientes a una mascota en base a su EPC
     */
    private void InitAcontecimientos(){
        RecyclerView recycler_view = (RecyclerView) findViewById(R.id.recycler_view);
        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        ArrayList<Acontecimiento> acontecimientosList = new ArrayList<>();
        acontecimientosList.add(new Acontecimiento("TITULO 1", "30/01/2020","Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua"));
        acontecimientosList.add(new Acontecimiento("TITULO 2", "30/01/2020","Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua"));
        acontecimientosList.add(new Acontecimiento("TITULO 3", "30/01/2020","Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua"));
        acontecimientosList.add(new Acontecimiento("TITULO 4", "30/01/2020","Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua"));
        acontecimientosList.add(new Acontecimiento("TITULO 5", "30/01/2020","Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua"));
        acontecimientosList.add(new Acontecimiento("TITULO 6", "30/01/2020","Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua"));
        acontecimientosList.add(new Acontecimiento("TITULO 7", "30/01/2020","Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua"));
        AcontecimientoListAdapter adapter = new AcontecimientoListAdapter(this, acontecimientosList);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    /**
     * Método que se encarga de eliminar un acontecimiento del listado de acontecimientos de la mascota
     * @param index es la posicion del acontecimiento que se desea eliminar
     */
    public void DeleteAcontecimiento(int index){
        //Toast.makeText(EventListFragmentActivity.this, String.valueOf(index) , Toast.LENGTH_SHORT).show();
        Toast.makeText(EventListFragmentActivity.this, "DELETE EVENT " + index  , Toast.LENGTH_SHORT).show();
    }

    /**
     * Método que se encarga de obtener la información asociada a una mascota en base a su EPC asociado
     * y actualizar la vista con su información correspondiente
     */
    private void LoadInfoPet(){
        Bundle extras = getIntent().getExtras();
        txtEPC = extras.getString("EPC");
    }
}