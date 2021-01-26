package com.example.medhub;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import android.os.Bundle;
import android.widget.TextView;
import android.widget.Button;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

@SuppressWarnings("ALL")
public class PatientMain extends AppCompatActivity {
    Button usernameButton;
    Button passwordButton;
    Button accountTypeButton;

    /*
    3 buttons each do something, like go to a different page. Also helps if the app
    already starts loading on this screen to retrace pathing.
    */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_main);

        usernameButton = (Button) findViewById(R.id.button4);
        usernameButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                makeStringReq("username");
            }
        });

        passwordButton = (Button) findViewById(R.id.button2);
        passwordButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                makeStringReq("password");
            }
        });

        accountTypeButton = (Button) findViewById(R.id.button3);
        accountTypeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                makeStringReq("accountType");
            }
        });
    }

    private void makeStringReq(String requestType) {
        RequestQueue queue = Volley.newRequestQueue(this);
        String url = "http://coms-309-sk-3.cs.iastate.edu:8080/Users/" + 466 + "/" + requestType;
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Toast.makeText(getApplicationContext(), response, Toast.LENGTH_LONG).show();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.i("VolleyError", "That didn't work!");
            }
        });

        queue.add(stringRequest);
    }
}



























// Text that was previously inside the onClick method, moved for demo 2 work.

/*
                if (getIntent().hasExtra("edu.iastate.medhublogin.patientinfo")) {
                    // Fix R.ID buttons.
                    TextView patienttext = (TextView) findViewById(R.id.patientTextView);

                    // Variable "text" going to get the string associated with that intent key (HELLO WORLD!)
                    String text = getIntent().getExtras().getString("edu.iastate.medhublogin.patientinfo");

                    // Set text that is received from the bundle of information to TextView button.
                    patienttext.setText(text);
                }
 */
