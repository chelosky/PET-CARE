package com.fillikenesucn.petcare.activity.PETCARE;

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
import com.fillikenesucn.petcare.activity.PETCARE.models.Acontecimiento;
import com.fillikenesucn.petcare.activity.PETCARE.utils.IOHelper;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

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
                try {
                    String titulo = txtTitulo.getText().toString();
                    String fecha = et_fechaNacimiento.getText().toString();
                    String descripcion = txtDescripcion.getText().toString();
                    String epc = txtTag.getText().toString();

                    JSONObject tag = new JSONObject();
                    tag.put("EPC", epc);
                    Gson gson = new Gson();
                    Acontecimiento acontecimiento = new Acontecimiento(titulo,fecha,descripcion);
                    Boolean status = WriteJsonFile(gson.toJson(acontecimiento), tag);

                    if (status) {
                        Toast.makeText(AddEventFragmentActivity.this, "INGRESO EXITOSO", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(AddEventFragmentActivity.this, "INGRESO FALLIDO", Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
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

    private Boolean WriteJsonFile(String stringvalue, JSONObject tag){ return IOHelper.AddEvent(this,stringvalue, tag); }

}
