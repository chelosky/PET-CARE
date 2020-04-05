package com.fillikenesucn.petcare.activity.PETCARE;

import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.fillikenesucn.petcare.R;
import com.fillikenesucn.petcare.activity.PETCARE.utils.IOHelper;
import com.google.gson.Gson;

public class MainMenuFragmentActivity extends FragmentActivity {
    private Button btnScanearMascota;
    private Button btnRegistrarMascota;
    private Button btnListadoMascotas;
    private Button btnAddEvent;
    private Button btnSalir;

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

        Boolean status = CheckFile();
        if (!status) {
            Gson gson = new Gson();
            InitJsonFile();
        }
        /** Esto debe eliminarse al terminar las pruebas */
        else {
            Gson gson = new Gson();
            InitJsonFile();
        }
    }

    /** Verifica si ya existe un archivo de texto */
    private Boolean CheckFile(){ return IOHelper.CheckFile(this); }

    /** Crea el archivo de texto vacío nuevo (elimina el anterior is existe) */
    private void InitJsonFile(){ IOHelper.WriteJson(this, "[]"); }
}
