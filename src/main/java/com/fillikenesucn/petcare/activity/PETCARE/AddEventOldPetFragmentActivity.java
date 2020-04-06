package com.fillikenesucn.petcare.activity.PETCARE;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import com.fillikenesucn.petcare.R;
import com.fillikenesucn.petcare.activity.PETCARE.models.Acontecimiento;
import com.fillikenesucn.petcare.activity.PETCARE.utils.IOHelper;

import java.util.Calendar;

public class AddEventOldPetFragmentActivity extends FragmentActivity {
    private Button btnFechaNacimiento;
    private EditText et_fechaNacimiento;
    private static final int DATE_ID = 0;
    private int nYearIni, nMonthIni, nDayIni, sYearIni, sMonthIni, sDayIni;
    private Calendar calendar = Calendar.getInstance();
    private TextView txtEPC;
    private EditText txtTittle;
    private EditText txtDescripcion;
    private Button btnAddAcontecimiento;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_event_old_pet_fragment);

        this.sMonthIni = this.calendar.get(Calendar.MONTH);
        this.sYearIni = this.calendar.get(Calendar.YEAR);
        this.sDayIni = this.calendar.get(Calendar.DAY_OF_MONTH);
        txtEPC = (TextView) findViewById(R.id.txtEPC);
        et_fechaNacimiento = (EditText)findViewById(R.id.fechaAcontecimiento);
        btnFechaNacimiento = (Button)findViewById(R.id.btnFechaNacimiento);
        txtTittle = (EditText) findViewById(R.id.txtTittle);
        txtDescripcion = (EditText) findViewById(R.id.txtDescripcionAcontecimiento);
        btnAddAcontecimiento = (Button) findViewById(R.id.btnAddAcontecimiento);

        LoadPetExtraEpc();
        btnFechaNacimiento.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog dpd = new DatePickerDialog(AddEventOldPetFragmentActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                        et_fechaNacimiento.setText(i2 + "/" + (i1+1) + "/" + i);

                    }
                }, sYearIni, sMonthIni, sDayIni);
                dpd.show();
            }
        });

        btnAddAcontecimiento.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Acontecimiento acontecimiento = new Acontecimiento(txtTittle.getText().toString(), et_fechaNacimiento.getText().toString(),txtDescripcion.getText().toString());
                IOHelper.AddEvent(AddEventOldPetFragmentActivity.this,acontecimiento,txtEPC.getText().toString());
                finish();
            }
        });
    }

    private void LoadPetExtraEpc(){
        Bundle extras = getIntent().getExtras();
        txtEPC.setText(extras.getString("EPC"));
    }
}
