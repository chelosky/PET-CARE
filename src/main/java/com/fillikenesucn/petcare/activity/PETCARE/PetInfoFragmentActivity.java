package com.fillikenesucn.petcare.activity.PETCARE;

import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.fillikenesucn.petcare.R;

public class PetInfoFragmentActivity extends FragmentActivity {
    private TextView txtEPC;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pet_info_fragment);
        txtEPC = (TextView) findViewById(R.id.txtEPC);
        LoadInfoPet();
    }

    private void LoadInfoPet(){
        Bundle extras = getIntent().getExtras();
        String txtExtra = extras.getString("EPC");
        txtEPC.setText(txtExtra);
    }
}
