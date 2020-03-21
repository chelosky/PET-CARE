package com.fillikenesucn.petcare.activity.PETCARE;

import android.os.Handler;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.fillikenesucn.petcare.R;

public class ScannerMainFragmentActivity extends FragmentActivity {
    private TextView txtStatus;
    private String dotValue = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scanner_main_fragment);
        txtStatus = (TextView) findViewById(R.id.txtStatus);
        ExecuteTask();
    }

    private void UpdateDotStatus(){
        dotValue = dotValue + ".";
        if (dotValue.length() > 3) {
            dotValue = "";
        }
    }

    private void ExecuteTask(){
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                UpdateDotStatus();
                txtStatus.setText("Escaneando" + dotValue);
                ExecuteTask();
            }
        },500);
    }
}
