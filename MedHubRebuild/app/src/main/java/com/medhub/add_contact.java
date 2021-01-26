package com.medhub;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

public class add_contact extends AppCompatActivity {
    Button addContactBtn;
    EditText addContactInput;
    String serverURL,userId,otherUserId,otherUserUsername,username;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_contact);

        addContactBtn = (Button) findViewById(R.id.addContactBtn);
        addContactBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = getIntent();
                addContactInput = (EditText) findViewById(R.id.addContactInput);
                otherUserUsername = addContactInput.getText().toString();
                username = in.getStringExtra("username");
                getUserId(username, true);
                getUserId(otherUserUsername, false);
                serverURL = "http://coms-309-sk-3.cs.iastate.edu:8080/Users/" + userId + "/contact";

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
                        params.put("userId", userId);
                        params.put("otherUserId", otherUserId);
                        return params;
                    }
                };

                Singleton.getInstance(add_contact.this).addToRequestQueue(stringRequest);

                Intent i = new Intent(add_contact.this, contact_list.class);
                startActivity(i);
            }
        });
    }

    public void getUserId(final String username, final boolean myUsername) {
        String url = "http://coms-309-sk-3.cs.iastate.edu:8080/Users/get/" + username + "/id";
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if (myUsername) {
                            userId = response;
                        } else {
                            otherUserId = response;
                        }
                        Log.i("LocalValuesInMethod", userId + " | " + otherUserId);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.i("VolleyError", error.toString());
            }
        });
        Singleton.getInstance(add_contact.this).addToRequestQueue(stringRequest);
    }
}
