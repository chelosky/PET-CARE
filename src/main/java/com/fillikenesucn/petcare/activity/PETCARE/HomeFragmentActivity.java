package com.fillikenesucn.petcare.activity.PETCARE;

import android.app.ActionBar;
import android.content.Intent;
import android.os.Handler;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;

import com.fillikenesucn.petcare.R;

public class HomeFragmentActivity extends FragmentActivity {
    private static int SPLASH_TIME_OUT = 2000;
    protected ActionBar mActionBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_fragment);
        mActionBar = getActionBar();
        mActionBar.hide();
        mActionBar.setDisplayShowTitleEnabled(false);
        mActionBar.setDisplayShowHomeEnabled(false);
        mActionBar.setDisplayHomeAsUpEnabled(false);
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
