package com.example.medhub;

import androidx.appcompat.app.AppCompatActivity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import java.util.Random;
import java.util.HashMap;
import java.util.Map;

@SuppressWarnings("ALL")
public class loginType extends AppCompatActivity {
    //receives the intent keys with certain information packaged from the main activity class
    Button loginBtn;
    EditText ID, username, password, accountType;
    String serverUrl = "http://coms-309-sk-3.cs.iastate.edu:8080/Users";
    AlertDialog.Builder builder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_type);

        loginBtn = (Button) findViewById(R.id.loginBtn);//edit here
        username = (EditText) findViewById(R.id.editText);
        password = (EditText) findViewById(R.id.editText2);
        builder = new AlertDialog.Builder(loginType.this);
        // Purpose of this button is to send the user to the correct screen based on user type.

        loginBtn.setOnClickListener(new View.OnClickListener() {
            /*
            Have to receive intent to check which button the user clicked (the 3 if statements), then
            edit onClick functionality based on which class to send to (.xml layout)
             */

            @Override
            public void onClick(View v) {
                final String accountType;
                if (getIntent().hasExtra("edu.iastate.medhublogin.patientinfo")) {
                    accountType = "patient";
                    Intent startIntent = new Intent(getApplicationContext(), PatientMain.class);
                    startIntent.putExtra("edu.iastate.quicklauncher.patientinfo", "Welcome Patient");
                    startActivity(startIntent);
                }
                //if the intent received from main class has the type of doctor
                //then send and intent to the doctor class with a certain value
                else if (getIntent().hasExtra("edu.iastate.medhublogin.doctorinfo")) {
                    accountType = "doctor";
                    Intent startIntent = new Intent(getApplicationContext(), DoctorMain.class);
                    startIntent.putExtra("edu.iastate.quicklauncher.doctorinfo", "Welcome Doctor");
                    startActivity(startIntent);
                }
                //if the intent received from main class has the type of admin
                //then send and intent to the admin class with a certain value
                else if (getIntent().hasExtra("edu.iastate.medhublogin.admininfo")) {
                    accountType = "admin";
                    Intent startIntent = new Intent(getApplicationContext(), AdminMain.class);
                    startIntent.putExtra("edu.iastate.quicklauncher.admininfo", "Welcome Admin");
                    startActivity(startIntent);
                }
                else
                {
                    accountType = "NA";
                }

                final String name, pass, id;
                Random rand = new Random();
                // Generate random integers in range 0 to 999
                int rand_int1 = rand.nextInt(1000);
                id = Integer.toString(rand_int1);
                name = username.getText().toString();
                pass = password.getText().toString();

                StringRequest stringRequest = new StringRequest(Request.Method.POST, serverUrl,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                            }
                        }
                        , new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(loginType.this, "Error", Toast.LENGTH_SHORT).show();
                        error.printStackTrace();
                    }
                }){
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        Map<String,String> params = new HashMap<String, String>();
                        params.put("id", id);
                        params.put("username", name);
                        params.put("password", pass);
                        params.put("accountType", accountType);
                        return params;
                    }
                };

                Singleton.getInstance(loginType.this).addToRequestQueue(stringRequest);
            }
        });



    }
}
