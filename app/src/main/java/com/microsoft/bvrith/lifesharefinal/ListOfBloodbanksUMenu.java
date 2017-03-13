package com.microsoft.bvrith.lifesharefinal;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;

import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

public class ListOfBloodbanksUMenu extends AppCompatActivity {
    DatabaseConnection databaseConnection;
    BloodBankAdapter ListAdapter;
    ArrayList<String> BBName_ArrayList = new ArrayList<String>();
    ArrayList<String> BBAddress_ArrayList = new ArrayList<String>();
    ArrayList<String> BBPhone_ArrayList = new ArrayList<String>();

    ListView LISTVIEW;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_of_bloodbanks_umenu);
        LISTVIEW = (ListView) findViewById(R.id.listView1);
        ListOfBloodbanksUMenu.DoGetListOfBloodbanksUMenu doGetListOfBloodbanksUMenu = new ListOfBloodbanksUMenu.DoGetListOfBloodbanksUMenu();
        doGetListOfBloodbanksUMenu.execute("");
    }

    public class DoGetListOfBloodbanksUMenu extends AsyncTask<String, String, String> {

        @Override
        protected void onPreExecute() {

        }
        protected void onPostExecute(String r) {
            LISTVIEW.setAdapter(ListAdapter);
        }
        @Override
        protected String doInBackground(String... params) {
            try {

                java.sql.Connection con = DatabaseConnection.CONN();
                String query = "select * from BloodBanks";
                Statement stmt = con.createStatement();
                ResultSet rs = stmt.executeQuery(query);
                BBName_ArrayList.clear();
                BBAddress_ArrayList.clear();
                BBPhone_ArrayList.clear();
                /*if(!(rs.next())){
                    AlertDialog.Builder dialog = new AlertDialog.Builder(ListOfRequests.this);
                    dialog.setCancelable(false);
                    dialog.setTitle("Alert");
                    dialog.setMessage("Sorry No requests to show" );
                }*/

                while (rs.next()) {

                    BBName_ArrayList.add(rs.getString("Name"));
                    BBAddress_ArrayList.add(rs.getString("Address"));
                    BBPhone_ArrayList.add(rs.getString("ContactDetails"));
                }

                ListAdapter = new BloodBankAdapter(ListOfBloodbanksUMenu.this,

                        BBName_ArrayList,
                        BBAddress_ArrayList,
                        BBPhone_ArrayList

                );

            }catch (Exception ex){
                Log.e("ERROR", ex.getMessage());
            }
            return "";
        }
    }
}
