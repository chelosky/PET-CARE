package com.fillikenesucn.petcare.activities;

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
import android.widget.Spinner;
import android.widget.TextView;

import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.fillikenesucn.petcare.R;
import com.fillikenesucn.petcare.models.Pet;
import com.fillikenesucn.petcare.adapters.DataHelper;
import com.fillikenesucn.petcare.utils.IOHelper;

import java.util.Calendar;

/**
 * Esta clase representa a la actividad que se encarga de agregar la información de una mascota, necesita escanear el EPC
 * @author: Rodrigo Dorat Merejo
 * @version: 06/04/2020
 */
public class RegisterPetFragmentActivity extends FragmentActivity {

    private final int RESULT_SCANNER_PET_ADD = 616;

    // VARIABLES
    private TextView txtTAG;
    private Button btnFechaNacimiento;
    private EditText et_fechaNacimiento;
    private static final int DATE_ID = 0;
    private int nYearIni, nMonthIni, nDayIni, sYearIni, sMonthIni, sDayIni;
    private Calendar calendar = Calendar.getInstance();
    private Spinner spinner;

    private Button btnRegist;
    private EditText txtName;
    private RadioGroup radioGroup;
    private RadioButton radioButton;
    private EditText txtAddress;
    private EditText txtAllergies;
    private TextView txtTest;
    private Button btnScanTag;

    /**
     * CONSTRUCTOR de la actividad
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_pet_fragment);
        this.spinner = findViewById(R.id.spinnerRegisterPet);
        ArrayAdapter<String> petsAdapter = new ArrayAdapter(this,android.R.layout.simple_spinner_item, DataHelper.GetSpecies());
        petsAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        this.spinner.setAdapter(petsAdapter);

        this.sMonthIni = this.calendar.get(Calendar.MONTH);
        this.sYearIni = this.calendar.get(Calendar.YEAR);
        this.sDayIni = this.calendar.get(Calendar.DAY_OF_MONTH);

        LoadInputs();

        btnFechaNacimiento.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog dpd = new DatePickerDialog(RegisterPetFragmentActivity.this, new DatePickerDialog.OnDateSetListener() {
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
                Intent intent = new Intent(RegisterPetFragmentActivity.this, ScannerMainFragmentActivity.class);
                intent.putExtra("STATUS","PET-ADD");
                startActivityForResult(intent,RESULT_SCANNER_PET_ADD);
            }
        });

        // Sección de pruebas para leer los que hay dentro del archivo de 'base de datos'
        Button tempLeer = (Button)findViewById(R.id.leerjson);
        tempLeer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Ver el archivo de texto completo (desactivado)
                txtTest.setText(IOHelper.ReadFileString(RegisterPetFragmentActivity.this));
            }
        });

        // Sección que registra la mascota al pulsar el botón de guardar
        btnRegist = (Button) findViewById(R.id.btnRegist);
        btnRegist.setOnClickListener(
                new View.OnClickListener() {
                    /**
                     * Función que se encarga de agregar la nueva mascota a la lista de mascotas
                     * @param view
                     */
                    @Override
                    public void onClick(View view) { RegisterPet(); }
                }
        );
    }

    /**
     * Método que registra la mascota en el sistema
     */
    private Pet NewPet(){
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
        return new Pet(name,sex,birthdate,address,allergies,species,epc);
    }

    /**
     * Método que registra la mascota en el sistema
     */
    private void RegisterPet() {
        Pet pet = NewPet();
        // Revisamos que la información sea válida
        if(DataHelper.VerificarMascotaValida(RegisterPetFragmentActivity.this,pet)){
            // Si puede agregar a la mascota cierra la actividad
            if (IOHelper.AddPet(RegisterPetFragmentActivity.this,pet)) {
                Toast.makeText(RegisterPetFragmentActivity.this, "INGRESO EXITOSO", Toast.LENGTH_SHORT).show();
                RedirectToPetList();
            }
        }
    }

    /**
     * Método que redirecciona a la lista de mascotas
     */
    private void RedirectToPetList(){
        Intent intent = new Intent(RegisterPetFragmentActivity.this, PetListFragmentActivity.class);
        startActivity(intent);
        finish();
    }

    /**
     * Método que carga los inputs del Layout
     */
    private void LoadInputs(){
        btnScanTag = (Button)findViewById(R.id.btnScanTag);
        txtTAG = (TextView)findViewById(R.id.txtTAG);
        txtTAG.setText(DataHelper.GetDefaultTagRFID());

        et_fechaNacimiento = (EditText)findViewById(R.id.fechaNacimiento);
        btnFechaNacimiento = (Button)findViewById(R.id.btnFechaNacimiento);
        txtTest = (TextView) findViewById(R.id.txtTest);
        txtName = (EditText) findViewById(R.id.txtName);
        radioGroup = (RadioGroup) findViewById(R.id.radio);
        spinner = findViewById(R.id.spinnerRegisterPet);
        txtAddress = (EditText) findViewById(R.id.txtAddress);
        txtAllergies = (EditText) findViewById(R.id.txtAllergies);
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

        if (requestCode == RESULT_SCANNER_PET_ADD){
            if(resultCode == RESULT_OK){
                String txtEPC = data.getStringExtra("result");
                txtTAG.setText(txtEPC);
            }
            if(resultCode == RESULT_CANCELED){
                Toast.makeText(this, "ERROR AL OBTENER TAG", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
