package com.gasmanager.viacheslav.gasmanager;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

public class HowtoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_howto);
        getSupportActionBar().hide();
    }
}
