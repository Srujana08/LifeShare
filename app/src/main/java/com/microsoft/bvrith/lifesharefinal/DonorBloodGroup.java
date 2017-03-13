package com.microsoft.bvrith.lifesharefinal;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import java.sql.ResultSet;
import java.sql.Statement;

public class DonorBloodGroup extends AppCompatActivity {
    SharedPreferences sharedPreferences;
    Button dbgok;
    RadioGroup bloodgroup;
    CharSequence select;

    public static final String MyPREFERENCES = "MyPrefs" ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_donor_blood_group);
        bloodgroup = (RadioGroup) findViewById(R.id.bloodgroup);
        sharedPreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        dbgok = (Button) findViewById(R.id.dbgok);
        dbgok.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                int radioButtonID = bloodgroup.getCheckedRadioButtonId();
                if (radioButtonID == -1) {
                    Toast.makeText(DonorBloodGroup.this, "Please select a blood group.", Toast.LENGTH_SHORT).show();
                    return;
                }
                RadioButton radioButton = (RadioButton) bloodgroup.findViewById(radioButtonID);
                select = radioButton.getText();
                DonorBloodGroup.DoBecomeDonor doBecomeDonor = new DonorBloodGroup.DoBecomeDonor();
                doBecomeDonor.execute("");
            }
        });
    }

    public class DoBecomeDonor extends AsyncTask<String, String, String> {
        Boolean isSuccess = false;
        String selectBG = select.toString();
        String ph = sharedPreferences.getString("ContactDetails", "");


        @Override
        protected void onPreExecute() {

        }

        @Override
        protected void onPostExecute(String r) {


            if (isSuccess) {
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("Role", "Donor");
                editor.commit();
                Toast.makeText(DonorBloodGroup.this, "You are now a donor!", Toast.LENGTH_SHORT).show();

                Intent i = new Intent("com.microsoft.bvrith.lifesharefinal.UserMenu");
                startActivity(i);
                finish();
            }


        }


        @Override
        protected String doInBackground(String... params) {
            try {
                java.sql.Connection con = DatabaseConnection.CONN();
                if (con == null) {
                    Toast.makeText(DonorBloodGroup.this, "Error in SQL connection", Toast.LENGTH_SHORT).show();

                } else {
                    String query = "insert into Donor(BloodGroup, ContactDetails) values( '"+selectBG+"', '"+ph+"') ";
                    String role = "Donor";

                    Statement stmt = con.createStatement();
                    int result = stmt.executeUpdate(query);
                    if(result >= 0){
                        isSuccess = true;
                    }

                }


            } catch (Exception ex) {
                isSuccess = false;
                Log.e("ERROR", ex.getMessage());

            }

            return "";
        }
    }
}

