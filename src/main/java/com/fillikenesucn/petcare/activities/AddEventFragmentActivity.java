package com.fillikenesucn.petcare.activities;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.fillikenesucn.petcare.R;
import com.fillikenesucn.petcare.models.Acontecimiento;
import com.fillikenesucn.petcare.adapters.DataHelper;
import com.fillikenesucn.petcare.utils.IOHelper;

import java.util.Calendar;

public class AddEventFragmentActivity extends FragmentActivity {
    private final int RESULT_SCANNER_EVENT_ADD = 541;

    private Button btnScanner;
    private TextView txtTag;

    private Button btnFechaNacimiento;
    private EditText et_fechaNacimiento;
    private static final int DATE_ID = 0;
    private int nYearIni, nMonthIni, nDayIni, sYearIni, sMonthIni, sDayIni;
    private Calendar calendar = Calendar.getInstance();

    private Button btnAgregar;
    private EditText txtTitulo;
    private EditText txtDescripcion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_event_fragment);

        txtTitulo = (EditText) findViewById(R.id.txtTitulo);
        txtDescripcion = (EditText) findViewById(R.id.txtDescripcion);
        btnFechaNacimiento = (Button) findViewById(R.id.btnFechaNacimiento);
        this.sMonthIni = this.calendar.get(Calendar.MONTH);
        this.sYearIni = this.calendar.get(Calendar.YEAR);
        this.sDayIni = this.calendar.get(Calendar.DAY_OF_MONTH);
        et_fechaNacimiento = (EditText)findViewById(R.id.fechaNacimiento);
        btnFechaNacimiento = (Button)findViewById(R.id.btnFechaNacimiento);
        btnFechaNacimiento.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog dpd = new DatePickerDialog(AddEventFragmentActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                        et_fechaNacimiento.setText(i2 + "/" + (i1+1) + "/" + i);

                    }
                }, sYearIni, sMonthIni, sDayIni);
                dpd.show();
            }
        });

        txtTag = (TextView)findViewById(R.id.txtTAG);
        txtTag.setText(DataHelper.GetDefaultTagRFID());
        btnScanner = (Button)findViewById(R.id.btnScanTag);
        btnScanner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AddEventFragmentActivity.this, ScannerMainFragmentActivity.class);
                intent.putExtra("STATUS","EVENT-ADD");
                startActivityForResult(intent,RESULT_SCANNER_EVENT_ADD);
            }
        });

        btnAgregar = (Button)findViewById(R.id.btnAgregarEvento);
        btnAgregar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String titulo = txtTitulo.getText().toString();
                String fecha = et_fechaNacimiento.getText().toString();
                String descripcion = txtDescripcion.getText().toString();
                String epc = txtTag.getText().toString();

                Acontecimiento acontecimiento = new Acontecimiento(titulo,fecha,descripcion);
                if(DataHelper.VerificarAcontecimientoValido(AddEventFragmentActivity.this, acontecimiento, epc)){
                    AddEvent(acontecimiento, epc);
                }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RESULT_SCANNER_EVENT_ADD){
            if(resultCode == RESULT_OK){
                String txtEPC = data.getStringExtra("result");
                txtTag.setText(txtEPC);
            }
            if(resultCode == RESULT_CANCELED){
                Toast.makeText(this, "ERROR AL OBTENER TAG", Toast.LENGTH_SHORT).show();
            }   
        }
    }

    private void AddEvent(Acontecimiento acontecimiento, String tag){ IOHelper.AddEvent(this,acontecimiento, tag); }

}
