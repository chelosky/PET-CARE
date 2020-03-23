package com.fillikenesucn.petcare.activity.PETCARE;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.Button;

import com.fillikenesucn.petcare.R;

public class PetListFragmentActivity extends FragmentActivity {
    private Button btnAgregarMascota;

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
    }
}

