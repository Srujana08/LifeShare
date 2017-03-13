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
import android.widget.EditText;
import android.widget.Toast;

import java.sql.ResultSet;
import java.sql.Statement;

public class SignUp extends AppCompatActivity {
    SharedPreferences sharedpreferences;
    public static final String MyPREFERENCES = "MyPrefs" ;
    DatabaseConnection databaseConnection;
    //DatabaseHelper myDB;
    EditText name, phone, address, password;
    Button signupok;
    int iagree;
    Boolean check = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        iagree = Integer.parseInt(sharedpreferences.getString("iagree", ""));
        databaseConnection = new DatabaseConnection();
        //myDB = new DatabaseHelper(this);
        name = (EditText)findViewById(R.id.name);
        phone = (EditText)findViewById(R.id.phone);
        address = (EditText)findViewById(R.id.address);
        password = (EditText)findViewById(R.id.password);
        signupok = (Button)findViewById(R.id.signupok);
        signupok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String regexStr = "^[+]?[0-9]{10}$";

                if(phone.getText().toString().length()<10 || phone.getText().toString().matches(regexStr)==false  ) {
                    check = false;
                }else check = true;
                if(check) {
                    DoSignup doSignup = new DoSignup();
                    doSignup.execute("");
                }else {
                    Toast.makeText(SignUp.this, "Enter valid credentials", Toast.LENGTH_SHORT).show();

                }

            }
        });

    }
    public class DoSignup extends AsyncTask<String,Void,String>
    {
        String z = "";
        Boolean isSuccess = false;


        String str_name = name.getText().toString();
        String str_phone = phone.getText().toString();
        String str_address = address.getText().toString();
        String str_password = password.getText().toString();



        @Override
        protected void onPreExecute() {

        }

        @Override
        protected void onPostExecute(String r) {

            Toast.makeText(SignUp.this,r,Toast.LENGTH_SHORT).show();

            if(isSuccess) {
                Intent i = new Intent("com.microsoft.bvrith.lifesharefinal.Login");
                startActivity(i);
                finish();
            }

        }

        @Override
        protected String doInBackground(String... params) {
            if(str_name.trim().equals("")|| str_password.trim().equals("") || str_address.trim().equals("") || str_phone.trim().equals("")){
                z = "Please fill all the fields";
            }else{
                try {
                    java.sql.Connection con = DatabaseConnection.CONN();
                    if (con == null) {
                        z = "Error in connection with SQL server";
                    }else{
                        String query = "select * from dbo.Guest where UserName = '"+str_name+"' and ContactDetails = '"+str_phone+"'";
                        Statement stmt = con.createStatement();
                        ResultSet rs = stmt.executeQuery(query);

                        if(rs.next())
                        {
                            z = "User Already Exists";
                            isSuccess = false;

                        }
                        else
                        {

                            int flag = stmt.executeUpdate("insert into dbo.Guest(UserName, Password, Address, ContactDetails, setConsent) values('"+str_name+"','"+str_password+"','"+str_address+"','"+str_phone+"','"+iagree+"');");
                            z = "SignUp successfull";
                            isSuccess=true;
                        }
                    }
                }catch (Exception ex)
                {
                    isSuccess = false;
                    z = "Exceptions";
                    Log.e("ERROR", ex.getMessage());

                }
            }
            return z;
        }
    }
}
