package com.fillikenesucn.petcare.activities;

import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.fillikenesucn.petcare.R;
import com.fillikenesucn.petcare.utils.IOHelper;

import java.io.IOException;

/**
 * Esta clase representa a la actividad que se encarga de ejecutar el menu principal del sistema
 * @author: Marcelo Lazo Chavez
 * @version: 03/04/2020
 */
public class MainMenuFragmentActivity extends FragmentActivity {
    private Button btnScanearMascota;
    private Button btnRegistrarMascota;
    private Button btnListadoMascotas;
    private Button btnAddEvent;
    private Button btnSalir;

    /**
     * Constructor de la actividad
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu_fragment);

        btnRegistrarMascota = (Button) findViewById(R.id.btnRegistrarMascota);
        btnRegistrarMascota.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(MainMenuFragmentActivity.this, RegisterPetFragmentActivity.class);
                        startActivity(intent);
                    }
                }
        );

        btnScanearMascota = (Button) findViewById(R.id.btnEscanearMascota);
        btnScanearMascota.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainMenuFragmentActivity.this, ScannerMainFragmentActivity.class);
                intent.putExtra("STATUS","MAIN-MENU");
                startActivity(intent);
            }
        });

        btnAddEvent = (Button)findViewById(R.id.btnAgregarEvento);
        btnAddEvent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainMenuFragmentActivity.this, AddEventFragmentActivity.class);
                startActivity(intent);
            }
        });

        btnListadoMascotas = (Button)findViewById(R.id.btnListadoMascotas);
        btnListadoMascotas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainMenuFragmentActivity.this, PetListFragmentActivity.class);
                startActivity(intent);
            }
        });

        btnSalir = (Button)findViewById(R.id.btnSalir);
        btnSalir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        try {
            if (!IOHelper.checkFile(MainMenuFragmentActivity.this)) {
                IOHelper.writeJson(MainMenuFragmentActivity.this, "[]");
            }
        } catch (IOException e) {
            Log.d("DORAT", e.toString());

        }
    }

}
