package com.fillikenesucn.petcare.activity.PETCARE;

import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.fillikenesucn.petcare.R;

public class PetInfoFragmentActivity extends FragmentActivity {
    private TextView txtEPC;
    private Button btnVerLista;
    private Button btnAgregarEvento;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pet_info_fragment);
        txtEPC = (TextView) findViewById(R.id.txtEPC);
        LoadInfoPet();

        btnVerLista = (Button)findViewById(R.id.btnVerLista);
        btnVerLista.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(PetInfoFragmentActivity.this, EventListFragmentActivity.class);
                startActivity(intent);
            }
        });

        btnAgregarEvento = (Button)findViewById(R.id.btnAgregarEvento);
        btnAgregarEvento.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(PetInfoFragmentActivity.this, AddEventOldPetFragmentActivity.class);
                startActivity(intent);
            }
        });
    }

    private void LoadInfoPet(){
        Bundle extras = getIntent().getExtras();
        String txtExtra = extras.getString("EPC");
        txtEPC.setText(txtExtra);
    }
}
