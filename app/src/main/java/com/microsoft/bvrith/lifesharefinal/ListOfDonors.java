package com.microsoft.bvrith.lifesharefinal;

import android.Manifest;
import android.app.NotificationManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.app.NotificationCompat;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import net.sourceforge.jtds.jdbc.DateTime;

import java.sql.ResultSet;
import java.sql.Statement;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;

import static com.microsoft.bvrith.lifesharefinal.Login.MyPREFERENCES;
import static com.microsoft.bvrith.lifesharefinal.R.id.activity_list_of_donors;
import static com.microsoft.bvrith.lifesharefinal.R.id.listView1;

public class ListOfDonors extends AppCompatActivity {
    SharedPreferences sharedpreferences;

    DatabaseConnection databaseConnection;
    CustomAdapter ListAdapter ;
    ArrayList<String> NAME_ArrayList = new ArrayList<String>();
    ArrayList<String> PHONE_NUMBER_ArrayList = new ArrayList<String>();
    ListView LISTVIEW;
    String bg;
    Button reqblood;
    String ph;
    String DonorNumber, UserName, UserContact;

    String todaydate= DateFormat.getDateTimeInstance().format(new Date());

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_of_donors);
        LISTVIEW = (ListView) findViewById(listView1);
        bg = getIntent().getExtras().getString("selectBG");
        reqblood = (Button)findViewById(R.id.reqblood);
        reqblood.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(ListOfDonors.this);
                mBuilder.setSmallIcon(R.drawable.lslogo);
                mBuilder.setContentTitle("Need for Blood!!");
                sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
                ph = sharedpreferences.getString("ContactDetails", "");
                try {
                    java.sql.Connection con = DatabaseConnection.CONN();
                    String query = "select d.ContactDetails, g.ContactDetails, g.UserName from Donor d, Guest g where d.bloodgroup = '"+bg+"' and g.ContactDetails = '"+ph+"'";
                    Statement stmt = con.createStatement();
                    ResultSet rs = stmt.executeQuery(query);
                    while(rs.next()){
                        UserName = rs.getString(3);
                        DonorNumber = rs.getString(1);
                        UserContact = rs.getString(2);
                    }
                }catch(Exception ex){
                    Log.e("ERROR", ex.getMessage());
                }

                try {
                    java.sql.Connection con = DatabaseConnection.CONN();
                    String query = "Insert into Request(BloodGroup, DateOfRequest,ContactDetails) values('"+bg+"', '"+todaydate+"', '"+UserContact+"')";
                    Statement stmt = con.createStatement();
                    int result = stmt.executeUpdate(query);
                    if(result > 0){
                        Toast.makeText(ListOfDonors.this, "Request Sent Across.", Toast.LENGTH_SHORT).show();
                    }
                }catch(Exception ex){
                    Log.e("ERROR", ex.getMessage());
                }



                mBuilder.setContentText("I, '"+UserName+"', request you to donate blood. My phone number is '"+UserContact+"'");
                NotificationManager mNotificationManager = (NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);

// notificationID allows you to update the notification later on.
                mNotificationManager.notify(0, mBuilder.build());
            }

        });
        }


    @Override
    protected void onResume() {

        GetDonorsData() ;

        super.onResume();
    }
    private void GetDonorsData() {
        try {
            //databaseConnection = SQLITEHELPER.getWritableDatabase();
            java.sql.Connection con = DatabaseConnection.CONN();
            String query = "select * from Donor d, Guest g where g.ContactDetails = d.ContactDetails and d.BloodGroup = '" + bg + "'";
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            NAME_ArrayList.clear();
            PHONE_NUMBER_ArrayList.clear();
            if(!(rs.next())){
                AlertDialog.Builder dialog = new AlertDialog.Builder(ListOfDonors.this);
                dialog.setCancelable(false);
                dialog.setTitle("Alert");
                dialog.setMessage("No donors available, Search in blood banks" );
            }

            while (rs.next()) {

                    NAME_ArrayList.add(rs.getString("UserName"));
                    PHONE_NUMBER_ArrayList.add(rs.getString("ContactDetails"));

            }

            ListAdapter = new CustomAdapter(ListOfDonors.this,

                    NAME_ArrayList,
                    PHONE_NUMBER_ArrayList

            );

            LISTVIEW.setAdapter(ListAdapter);


        }catch (Exception ex){
            Log.e("ERROR", ex.getMessage());
        }
    }
}
