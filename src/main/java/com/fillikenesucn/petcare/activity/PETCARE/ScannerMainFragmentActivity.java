package com.fillikenesucn.petcare.activity.PETCARE;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Handler;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.fillikenesucn.petcare.R;
import com.fillikenesucn.petcare.activity.BaseTabFragmentActivity;
import com.fillikenesucn.petcare.activity.PETCARE.utils.IOHelper;
import com.rscja.deviceapi.RFIDWithUHF;

import java.util.Random;

/**
 * Esta clase representa a la actividad que se encargará de escanear los tags rfids asociadas a la mascotas
 * @author: Marcelo Lazo Chavez
 * @version: 26/03/2020
 */
public class ScannerMainFragmentActivity extends FragmentActivity {

    // VARIABLES
    private ImageView imageView;
    // DECLARAMOS e instanciamos una variable que actuará como un indice para simular la animaciones del lector
    private int countState = 4;
    private Button btnLoadInfo;
    // Declaramos el objeto perteneciente al lector UHF de RFID (LA PISTOLA)
    public RFIDWithUHF mReader;
    // Es el tipo de scannear si para el menu principal, para acontecimiento o para añadir pet
    private String statusScannaer;

    /**
     * CONSTRUCTOR DE LA ACTIVIDAD
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scanner_main_fragment);
        imageView = (ImageView) findViewById(R.id.imgScanner);
        btnLoadInfo = (Button) findViewById(R.id.btnLoadInfo);
        // EJECUTAMOS LA ANIMACION DEL SCANNER
        ExecuteTask();
        // OBTENEMOS EL ESTADO ASOCIADO A LA ACTIVIDAD QUE LLAMO AL ESCANER
        Intent parentIntent = getIntent();
        statusScannaer = parentIntent.getStringExtra("STATUS");
        // INICIALIZA EL UHF DE LA PISTOLA
        InitUHF();
        // DEV-BUTTON
        btnLoadInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Random random = new Random();
                switch (statusScannaer){
                    case "MAIN-MENU":
                        LoadPetInfoActivity("TAG-DE-PRUEBA-" + random.nextInt(255 - 0 + 1));
                        break;
                    case "PET-ADD":
                    case "PET-MODIFY":
                    case "EVENT-ADD":
                        ReturnEPCParent("TAG-DE-PRUEBA-" + random.nextInt(255 - 0 + 1));
                        break;
                    default:
                        break;
                }
            }
        });
    }

    /**
     * Método que se encarga de enviar la información asociada al EPC que se acaba de leer de un tag
     * @param txtEPC Es el valor asociado al epc del tag escaneado
     */
    private void LoadPetInfoActivity(String txtEPC){
        // VERIFICA SI EXISTE
        int resultEPC = IOHelper.CheckActiveEPC(ScannerMainFragmentActivity.this, txtEPC);
        //SI EXISTE
        if (resultEPC != -1){
            Intent intent = new Intent(ScannerMainFragmentActivity.this, PetInfoFragmentActivity.class);
            intent.putExtra("EPC",txtEPC);
            startActivity(intent);
            finish();
        }else{ //SI NO EXISTE
            Load404Error();
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
     * Método que se encarga de redireccionar a la vista o actividad de 404 error al falla la lectura de un tag
     */
    private void Load404Error(){
        Intent intent = new Intent(ScannerMainFragmentActivity.this, PetNotFoundFragmentActivity.class);
        startActivity(intent);
        finish();
    }

    /**
     * Método que se encarga de inicializar el lector de UHF asociada al hardware de la pistola
     */
    private void InitUHF(){
        try {
            mReader = RFIDWithUHF.getInstance();
        } catch (Exception ex) {
            Toast.makeText( ScannerMainFragmentActivity.this,ex.getMessage(), Toast.LENGTH_SHORT).show();
            return;
        }
        if (mReader != null){
            new InitTask().execute();
        }
    }

    /**
     * Metodo que se ejecuta cuando se termina la actividad del escanner, liberando la memoria asociada
     * al lector RFID del celular, si es que este fue inicializado en algun momento
     */
    @Override
    protected void onDestroy() {
        if (mReader != null) {
            mReader.free();
        }
        super.onDestroy();
    }

    /**
     * Método que se encarga de intercalando entre las imagenes estaticas de los resources de la aplicación
     * Teniendo un total de secuencia ... -> 4 -> 3 -> 2 -> 1 -> 4 -> 3 -> 2 -> .....
     * Va reducienciendo el estado de la imagen entre un rango de 1 a 4 (inclusivos)
     */
    private void UpdateImageStatus(){
        countState--;
        if( countState < 1){
            countState = 4;
        }
        switch (countState){
            case 4:
                imageView.setImageDrawable(getResources().getDrawable(R.drawable.scan4));
                break;
            case 3:
                imageView.setImageDrawable(getResources().getDrawable(R.drawable.scan3));
                break;
            case 2:
                imageView.setImageDrawable(getResources().getDrawable(R.drawable.scan2));
                break;
            case 1:
                imageView.setImageDrawable(getResources().getDrawable(R.drawable.scan1));
                break;
            default:
                break;
        }
    }

    /**
     * Método que se encarga de ejecutar la animación del escanner de la actividad
     */
    private void ExecuteTask(){
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                UpdateImageStatus();
                ExecuteTask();
            }
        },250);
    }

    /**
     * Clase se encarga de ejecutar una tarea de forma paralela que se encarga de inicializar
     * el lector de RFID, desplegando un dialog para informale al usuario de que esta incializando el lector
     */
    public class InitTask extends AsyncTask<String, Integer, Boolean> {
        // VARIABLES
        ProgressDialog mypDialog;

        /**
         * Método que se encarga de mostrar el dialog de progreso de la inicialización del lector uhf
         */
        @Override
        protected void onPreExecute() {
            // TODO Auto-generated method stub
            super.onPreExecute();

            mypDialog = new ProgressDialog(ScannerMainFragmentActivity.this);
            mypDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            mypDialog.setMessage("Iniciando Lector UHF");
            mypDialog.setCanceledOnTouchOutside(false);
            mypDialog.show();
        }

        /**
         * Método que se encarga de ejecutar la funcionalidad de inicialización del lector UHF
         * @param params Conjuntos de parametros asociados al tarea paralela
         * @return retorna true si la inicialización fue realizada con exito, en caso contrario retorna false
         */
        @Override
        protected Boolean doInBackground(String... params) {
            // TODO Auto-generated method stub
            return mReader.init();
        }

        /**
         * Método que se encarga de finalizar el proceso de la tarea paralela de inicializar el lector UHF
         * @param result Es un valor booleando que indica si la inicialización del lector fue completada con exito
         */
        @Override
        protected void onPostExecute(Boolean result) {
            super.onPostExecute(result);
            // CIERRA EL DIALOG
            mypDialog.cancel();

            if (!result) {
                Toast.makeText(ScannerMainFragmentActivity.this, "FALLO AL INICIAR EL UHF",
                        Toast.LENGTH_SHORT).show();
            }else{
                String strUII = mReader.inventorySingleTag();
                if(!TextUtils.isEmpty(strUII)){
                    String strEPC = mReader.convertUiiToEPC(strUII);
                    // SEGUN EL TIPO DE ACTIVIDAD QUE LLAMO AL ESCANNER LA FUNCIONALIDAD SOBRE EL EPC SERA DIFERENTE
                    switch (statusScannaer){
                        case "MAIN-MENU":
                            LoadPetInfoActivity(strEPC);
                            break;
                        case "PET-ADD":
                        case "PET-MODIFY":
                        case "EVENT-ADD":
                            ReturnEPCParent(strEPC);
                            break;
                        default:
                            break;
                    }
                } else {
                    //NO CARGO TAG
                    Toast.makeText(ScannerMainFragmentActivity.this, "NO HAY TAG CERCA",
                            Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

}
