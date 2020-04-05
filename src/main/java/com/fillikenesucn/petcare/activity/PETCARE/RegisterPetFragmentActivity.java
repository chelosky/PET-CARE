package com.fillikenesucn.petcare.activity.PETCARE;

import android.app.DatePickerDialog;
import android.content.Context;
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
import com.fillikenesucn.petcare.activity.PETCARE.models.Pet;
import com.fillikenesucn.petcare.activity.PETCARE.utils.DataHelper;
import com.fillikenesucn.petcare.activity.PETCARE.utils.IOHelper;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class RegisterPetFragmentActivity extends FragmentActivity {

    private final int RESULT_SCANNER_PET_ADD = 616;

    private TextView txtTAG;
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

    private Button btnScanTag;

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

        btnScanTag = (Button)findViewById(R.id.btnScanTag);
        txtTAG = (TextView)findViewById(R.id.txtTAG);

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

        btnScanTag.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(RegisterPetFragmentActivity.this, ScannerMainFragmentActivity.class);
                intent.putExtra("STATUS","PET-ADD");
                startActivityForResult(intent,RESULT_SCANNER_PET_ADD);
            }
        });
        // Testing
        Button tempLeer = (Button)findViewById(R.id.leerjson);
        tempLeer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                txtTest.setText(ReadJsonFile());
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
                        String epc = txtTAG.getText().toString();

                        Pet pet = new Pet(name,sex,birthdate,address,allergies,species, epc);
                        Gson gson = new Gson();
                        Boolean status = WriteJsonFile(gson.toJson(pet));

                        if (status) {
                            Toast.makeText(RegisterPetFragmentActivity.this, "INGRESO EXITOSO", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(RegisterPetFragmentActivity.this, "INGRESO FALLIDO", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
        );
    }

    private String ReadJsonFile(){ return IOHelper.ReadFileString(this); }

    private Boolean WriteJsonFile(String stringvalue){
        String tmp = IOHelper.WriteJson(this,"petcare.txt",stringvalue);
        return true;
    }

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
