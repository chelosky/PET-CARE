package com.fillikenesucn.petcare.activity.PETCARE;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.Button;

import com.fillikenesucn.petcare.R;

public class PetNotFoundFragmentActivity extends FragmentActivity {
    private Button btnRegistrarMascota;
    private Button btnNoRegistrarMascota;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pet_not_found_fragment);

        btnRegistrarMascota = (Button)findViewById(R.id.btnRegistrarMascota);
        btnRegistrarMascota.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(PetNotFoundFragmentActivity.this, RegisterScannedPetFragmentActivity.class);
                startActivity(intent);
            }
        });

        btnNoRegistrarMascota = (Button)findViewById(R.id.btnNoRegistrarMascota);
        btnNoRegistrarMascota.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(PetNotFoundFragmentActivity.this, MainMenuFragmentActivity.class);
                startActivity(intent);
            }
        });
    }
}
