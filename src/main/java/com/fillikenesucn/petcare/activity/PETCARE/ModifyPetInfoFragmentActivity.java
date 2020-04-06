package com.fillikenesucn.petcare.activity.PETCARE;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.fillikenesucn.petcare.R;
import com.fillikenesucn.petcare.activity.PETCARE.models.Pet;
import com.fillikenesucn.petcare.activity.PETCARE.utils.DataHelper;
import com.fillikenesucn.petcare.activity.PETCARE.utils.IOHelper;

import java.util.Calendar;

/**
 * Esta clase representa a la actividad que se encarga de actualizar la información de una mascota especifica
 * @author: Marcelo Lazo Chavez
 * @version: 2/04/2020
 */
public class ModifyPetInfoFragmentActivity extends FragmentActivity {

    //VARIABLES
    private final int RESULT_SCANNER_PET_MODIFY = 616;
    private TextView txtTAG;

    // CALENDAR STUFF
    private Button btnFechaNacimiento;
    private EditText et_fechaNacimiento;
    private static final int DATE_ID = 0;
    private int nYearIni, nMonthIni, nDayIni, sYearIni, sMonthIni, sDayIni;
    private Calendar calendar = Calendar.getInstance();
    private Spinner spinner;

    private Button btnModificarMascota;
    private EditText txtName;
    private RadioGroup radioGroup;
    private RadioButton radioButton;
    private EditText txtAddress;
    private EditText txtAllergies;
    private Button btnScanTag;

    private Pet petOBJ;
    private ArrayAdapter<String> petsAdapter;
    /**
     * CONSTRUCTOR de la actividad
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modify_pet_info_fragment);
        this.spinner = findViewById(R.id.spinnerRegisterPet);
        petsAdapter = new ArrayAdapter(this,android.R.layout.simple_spinner_item, DataHelper.GetSpecies());
        petsAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        this.spinner.setAdapter(petsAdapter);

        et_fechaNacimiento = (EditText)findViewById(R.id.fechaNacimiento);
        btnFechaNacimiento = (Button)findViewById(R.id.btnFechaNacimiento);
        txtName = (EditText) findViewById(R.id.txtName);
        radioGroup = (RadioGroup) findViewById(R.id.radio);
        spinner = findViewById(R.id.spinnerRegisterPet);
        txtAddress = (EditText) findViewById(R.id.txtAddress);
        txtAllergies = (EditText) findViewById(R.id.txtAllergies);
        btnModificarMascota = (Button) findViewById(R.id.btnModificarMascota);

        this.sMonthIni = this.calendar.get(Calendar.MONTH);
        this.sYearIni = this.calendar.get(Calendar.YEAR);
        this.sDayIni = this.calendar.get(Calendar.DAY_OF_MONTH);

        btnScanTag = (Button)findViewById(R.id.btnScanTag);
        txtTAG = (TextView)findViewById(R.id.txtTAG);

        // OBTENEMOS LA INFORMACIÓN DE LA MASCOTA
        LoadInfoPet();

        btnFechaNacimiento.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog dpd = new DatePickerDialog(ModifyPetInfoFragmentActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                        et_fechaNacimiento.setText(i2 + "/" + (i1+1) + "/" + i);

                    }
                }, sYearIni, sMonthIni, sDayIni);
                dpd.show();
            }
        });

        btnScanTag.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ModifyPetInfoFragmentActivity.this, ScannerMainFragmentActivity.class);
                intent.putExtra("STATUS","PET-MODIFY");
                startActivityForResult(intent,RESULT_SCANNER_PET_MODIFY);
            }
        });

        btnModificarMascota.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ModififyCurrentPet();
            }
        });
    }

    /**
     * Método que retorna la mascota con los datos actualizados
     * @return retorna la mascota con los datos modificados/actualizados
     */
    private Pet GetModifiedPet(){
        // Obtenemos el ID de la opción seleccionada
        int selectedId = radioGroup.getCheckedRadioButtonId();
        // Obtenemos el botón
        radioButton = (RadioButton) findViewById(selectedId);
        // Extraemos la información de los inputs
        String name = txtName.getText().toString();
        String sex = radioButton.getText().toString();
        String species = spinner.getSelectedItem().toString();
        String birthdate = et_fechaNacimiento.getText().toString();
        String address = txtAddress.getText().toString();
        String allergies = txtAllergies.getText().toString();
        String epc = txtTAG.getText().toString();
        // Creamos el objeto mascota y lo guardamos en el archivo de texto
        Pet newPet = new Pet(name,sex,birthdate,address,allergies,species,epc);
        return newPet;
    }

    /**
     * Método que se encarga de actualizar los valores de la mascota
     */
    private void ModififyCurrentPet(){
        Pet newPet = GetModifiedPet();
        if(DataHelper.VerificarMascotaValida(ModifyPetInfoFragmentActivity.this, newPet)){
            IOHelper.UpdatePetInfo(ModifyPetInfoFragmentActivity.this, newPet, petOBJ.getEPC());
            Intent resultIntent = new Intent();
            resultIntent.putExtra("result",txtTAG.getText().toString());
            setResult(RESULT_OK, resultIntent);
            finish();
        }
    }
    /**
     * Método que se encarga de retornar el valor del EPC a la actividad padre
     * (Utilizado por el agregar mascota y agregar acontecimiento global)
     * @param txtEPC Es el valor asociado al epc del tag escaneado
     */
    private void ReturnEPCParent(String txtEPC){
        Intent resultIntent = new Intent();
        resultIntent.putExtra("result",txtEPC);
        setResult(RESULT_OK, resultIntent);
        finish();
    }

    /**
     * Mpetodo que se utiliza para recibir el epc que se lea del escaner y actualizar el campo correspondiente en la vista
     * @param requestCode CODIGO DE LA PETICION
     * @param resultCode CODIGO DEL RESULTADO DEL ESCANER
     * @param data CONJUNTO DE DATOS QUE SE RETORNAR (OBTENER STRING "result" -> ES EL EPC)
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RESULT_SCANNER_PET_MODIFY){
            if(resultCode == RESULT_OK){
                String txtEPC = data.getStringExtra("result");
                txtTAG.setText(txtEPC);
            }
            if(resultCode == RESULT_CANCELED){
                Toast.makeText(this, "ERROR AL OBTENER TAG", Toast.LENGTH_SHORT).show();
            }
        }
    }

    /**
     * Método que se encarga de obtener la información asociada a una mascota en base a su EPC asociado
     * y actualizar la vista con su información correspondiente
     */
    private void LoadInfoPet(){
        Bundle extras = getIntent().getExtras();
        String txtExtra = extras.getString("EPC");
        petOBJ = IOHelper.GetPet(ModifyPetInfoFragmentActivity.this, txtExtra);
        SetInfoPetView();
    }

    /**
     * Método que se encarga de actualizar la información de la mascota en la vista de la actividad
     */
    private void SetInfoPetView(){
        et_fechaNacimiento.setText(petOBJ.getBirthdate());
        txtName.setText(petOBJ.getName());
        spinner.setSelection(petsAdapter.getPosition(petOBJ.getSpecies()));
        txtAddress.setText(petOBJ.getAddress());
        txtAllergies.setText(petOBJ.getAllergies());
        txtTAG.setText(petOBJ.getEPC());
        for(int i=0; i< radioGroup.getChildCount(); i++){
            RadioButton radioButton = (RadioButton)radioGroup.getChildAt(i);
            if(radioButton.getText().toString().toUpperCase().equals(petOBJ.getSex().toUpperCase())){
                radioButton.setChecked(true);
                break;
            }
        }
    }
}
