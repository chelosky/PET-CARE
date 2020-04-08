package com.fillikenesucn.petcare.activities;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
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
import com.fillikenesucn.petcare.models.Pet;
import com.fillikenesucn.petcare.utils.DataHelper;
import com.fillikenesucn.petcare.utils.IOHelper;

import java.util.Calendar;

/**
 * Esta clase representa a la actividad que se encarga de agregar la información de una mascota para un EPC ya escaneado
 * @author: Rodrigo Dorat Merejo
 * @version: 06/04/2020
 */
public class RegisterScannedPetFragmentActivity extends FragmentActivity {

    // VARIABLES
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
    private TextView txtEPC;

    /**
     * CONSTRUCTOR de la actividad
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_scanned_pet_fragment);

        this.spinner = findViewById(R.id.spinnerRegisterPet);
        ArrayAdapter<String> petsAdapter = new ArrayAdapter(this,android.R.layout.simple_spinner_item, DataHelper.GetSpecies());
        petsAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        this.spinner.setAdapter(petsAdapter);

        this.sMonthIni = this.calendar.get(Calendar.MONTH);
        this.sYearIni = this.calendar.get(Calendar.YEAR);
        this.sDayIni = this.calendar.get(Calendar.DAY_OF_MONTH);

        LoadInputs();

        LoadExtraEPC();
        btnFechaNacimiento.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog dpd = new DatePickerDialog(RegisterScannedPetFragmentActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                        et_fechaNacimiento.setText(i2 + "/" + (i1+1) + "/" + i);

                    }
                }, sYearIni, sMonthIni, sDayIni);
                dpd.show();
            }
        });

        btnRegist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RegisterPet();
            }
        });
    }

    /**
     * Método que crea el objeto mascota
     * @return retorna la nueva mascota creada
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
        String epc = txtEPC.getText().toString();
        Log.d("DORAT", "done pet");
        // Creamos el objeto mascota y lo guardamos en el archivo de texto
        return new Pet(name,sex,birthdate,address,allergies,species,epc);
    }

    /**
     * Método que registra la mascota en el sistema
     */
    private void RegisterPet() {
        Pet pet = NewPet();
        // Revisamos que la información sea válida
        if(DataHelper.VerificarMascotaValida(RegisterScannedPetFragmentActivity.this,pet)){
            // Si puede agregar a la mascota cierra la actividad
            if (IOHelper.addPet(RegisterScannedPetFragmentActivity.this,pet)) {
                Toast.makeText(RegisterScannedPetFragmentActivity.this, "INGRESO EXITOSO", Toast.LENGTH_SHORT).show();
                RedirectToPetList();
            }
        }
    }

    /**
     * Método que redirecciona a la lista de mascotas
     */
    private void RedirectToPetList(){
        Intent intent = new Intent(RegisterScannedPetFragmentActivity.this, PetListFragmentActivity.class);
        startActivity(intent);
        finish();
    }

    /**
     * Método que recibe el EPC de la actividad anterior y lo setea en una variable local
     */
    private void LoadExtraEPC(){
        Bundle extras = getIntent().getExtras();
        String txtExtra = extras.getString("EPC");
        txtEPC.setText(txtExtra);
    }

    /**
     * Método que carga los inputs del Layout
     */
    private void LoadInputs(){
        et_fechaNacimiento = (EditText)findViewById(R.id.fechaNacimiento);
        btnFechaNacimiento = (Button)findViewById(R.id.btnFechaNacimiento);

        radioGroup = (RadioGroup) findViewById(R.id.radioGroupRegistedPet);
        txtName = (EditText) findViewById(R.id.txtRegistedPetNombre);
        txtAddress = (EditText) findViewById(R.id.txtRegistedPetDireccion);
        txtAllergies = (EditText) findViewById(R.id.txtRegistedPetAllergies);
        txtEPC = (TextView) findViewById(R.id.txtRegistedPetEPC);
        btnRegist = (Button) findViewById(R.id.btnAddRegistedPet);
    }
}
