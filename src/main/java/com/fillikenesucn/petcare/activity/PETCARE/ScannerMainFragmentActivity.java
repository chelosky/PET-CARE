package com.fillikenesucn.petcare.activity.PETCARE;

import android.os.Handler;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.fillikenesucn.petcare.R;

public class ScannerMainFragmentActivity extends FragmentActivity {
    private ImageView imageView;
    private int countState = 4; //0,1,2
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scanner_main_fragment);
        imageView = (ImageView) findViewById(R.id.imgScanner);
        ExecuteTask();
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
        },500);
    }
}
