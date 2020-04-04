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

public class PetListFragmentActivity extends FragmentActivity {
    private Button btnAgregarMascota;
    private ArrayList<Pet> petList = new ArrayList<>();
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

    private void InitPetData(){
        petList.add(new Pet("PERRO 1", "MACHO", "30/01/2019","XXXX","","PERRO","XXX"));
        petList.add(new Pet("GATO 1", "MACHO", "30/01/2019","XXXX","","GATO","XXX"));
        petList.add(new Pet("PERRO 2", "MACHO", "30/01/2019","XXXX","","PERRO","XXX"));
        petList.add(new Pet("GATO 2", "HEMBRA", "30/01/2019","XXXX","","GATO","XXX"));
        petList.add(new Pet("PERRO 3", "HEMBRA", "30/01/2019","XXXX","","PERRO","XXX"));
        petList.add(new Pet("GATO 3", "HEMBRA", "30/01/2019","XXXX","","GATO","XXX"));
        petList.add(new Pet("PERRO 4", "MACHO", "30/01/2019","XXXX","","PERRO","XXX"));
        InitPetList();
    }

    private void InitPetList(){
        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        PetListAdapter adater = new PetListAdapter(this, petList);
        recyclerView.setAdapter(adater);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }
}

