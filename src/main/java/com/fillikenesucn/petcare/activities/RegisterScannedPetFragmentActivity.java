package com.fillikenesucn.petcare.activities;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;

import com.fillikenesucn.petcare.R;
import com.fillikenesucn.petcare.models.Pet;
import com.fillikenesucn.petcare.adapters.DataHelper;
import com.fillikenesucn.petcare.utils.IOHelper;

import java.util.Calendar;

public class RegisterScannedPetFragmentActivity extends FragmentActivity {
    private Button btnFechaNacimiento;
    private EditText et_fechaNacimiento;
    private static final int DATE_ID = 0;
    private int nYearIni, nMonthIni, nDayIni, sYearIni, sMonthIni, sDayIni;
    private Calendar calendar = Calendar.getInstance();
    private Spinner spinner;

    private Button btnRegist;
    private EditText txtName;
    private RadioGroup radioGroup;
    private EditText txtAddress;
    private EditText txtAllergies;
    private TextView txtEPC;

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

        et_fechaNacimiento = (EditText)findViewById(R.id.fechaNacimiento);
        btnFechaNacimiento = (Button)findViewById(R.id.btnFechaNacimiento);

        radioGroup = (RadioGroup) findViewById(R.id.radioGroupRegistedPet);
        txtName = (EditText) findViewById(R.id.txtRegistedPetNombre);
        txtAddress = (EditText) findViewById(R.id.txtRegistedPetDireccion);
        txtAllergies = (EditText) findViewById(R.id.txtRegistedPetAlergies);
        txtEPC = (TextView) findViewById(R.id.txtRegistedPetEPC);
        btnRegist = (Button) findViewById(R.id.btnAddRegistedPet);

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
                RegisterNewPet();
            }
        });
    }

    private void RegisterNewPet(){
        // Obtenemos el ID de la opción seleccionada
        int selectedId = radioGroup.getCheckedRadioButtonId();
        // Obtenemos el botón
        RadioButton radioButton = (RadioButton) findViewById(selectedId);
        // Extraemos la información de los inputs
        String name = txtName.getText().toString();
        String sex = radioButton.getText().toString();
        String species = spinner.getSelectedItem().toString();
        String birthdate = et_fechaNacimiento.getText().toString();
        String address = txtAddress.getText().toString();
        String allergies = txtAllergies.getText().toString();
        String epc = txtEPC.getText().toString();
        // Creamos el objeto mascota y lo guardamos en el archivo de texto
        Pet pet = new Pet(name,sex,birthdate,address,allergies,species,epc);
        if(DataHelper.VerificarMascotaValida(RegisterScannedPetFragmentActivity.this,pet)){
            IOHelper.AddPet(RegisterScannedPetFragmentActivity.this,pet);
            RedirectToPetList();
        }
    }

    private void RedirectToPetList(){
        Intent intent = new Intent(RegisterScannedPetFragmentActivity.this, PetListFragmentActivity.class);
        startActivity(intent);
        finish();
    }

    private void LoadExtraEPC(){
        Bundle extras = getIntent().getExtras();
        String txtExtra = extras.getString("EPC");
        txtEPC.setText(txtExtra);
    }


}
