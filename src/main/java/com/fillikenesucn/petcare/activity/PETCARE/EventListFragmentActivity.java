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
import com.fillikenesucn.petcare.activity.PETCARE.models.Pet;
import com.fillikenesucn.petcare.activity.PETCARE.utils.AcontecimientoListAdapter;
import com.fillikenesucn.petcare.activity.PETCARE.utils.IOHelper;

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
    ArrayList<Acontecimiento> acontecimientosList;
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
    }

    /**
     * Método que Obtiene el listado de acontecimientos pertenecientes a una mascota en base a su EPC
     */
    private void InitAcontecimientos(){
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        Pet currentPet = IOHelper.GetPet(EventListFragmentActivity.this, txtEPC);
        acontecimientosList = currentPet.getEventList();
        AcontecimientoListAdapter adapter = new AcontecimientoListAdapter(this, acontecimientosList);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    /**
     * Método que se encarga de eliminar un acontecimiento del listado de acontecimientos de la mascota
     * @param index es la posicion del acontecimiento que se desea eliminar
     */
    public void DeleteAcontecimiento(int index){
        IOHelper.UpdateEventList(EventListFragmentActivity.this,txtEPC,index);
        InitAcontecimientos();
    }

    /**
     * Método que se encarga de obtener la información asociada a una mascota en base a su EPC asociado
     * y actualizar la vista con su información correspondiente
     */
    private void LoadInfoPet(){
        Bundle extras = getIntent().getExtras();
        txtEPC = extras.getString("EPC");
        // OBTIENE LA INFORMACIÓN DE LOS ACONTECIMIENTOS DE LA MASCOTA
        InitAcontecimientos();
    }

    /**
     * Método que se encarga de refrescar el listado de acontecimientos, cuando la actividad vuelve a ser la actividad
     * principal del sistema ( deja de estar en pausa)
     */
    @Override
    protected void onRestart() {
        super.onRestart();
        InitAcontecimientos();
    }
}