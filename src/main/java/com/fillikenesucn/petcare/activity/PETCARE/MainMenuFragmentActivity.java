package com.fillikenesucn.petcare.activity.PETCARE;

import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.fillikenesucn.petcare.R;

public class MainMenuFragmentActivity extends FragmentActivity {
    private Button btnRegistrarMascota;
    private Button btnSalir;
    private Button btnScanearMascota;

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
        btnSalir = (Button)findViewById(R.id.btnSalir);
        btnSalir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        btnScanearMascota = (Button) findViewById(R.id.btnEscanearMascota);
        btnScanearMascota.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainMenuFragmentActivity.this, ScannerMainFragmentActivity.class);
                startActivity(intent);
            }
        });
    }
}
