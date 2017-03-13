package com.microsoft.bvrith.lifesharefinal;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.sql.Statement;

public class UserMenu extends AppCompatActivity {
    SharedPreferences sharedpreferences;
    public static final String MyPREFERENCES = "MyPrefs" ;
    Button becomeDonor;
    String role;
    String ph;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_menu);
        sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        role = sharedpreferences.getString("Role", "");
        ph = sharedpreferences.getString("ContactDetails", "");
        becomeDonor = (Button)findViewById(R.id.becomedonor);
        if(role.equals("Donor")){
            becomeDonor.setEnabled(false);
        }
        if(becomeDonor.isEnabled()){
            becomeDonor.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                   try{
                       java.sql.Connection con = DatabaseConnection.CONN();
                       String query = "update Guest set Role = 'Donor' where ContactDetails = '"+ph+"'";
                       Statement stmt = con.createStatement();
                       int result = stmt.executeUpdate(query);
                       if(result > 0){
                           //becomeDonor.setEnabled(false);
                           //Toast.makeText(UserMenu.this, "You are now a donor!", Toast.LENGTH_SHORT).show();
                           Intent i = new Intent("com.microsoft.bvrith.lifesharefinal.DonorBloodGroup");
                           startActivity(i);
                       }
                   }catch(Exception ex){
                       Log.e("ERROR", ex.getMessage());
                   }

                }
            });
        }


    }
    public void OpenLOBB(View view){
        startActivity(new Intent("com.microsoft.bvrith.lifesharefinal.ListOfBloodbanksUMenu"));
    }
    public void OpenFB(View view){
        startActivity(new Intent("com.microsoft.bvrith.lifesharefinal.FindBlood"));
    }
    public void OpenR(View view) {
        startActivity(new Intent("com.microsoft.bvrith.lifesharefinal.ListOfRequests"));
    }

}
