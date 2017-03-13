package com.microsoft.bvrith.lifesharefinal;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class HomePage extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);
    }

    public void OpenSignup(View view){
        startActivity(new Intent("com.microsoft.bvrith.lifesharefinal.ConsentForm"));
    }
    public void OpenLOBB(View view){
        startActivity(new Intent("com.microsoft.bvrith.lifesharefinal.ListOfBloodbanksUMenu"));
    }
    public void OpenLogin(View view) {
        startActivity(new Intent("com.microsoft.bvrith.lifesharefinal.Login"));
    }
}
