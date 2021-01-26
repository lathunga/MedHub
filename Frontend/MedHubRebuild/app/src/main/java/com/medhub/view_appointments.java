package com.medhub;

import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;

public class view_appointments extends AppCompatActivity {
    ListView appointments;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_appointments);

        appointments = (ListView) findViewById(R.id.appointments);

        ArrayList<String> arrayList = new ArrayList<>();

        GlobalVariables gb = new GlobalVariables();
        gb = gb.getInstance();
        String username = gb.getValue();
        Log.d("value", username);

        String url = "http://coms-309-sk-3.cs.iastate.edu:8080/Users/get/" + username + "/id";

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        String url = "http://coms-309-sk-3.cs.iastate.edu:8080/users/gets/" + response + "/appointments";
                        Log.d("value2", response);
                        Toast.makeText(getApplicationContext(), response, Toast.LENGTH_LONG).show();
                        JsonArrayRequest stringRequest2 = new JsonArrayRequest(Request.Method.GET, url, null,
                                new Response.Listener<JSONArray>() {
                                    @Override
                                    public void onResponse(JSONArray response) {
                                        ArrayList<String> listdata = new ArrayList<String>();
                                        if (response != null) {
                                            for (int i=0;i<response.length();i++){
                                                try {
                                                    listdata.add(response.getString(i));
                                                } catch (JSONException e) {
                                                    e.printStackTrace();
                                                }
                                            }
                                        }
                                        Log.d("value3", listdata.get(0));
                                        ArrayAdapter arrayAdapter = new ArrayAdapter(view_appointments.this,android.R.layout.simple_list_item_1, listdata);
                                        appointments.setAdapter(arrayAdapter);
                                    }
                                }
                                , new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Log.i("VolleyError", error.toString());
                            }
                        });
                        Singleton.getInstance(view_appointments.this).addToRequestQueue(stringRequest2);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.i("VolleyError", "That didn't work!");
            }
        });
        Singleton.getInstance(view_appointments.this).addToRequestQueue(stringRequest);

    }

}
