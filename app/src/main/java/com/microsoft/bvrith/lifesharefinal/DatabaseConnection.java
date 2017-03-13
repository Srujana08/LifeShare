package com.microsoft.bvrith.lifesharefinal;

/**
 * Created by SrujanaParupudi on 3/6/2017.
 */

import android.annotation.SuppressLint;
import android.os.StrictMode;
import android.util.Log;

import java.sql.*;

public class DatabaseConnection {

    @SuppressLint("NewApi")
    public static Connection CONN() {
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
                .permitAll().build();
        StrictMode.setThreadPolicy(policy);
        Connection conn = null;
        String ConnURL = null;
        try {

            //Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            Class.forName("net.sourceforge.jtds.jdbc.Driver");
            conn = DriverManager.getConnection("jdbc:jtds:sqlserver://meher.database.windows.net:1433/LifeShare;user=Sameera@meher;password=Daddy143;encrypt=true;trustServerCertificate=false;hostNameInCertificate=*.database.windows.net;loginTimeout=30;");
        } catch (SQLException se) {
            Log.e("ERRO", se.getMessage());
        } catch (ClassNotFoundException e) {
            Log.e("ERRO", e.getMessage());
        } catch (Exception e) {
            Log.e("ERRO", e.getMessage());
        }
        return conn;
    }
}

// Connect to your database.
// Replace server name, username, and password with your credentials
    /*public static void main(String[] args) {
        String connectionString =
                "jdbc:sqlserver://meher.database.windows.net:1433;database=LifeShare;user=Sameera@meher;password=Daddy143;encrypt=true;trustServerCertificate=false;hostNameInCertificate=*.database.windows.net;loginTimeout=30;";

        // Declare the JDBC objects.
        Connection connection = null;

        try {
            connection = DriverManager.getConnection(connectionString);
            //System.out.print("true");

        }
        catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            if (connection != null) try { connection.close(); } catch(Exception e) {}
        }
    }*/

