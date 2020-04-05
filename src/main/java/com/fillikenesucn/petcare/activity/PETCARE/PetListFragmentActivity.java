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
        petList.add(new Pet("PERRO 1", "MACHO", "30/01/2019","XXXX","","PERRO","XXX1"));
        petList.add(new Pet("GATO 1", "MACHO", "30/01/2019","XXXX","","GATO","XXX2"));
        petList.add(new Pet("PERRO 2", "MACHO", "30/01/2019","XXXX","","PERRO","XXX3"));
        petList.add(new Pet("GATO 2", "HEMBRA", "30/01/2019","XXXX","","GATO","XXX4"));
        petList.add(new Pet("PERRO 3", "HEMBRA", "30/01/2019","XXXX","","PERRO","XXX5"));
        petList.add(new Pet("GATO 3", "HEMBRA", "30/01/2019","XXXX","","GATO","XXX6"));
        petList.add(new Pet("PERRO 4", "MACHO", "30/01/2019","XXXX","","PERRO","XXX7"));
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

    public void OpenInfoPet(String txtEPC){
        Intent intent = new Intent(this, PetInfoFragmentActivity.class);
        intent.putExtra("EPC",txtEPC);
        startActivity(intent);
    }
}

