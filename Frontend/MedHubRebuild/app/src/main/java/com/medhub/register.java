package com.medhub;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class register extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    private Spinner spinner;
    private static final String[] paths = {"Patient", "Doctor", "Admin"};
    String id,uname,upass,accountType,serverURL;
    EditText username,password;
    Button createAnAccount;
    Button login;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register);
        serverURL = "http://coms-309-sk-3.cs.iastate.edu:8080/Users/";

        spinner = (Spinner)findViewById(R.id.spinner);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(register.this,
                android.R.layout.simple_spinner_item,paths);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener((AdapterView.OnItemSelectedListener) this);

        createAnAccount = (Button) findViewById(R.id.createAnAccountButton);
        createAnAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                username = (EditText) findViewById(R.id.usernameInput);
                password = (EditText) findViewById(R.id.passwordInput);
                uname = username.getText().toString();
                upass = password.getText().toString();
                id = Integer.toString((new Random()).nextInt(1000));

                StringRequest stringRequest = new StringRequest(Request.Method.POST, serverURL,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                            }
                        }
                        , new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.i("VolleyError", error.toString());
                    }
                }){
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        Map<String,String> params = new HashMap<String, String>();
                        params.put("id", id);
                        params.put("username", uname);
                        params.put("password", upass);
                        params.put("accountType", accountType);
                        return params;
                    }
                };

                Singleton.getInstance(register.this).addToRequestQueue(stringRequest);

                Intent i = new Intent(register.this, login.class);
                startActivity(i);
            }
        });

        login = (Button) findViewById(R.id.loginButton);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(register.this, login.class);
                startActivity(i);
            }
        });
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View v, int position, long id) {
        accountType = paths[position].toLowerCase();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        accountType = paths[0].toLowerCase();
    }
}
