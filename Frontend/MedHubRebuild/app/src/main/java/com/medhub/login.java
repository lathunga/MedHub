package com.medhub;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

public class login extends AppCompatActivity {
    Button register,login;
    String uname,upass,accountType="";
    EditText username,password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        register = (Button) findViewById(R.id.registerButton);
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(login.this, register.class);
                startActivity(i);
            }
        });

       login = (Button) findViewById(R.id.loginButton);
                login.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        username = (EditText) findViewById(R.id.usernameInput);
                        password = (EditText) findViewById(R.id.passwordInput);
                        uname = username.getText().toString();
                        upass = password.getText().toString();

                        makeStringReq(uname);
                        GlobalVariables gb = new GlobalVariables();
                        gb.setInstance(gb);
                        gb.setValue(uname);

                switch (accountType) {
                    case "patient":
                        Intent i = new Intent(login.this, patient_home.class);
                        i.putExtra("username", uname);
                        startActivity(i);
                        break;
                    case "admin":
                        Intent i2 = new Intent(login.this, admin_home.class);
                        startActivity(i2);
                        break;
                    case "doctor":
                        Intent i3 = new Intent(login.this, doctor_home.class);
                        startActivity(i3);
                        break;
                }
            }
        });
    }

    private void makeStringReq(String unameInput) {
        String url = "http://coms-309-sk-3.cs.iastate.edu:8080/Users/get/" + unameInput + "/accountType";
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        accountType = response;
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.i("VolleyError", error.toString());
            }
        });
        Singleton.getInstance(login.this).addToRequestQueue(stringRequest);
    }
}
