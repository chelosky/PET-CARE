package com.fillikenesucn.petcare.activity.PETCARE;

import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;

import com.fillikenesucn.petcare.R;
import com.fillikenesucn.petcare.activity.PETCARE.models.Pet;
import com.fillikenesucn.petcare.activity.PETCARE.utils.IOHelper;
import com.fillikenesucn.petcare.activity.PETCARE.utils.PetListAdapter;

import java.util.ArrayList;

/**
 * Esta clase representa a la actividad que se encargará de desplegar el listado de mascotas registradas en el sistema
 * @author: Marcelo Lazo Chavez
 * @version: 30/03/2020
 */
public class PetListFragmentActivity extends FragmentActivity {
    private Button btnAgregarMascota;
    private ArrayList<Pet> petList = new ArrayList<>();

    /**
     * CONSTRUCTOR DE LA ACTIVIDAD
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pet_list_fragment);
        btnAgregarMascota = (Button) findViewById(R.id.btnAgregarMascota);
        btnAgregarMascota.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(PetListFragmentActivity.this, RegisterPetFragmentActivity.class);
                        startActivity(intent);
                    }
                }
        );
        InitPetData();
    }

    /**
     * Método que se encarga de obtener las mascotas que se encuentran registradas en el sistema
     */
    private void InitPetData(){
        petList = IOHelper.PetList(PetListFragmentActivity.this);
        InitPetList();
    }

    /**
     * Método que se encarga de actualiar el listado/recyclerview de la actividad
     * permitiendo asociar un adapter para brindarle funcionalidad a cada uno de los item pertenecientes
     * a la mascotas del sistema
     */
    private void InitPetList(){
        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        PetListAdapter adapter = new PetListAdapter(this, petList);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    /**
     * Método que se encarga en redireccionar a la actividad de pet info con el epc de la mascota que fue seleccionada del listado
     * @param txtEPC es el epc asociado a la mascota
     */
    public void OpenInfoPet(String txtEPC){
        Intent intent = new Intent(this, PetInfoFragmentActivity.class);
        intent.putExtra("EPC",txtEPC);
        startActivity(intent);
    }

    /**
     * Método que se encarga de refrescar la información de la actividad, una vez que esta vuelva a ser la actividad
     * principal de la aplicación
     */
    @Override
    protected void onRestart() {
        super.onRestart();
        InitPetData();
    }
}

