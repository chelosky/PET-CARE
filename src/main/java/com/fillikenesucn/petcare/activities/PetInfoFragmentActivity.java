package com.fillikenesucn.petcare.activities;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.fillikenesucn.petcare.R;
import com.fillikenesucn.petcare.models.Pet;
import com.fillikenesucn.petcare.utils.DataHelper;
import com.fillikenesucn.petcare.utils.IOHelper;

/**
 * Esta clase representa a la actividad que desplegará la información asociada a una mascota del sistema
 * @author: Marcelo Lazo Chavez
 * @version: 26/03/2020
 */
public class PetInfoFragmentActivity extends FragmentActivity {
    // VARIABLES
    private final int RESULT_MODIFY_PET_INFO = 444;
    private TextView txtEPC;
    private Button btnVerLista;
    private Button btnEditInfo;
    private Button btnDesvincular;
    private Pet petOBJ;

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
        GetEPCExtra();
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
        // FUNCIONALIDAD PARA INSTANCIAR LA ACTIVIDAD PARA EDITAR A LA MASCOTA
        btnEditInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(PetInfoFragmentActivity.this, ModifyPetInfoFragmentActivity.class);
                intent.putExtra("EPC",txtEPC.getText().toString());
                startActivityForResult(intent, RESULT_MODIFY_PET_INFO);
            }
        });

        btnDesvincular = (Button) findViewById(R.id.btnDesvincular);
        btnDesvincular.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DeleteAPet();
            }
        });
    }

    /**
     * Método que se encarga de desvincular a una mascota registrada en el sistema
     * Desplegando un dialog de confirmación y liberando el epc para que pueda ser usado por otra mascota
     */
    private void DeleteAPet(){
        AlertDialog.Builder builder = DataHelper.CreateAlertDialog(PetInfoFragmentActivity.this,
                                                                    "Confirmación",
                                                                    "¿Está seguro de que desea desvincular a la mascota?");
        builder.setPositiveButton("Confirmar",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if(IOHelper.unlinkPet(PetInfoFragmentActivity.this, txtEPC.getText().toString())){
                            Toast.makeText(PetInfoFragmentActivity.this,"MASCOTA ELIMINADA", Toast.LENGTH_SHORT).show();
                            finish();
                        }else{
                            Toast.makeText(PetInfoFragmentActivity.this,"HA OCURRIDO UN ERROR!", Toast.LENGTH_SHORT).show();
                        }

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

    /**
     * Método que se encarga de obtener el epc de la mascota que se entrega como extra
     */
    private void GetEPCExtra(){
        Bundle extras = getIntent().getExtras();
        String txtExtra = extras.getString("EPC");
        LoadInfoPet(txtExtra);
    }

    /**
     * Método que se encarga de obtener la información asociada a una mascota en base a su EPC asociado
     * y actualizar la vista con su información correspondiente
     */
    private void LoadInfoPet(String epcValue){
        petOBJ = IOHelper.getPet(PetInfoFragmentActivity.this, epcValue);
        SetInfoPetView();
    }

    /**
     * Método que setea la información de la vista con los datos de la mascota segun su epc
     */
    private void SetInfoPetView(){
        EditText txtName = (EditText) findViewById(R.id.nombre);
        txtName.setText(petOBJ.getName());
        EditText txtTipo = (EditText) findViewById(R.id.tipo);
        txtTipo.setText(petOBJ.getSpecies());
        EditText txtSexo = (EditText) findViewById(R.id.sexo);
        txtSexo.setText(petOBJ.getSex());
        EditText txtNacimiento = (EditText) findViewById(R.id.nacimiento);
        txtNacimiento.setText(petOBJ.getBirthdate());
        EditText txtDireccion = (EditText) findViewById(R.id.direccion);
        txtDireccion.setText(petOBJ.getAddress());
        EditText txtAlergias = (EditText) findViewById(R.id.alergias);
        txtAlergias.setText(petOBJ.getAllergies());
        txtEPC.setText(petOBJ.getEPC());
    }

    /**
     * Método que se encarga de actualizar los datos de la mascota, si sus datos fueron modificados
     * @param requestCode codigo de la peticion
     * @param resultCode codigo de la respuesta (OK or Cancel)
     * @param data Conjuntos de datos que tendra el valor del epc actualizado de la mascota (result -> new epc)
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if(data == null){
            Toast.makeText(this, "Error TAG INFO", Toast.LENGTH_SHORT).show();
            return;
        }
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == RESULT_MODIFY_PET_INFO){
            if(resultCode == RESULT_OK){
                String txtEPC = data.getStringExtra("result");
                // NUEVO EPC
                LoadInfoPet(txtEPC);
            }
            if(resultCode == RESULT_CANCELED){
                // do nothing
            }
        }
    }
}
