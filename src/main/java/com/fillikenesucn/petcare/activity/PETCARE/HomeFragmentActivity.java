package com.fillikenesucn.petcare.activity.PETCARE;

import android.app.ActionBar;
import android.content.Intent;
import android.os.Handler;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;

import com.fillikenesucn.petcare.R;

/**
 * Esta clase representa a la actividad que se actuará como splash screen de la aplicación
 * @author: Marcelo Lazo Chavez
 * @version: 16/03/2020
 */
public class HomeFragmentActivity extends FragmentActivity {

    // VARIABLES
    private static int SPLASH_TIME_OUT = 2000;
    protected ActionBar mActionBar;

    /**
     * Constructor de la actividad
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_fragment);
        // SE DESACTIVA LA VISIBLIDAD DEL ACTION BAR DE ESTA ACTIVIDAD
        mActionBar = getActionBar();
        mActionBar.hide();
        mActionBar.setDisplayShowTitleEnabled(false);
        mActionBar.setDisplayShowHomeEnabled(false);
        mActionBar.setDisplayHomeAsUpEnabled(false);
        // SE EJECUTA UNA TAREA PARALELA QUE LUEGO DE 2 SEG INICIA LA ACTIVIDAD DEL MENU PRINCIPAL
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent homeIntent = new Intent(HomeFragmentActivity.this, MainMenuFragmentActivity.class);
                startActivity(homeIntent);
                finish();
            }
        }, SPLASH_TIME_OUT);
    }
}
