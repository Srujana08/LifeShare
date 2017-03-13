package com.microsoft.bvrith.lifesharefinal;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;
import android.widget.Toast;

import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

import static com.microsoft.bvrith.lifesharefinal.Login.MyPREFERENCES;

public class ListOfRequests extends AppCompatActivity {
    SharedPreferences sharedpreferences;
    DatabaseConnection databaseConnection;
    RequestAdapter ListAdapter;
    ArrayList<String> BG_ArrayList = new ArrayList<String>();
    ArrayList<String> RD_ArrayList = new ArrayList<String>();
    ListView LISTVIEW;
    String ph;
    int flag = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_of_requests);
        LISTVIEW = (ListView) findViewById(R.id.listView1);
        sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        ph = sharedpreferences.getString("ContactDetails", "");

        ListOfRequests.DoGetRequests doGetRequests = new ListOfRequests.DoGetRequests();
        doGetRequests.execute("");

    }

    /*protected void onResume() {

        GetRequestsData() ;

        super.onResume();
    }*/


    public class DoGetRequests extends AsyncTask<String, String, String> {

        @Override
        protected void onPreExecute() {

        }
        protected void onPostExecute(String r) {
            Log.i("ListOfRequests","eee");
            LISTVIEW.setAdapter(ListAdapter);
        }
        @Override
        protected String doInBackground(String... params) {
            try {

                java.sql.Connection con = DatabaseConnection.CONN();
                String query = "select * from Request where ContactDetails = '"+ph+"'";
                Statement stmt = con.createStatement();
                ResultSet rs = stmt.executeQuery(query);
                BG_ArrayList.clear();
                RD_ArrayList.clear();
                /*if(!(rs.next())){
                    AlertDialog.Builder dialog = new AlertDialog.Builder(ListOfRequests.this);
                    dialog.setCancelable(false);
                    dialog.setTitle("Alert");
                    dialog.setMessage("Sorry No requests to show" );
                }*/

                while (rs.next()) {
                    flag = 1;
                    BG_ArrayList.add(rs.getString("BloodGroup"));
                    RD_ArrayList.add(rs.getString("DateOfRequest"));
                    Log.i("Query   BloodGroup", rs.getString("BloodGroup"));
                    Log.i("Query   DateOfRequests", rs.getString("DateOfRequest"));
                }
                if(flag == 0){
                    Toast.makeText(ListOfRequests.this, "No requests to show", Toast.LENGTH_SHORT).show();

                }

                ListAdapter = new RequestAdapter(ListOfRequests.this,

                        BG_ArrayList,
                        RD_ArrayList

                );

            }catch (Exception ex){
                Log.e("ERROR", ex.getMessage());
            }
            return "";
        }
    }
}