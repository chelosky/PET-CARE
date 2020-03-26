package com.fillikenesucn.petcare.activity.PETCARE;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

// Testing
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import java.io.FileOutputStream;

import com.fillikenesucn.petcare.R;
import com.fillikenesucn.petcare.activity.PETCARE.models.Pet;
import com.fillikenesucn.petcare.activity.PETCARE.utils.IOHelper;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class RegisterPetFragmentActivity extends FragmentActivity {
    private Button btnFechaNacimiento;
    private EditText et_fechaNacimiento;
    private static final int DATE_ID = 0;
    private int nYearIni, nMonthIni, nDayIni, sYearIni, sMonthIni, sDayIni;
    private Calendar calendar = Calendar.getInstance();
    private Spinner spinner;

    // Testing
    private Button btnRegist;
    private EditText txtName;
    private RadioGroup radioGroup;
    private RadioButton radioButton;
    private EditText txtAddress;
    private EditText txtAllergies;
    private TextView txtTest;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_pet_fragment);
        this.spinner = findViewById(R.id.spinnerRegisterPet);
        final List<String> pets = new ArrayList<>();
        pets.add(0, "Seleccione Especie");
        pets.add("Perro");
        pets.add("Gato");

        ArrayAdapter<String> petsAdapter = new ArrayAdapter(this,android.R.layout.simple_spinner_item,pets);
        petsAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        this.spinner.setAdapter(petsAdapter);

        this.sMonthIni = this.calendar.get(Calendar.MONTH);
        this.sYearIni = this.calendar.get(Calendar.YEAR);
        this.sDayIni = this.calendar.get(Calendar.DAY_OF_MONTH);

        et_fechaNacimiento = (EditText)findViewById(R.id.fechaNacimiento);
        btnFechaNacimiento = (Button)findViewById(R.id.btnFechaNacimiento);
        txtTest = (TextView) findViewById(R.id.txtTest);
        txtName = (EditText) findViewById(R.id.txtName);
        radioGroup = (RadioGroup) findViewById(R.id.radio);
        spinner = findViewById(R.id.spinnerRegisterPet);
        txtAddress = (EditText) findViewById(R.id.txtAddress);
        txtAllergies = (EditText) findViewById(R.id.txtAllergies);

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

        // Testing
        Button tempLeer = (Button)findViewById(R.id.leerjson);
        tempLeer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ReadJsonFile();
                Toast.makeText(RegisterPetFragmentActivity.this, "LISTO2", Toast.LENGTH_SHORT).show();
            }
        });
        btnRegist = (Button) findViewById(R.id.btnRegist);
        btnRegist.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        // get selected radio button from radioGroup
                        int selectedId = radioGroup.getCheckedRadioButtonId();
                        // find the radiobutton by returned id
                        radioButton = (RadioButton) findViewById(selectedId);

                        String name = txtName.getText().toString();
                        String sex = radioButton.getText().toString();
                        String species = spinner.getSelectedItem().toString();
                        String birthdate = et_fechaNacimiento.getText().toString();
                        String address = txtAddress.getText().toString();
                        String allergies = txtAllergies.getText().toString();

                        Pet pet = new Pet(name,sex,birthdate,address,allergies,species);
                        Gson gson = new Gson();
                        WriteJsonFile(gson.toJson(pet));

                        Toast.makeText(RegisterPetFragmentActivity.this, "LISTO1", Toast.LENGTH_SHORT).show();
                    }
                }
        );
    }

    private void ReadJsonFile(){
        txtTest.setText(IOHelper.ReadFileString(this));
    }

    private void WriteJsonFile(String stringvalue){
        IOHelper.WriteJson(this,"petcare.txt",stringvalue);
    }

}
