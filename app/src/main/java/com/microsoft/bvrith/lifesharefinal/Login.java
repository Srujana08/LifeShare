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

public class Login extends AppCompatActivity {
    DatabaseConnection databaseConnection;
    EditText phone, password;
    Button loginok;
    SharedPreferences sharedpreferences;
    public static final String MyPREFERENCES = "MyPrefs" ;
    Boolean check = false;
    //public static final String ContactDetails = "ContactDetails";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        databaseConnection = new DatabaseConnection();
        phone = (EditText)findViewById(R.id.phone);
        password = (EditText)findViewById(R.id.password);
        loginok = (Button)findViewById(R.id.loginok);
        sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        loginok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                    Login.DoLogin doLogin = new Login.DoLogin();
                    doLogin.execute("");


            }
        });
    }

    public class DoLogin extends AsyncTask<String,String,String>
    {
        String z = "";
        String role, cd;
        Boolean isSuccess = false;
        String str_phone = phone.getText().toString();
        String str_password = password.getText().toString();



        @Override
        protected void onPreExecute() {

        }

        @Override
        protected void onPostExecute(String r) {

            Toast.makeText(Login.this,r,Toast.LENGTH_SHORT).show();

            if(isSuccess) {
                            Intent i = new Intent("com.microsoft.bvrith.lifesharefinal.UserMenu");
                            startActivity(i);
                            finish();
                        }



            }



        @Override
        protected String doInBackground(String... params) {
            if(str_password.trim().equals("") ||str_phone.trim().equals("")){
                z = "Please fill all the fields";
            }else{
                try {
                    java.sql.Connection con = DatabaseConnection.CONN();
                    if (con == null) {
                        z = "Error in connection with SQL server";
                    }else{
                        String query = "select * from dbo.Guest where Password = '"+str_password+"' and ContactDetails = '"+str_phone+"'";
                        Statement stmt = con.createStatement();
                        ResultSet rs = stmt.executeQuery(query);
                        if(rs.next()){
                            cd = rs.getString("ContactDetails");
                            role = rs.getString("Role");
                            SharedPreferences.Editor editor = sharedpreferences.edit();
                            editor.putString("ContactDetails", cd);
                            editor.putString("Role", role);
                            editor.commit();
                            z = "Login Successful";
                            isSuccess = true;
                        }else{
                            z = "Invalid Credentials";
                            isSuccess = false;
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
