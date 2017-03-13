package com.microsoft.bvrith.lifesharefinal;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import java.sql.ResultSet;
import java.sql.Statement;

import static com.microsoft.bvrith.lifesharefinal.R.id.findd;
import static com.microsoft.bvrith.lifesharefinal.R.id.radio;

public class FindBlood extends AppCompatActivity {
    DatabaseConnection databaseConnection;
    RadioButton ap, an, bp, bn, abp, abn, op, on;
    RadioGroup bloodgroup;
    int selectedValueId;
    String selectBG;
    Button findd, findbb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_blood1);
        databaseConnection = new DatabaseConnection();
        bloodgroup = (RadioGroup)findViewById(R.id.bloodgroup);
        ap = (RadioButton) findViewById(R.id.ap);
        an = (RadioButton) findViewById(R.id.an);
        bp = (RadioButton) findViewById(R.id.bp);
        bn = (RadioButton) findViewById(R.id.bn);
        abp = (RadioButton) findViewById(R.id.abp);
        abn = (RadioButton) findViewById(R.id.abn);
        op = (RadioButton) findViewById(R.id.op);
        on = (RadioButton) findViewById(R.id.on);
        findbb = (Button)findViewById(R.id.findbb);
        findd = (Button)findViewById(R.id.findd);
        findd.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                int radioButtonID = bloodgroup.getCheckedRadioButtonId();
                if(radioButtonID == -1) {
                    Toast.makeText(FindBlood.this, "Please select a blood group.", Toast.LENGTH_SHORT).show();
                    return;
                }
                RadioButton radioButton = (RadioButton) bloodgroup.findViewById(radioButtonID);
                CharSequence select = radioButton.getText();
                Intent i = new Intent("com.microsoft.bvrith.lifesharefinal.ListOfDonors");
                Bundle bundle = new Bundle();
                bundle.putString("selectBG", select.toString());
                i.putExtras(bundle);
                startActivity(i);
            }
        });
        findbb.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent i = new Intent("com.microsoft.bvrith.lifesharefinal.ListOfBloodbanksUMenu");
                //Bundle bundle = new Bundle();
                //bundle.putString("selectBG", select.toString());
                //i.putExtras(bundle);
                startActivity(i);
            }
        });


    }
}
