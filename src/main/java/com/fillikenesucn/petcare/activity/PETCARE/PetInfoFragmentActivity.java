package com.fillikenesucn.petcare.activity.PETCARE;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.fillikenesucn.petcare.R;
import com.fillikenesucn.petcare.activity.PETCARE.utils.DataHelper;

/**
 * Esta clase representa a la actividad que desplegará la información asociada a una mascota del sistema
 * @author: Marcelo Lazo Chavez
 * @version: 26/03/2020
 */
public class PetInfoFragmentActivity extends FragmentActivity {
    // VARIABLES
    private TextView txtEPC;
    private Button btnVerLista;
    private Button btnEditInfo;
    private Button btnDesvincular;

    /**
     * Constructor de la actividad
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pet_info_fragment);
        txtEPC = (TextView) findViewById(R.id.txtEPC);
        // OBTIENE LA INFORMACIÓN DE LA MASCOTA EN BASE AL EPC ASOCIADO
        LoadInfoPet();
        btnVerLista = (Button)findViewById(R.id.btnVerLista);
        // FUNCIONALIDAD PARA INSTANCIAR LA ACTIVIDAD PARA VER EL LISTADO DE ACONTECIMIENTOS ASOCIADOS A LA MASCOTA
        btnVerLista.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(PetInfoFragmentActivity.this, EventListFragmentActivity.class);
                intent.putExtra("EPC",txtEPC.getText().toString());
                startActivity(intent);
            }
        });

        btnEditInfo = (Button)findViewById(R.id.btnEditInfo);
        // FUNCIONALIDAD PARA INSTANCIAR LA ACTIVIDAD PARA AÑADIR UN NUEVO ACONTECIMIENTO A LA MASCOTA
        btnEditInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(PetInfoFragmentActivity.this, ModifyPetInfoFragmentActivity.class);
                intent.putExtra("EPC",txtEPC.getText().toString());
                startActivity(intent);
            }
        });

        btnDesvincular = (Button) findViewById(R.id.btnDesvincular);
        btnDesvincular.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = DataHelper.CreateAlertDialog(PetInfoFragmentActivity.this,"Confirmación","¿Está seguro de que desea desvincular a la mascota?");
                builder.setPositiveButton("Confirmar",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Toast.makeText(PetInfoFragmentActivity.this,"DELETE PET", Toast.LENGTH_SHORT).show();
                            }
                        });
                builder.setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // do nothing
                    }
                });
                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });
    }

    /**
     * Método que se encarga de obtener la información asociada a una mascota en base a su EPC asociado
     * y actualizar la vista con su información correspondiente
     */
    private void LoadInfoPet(){
        Bundle extras = getIntent().getExtras();
        String txtExtra = extras.getString("EPC");
        txtEPC.setText(txtExtra);
    }
}
