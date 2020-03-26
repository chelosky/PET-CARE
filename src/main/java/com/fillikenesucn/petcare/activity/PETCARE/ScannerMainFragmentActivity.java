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
import com.rscja.deviceapi.RFIDWithUHF;

public class ScannerMainFragmentActivity extends FragmentActivity {
    private ImageView imageView;
    private int countState = 4; //0,1,2
    private Button btnLoadInfo;
    public RFIDWithUHF mReader;
    private String statusScannaer; //es el tipo de scannear si para el menu principal, para acontecimiento o para añadir pet
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scanner_main_fragment);
        imageView = (ImageView) findViewById(R.id.imgScanner);
        btnLoadInfo = (Button) findViewById(R.id.btnLoadInfo);
        ExecuteTask();
        Intent parentIntent = getIntent();
        statusScannaer = parentIntent.getStringExtra("STATUS");
        InitUHF();
        btnLoadInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switch (statusScannaer){
                    case "MAIN-MENU":
                        LoadPetInfoActivity("TAG-DE-PRUEBA");
                        break;
                    case "PET-ADD":
                        ReturnEPCParent("TAG-DE-PRUEBA");
                        break;
                    case "EVENT-ADD":
                        ReturnEPCParent("TAG-DE-PRUEBA");
                        break;
                    default:
                        break;
                }
            }
        });
    }

    private void LoadPetInfoActivity(String txtEPC){
        Intent intent = new Intent(ScannerMainFragmentActivity.this, PetInfoFragmentActivity.class);
        intent.putExtra("EPC",txtEPC);
        startActivity(intent);
        finish();
    }

    private void ReturnEPCParent(String txtEPC){
        Intent resultIntent = new Intent();
        resultIntent.putExtra("result",txtEPC);
        setResult(RESULT_OK, resultIntent);
        finish();
    }


    private void Load404Error(){
        Intent intent = new Intent(ScannerMainFragmentActivity.this, PetNotFoundFragmentActivity.class);
        startActivity(intent);
        finish();
    }

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

    @Override
    protected void onDestroy() {
        if (mReader != null) {
            mReader.free();
        }
        super.onDestroy();
    }

    // 4 -> 3 -> 2 -> 1
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

    private void ExecuteTask(){
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                UpdateImageStatus();
                ExecuteTask();
            }
        },250);
    }

    public class InitTask extends AsyncTask<String, Integer, Boolean> {
        ProgressDialog mypDialog;
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
        @Override
        protected Boolean doInBackground(String... params) {
            // TODO Auto-generated method stub
            return mReader.init();
        }

        @Override
        protected void onPostExecute(Boolean result) {
            super.onPostExecute(result);

            mypDialog.cancel();

            if (!result) {
                Toast.makeText(ScannerMainFragmentActivity.this, "FALLO AL INICIAR EL UHF",
                        Toast.LENGTH_SHORT).show();
            }else{
                String strUII = mReader.inventorySingleTag();
                if(!TextUtils.isEmpty(strUII)){
                    String strEPC = mReader.convertUiiToEPC(strUII);
                    switch (statusScannaer){
                        case "MAIN-MENU":
                            LoadPetInfoActivity(strEPC);
                            break;
                        case "PET-ADD":
                            ReturnEPCParent(strEPC);
                            break;
                        case "EVENT-ADD":
                            ReturnEPCParent(strEPC);
                            break;
                        default:
                            break;
                    }
                } else {
                    Toast.makeText(ScannerMainFragmentActivity.this, "NO HAY TAG CERCA",
                            Toast.LENGTH_SHORT).show();
                    //Load404Error();
                }
            }
        }
    }

}
