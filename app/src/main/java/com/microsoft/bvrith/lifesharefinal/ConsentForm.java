package com.microsoft.bvrith.lifesharefinal;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

public class ConsentForm extends AppCompatActivity {
    SharedPreferences sharedpreferences;
    public static final String MyPREFERENCES = "MyPrefs" ;
    TextView consent;
    CheckBox iagree;
    Button agreed;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consent_form);
        consent = (TextView)findViewById(R.id.consent);
        consent.setText("I understand that I have read and would not held responsible and I'm at my own will using this application and would not held anyone responsible.\n" +
                "I hereby declare that any information present in the application will not be misused and any wrongful act the user will be legally prosecuted.");
        iagree = (CheckBox)findViewById(R.id.iagree);
        agreed = (Button)findViewById(R.id.agreed);
        sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        agreed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(iagree.isChecked()){
                    SharedPreferences.Editor editor = sharedpreferences.edit();
                    editor.putString("iagree", "1");
                    editor.commit();
                    startActivity(new Intent("com.microsoft.bvrith.lifesharefinal.SignUp"));
                }

            }
        });
    }
}
