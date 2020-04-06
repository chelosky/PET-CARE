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
import com.fillikenesucn.petcare.activity.PETCARE.utils.DataHelper;
import com.fillikenesucn.petcare.activity.PETCARE.utils.IOHelper;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Calendar;

/**
 * Esta clase representa a la actividad que se encarga de agregar la información de un evento, necesita escanear el EPC
 * @author: Rodrigo Dorat Merejo
 * @version: 06/04/2020
 */
public class AddEventFragmentActivity extends FragmentActivity {
    private final int RESULT_SCANNER_EVENT_ADD = 541;

    // VARIABLES
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

    /**
     * CONSTRUCTOR de la actividad
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_event_fragment);

        this.sMonthIni = this.calendar.get(Calendar.MONTH);
        this.sYearIni = this.calendar.get(Calendar.YEAR);
        this.sDayIni = this.calendar.get(Calendar.DAY_OF_MONTH);

        LoadInputs();

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

        btnScanner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AddEventFragmentActivity.this, ScannerMainFragmentActivity.class);
                intent.putExtra("STATUS","EVENT-ADD");
                startActivityForResult(intent,RESULT_SCANNER_EVENT_ADD);
            }
        });

        btnAgregar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RegisterNewEvent();
            }
        });
    }

    /**
     * Método que registra el evento en la mascota que tenga el EPC
     */
    private void RegisterNewEvent(){
        String titulo = txtTitulo.getText().toString();
        String fecha = et_fechaNacimiento.getText().toString();
        String descripcion = txtDescripcion.getText().toString();
        String epc = txtTag.getText().toString();
        // Guardamos el acontecimientos como un objeto Acontecimiento
        Acontecimiento acontecimiento = new Acontecimiento(titulo,fecha,descripcion);
        // Revisamos que la información sea válida
        if(DataHelper.VerificarAcontecimientoValido(AddEventFragmentActivity.this, acontecimiento, epc)){
            // Si puede agregar el evento cierra la actividad
            if (IOHelper.AddEvent(AddEventFragmentActivity.this,acontecimiento,epc)) {
                Toast.makeText(AddEventFragmentActivity.this, "INGRESO EXITOSO", Toast.LENGTH_SHORT).show();
                finish();
            }
        }
    }

    /**
     * Método que carga los inputs del Layout
     */
    private void LoadInputs(){
        txtTitulo = (EditText) findViewById(R.id.txtTitulo);
        txtDescripcion = (EditText) findViewById(R.id.txtDescripcion);

        btnFechaNacimiento = (Button) findViewById(R.id.btnFechaNacimiento);
        et_fechaNacimiento = (EditText)findViewById(R.id.fechaNacimiento);

        txtTag = (TextView)findViewById(R.id.txtTAG);
        txtTag.setText(DataHelper.GetDefaultTagRFID());

        btnScanner = (Button)findViewById(R.id.btnScanTag);
        btnAgregar = (Button)findViewById(R.id.btnAgregarEvento);
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

}
