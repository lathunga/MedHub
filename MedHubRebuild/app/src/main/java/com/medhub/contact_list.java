package com.medhub;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import android.util.Log;
import android.widget.TextView;

public class contact_list extends AppCompatActivity {
    public String clickedItem,accountType="Patient",username = "Sender",userId;
    private ListView theList;
    private List<String> contactIds;
    Button createContact, returnButton;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.contact_list);

        Intent in = getIntent();
        username = in.getStringExtra("username");
        accountType = in.getStringExtra("accountType");

        ((TextView) findViewById(R.id.NameBox)).setText(username);
        ((TextView) findViewById(R.id.SpecialityBox)).setText(accountType);

        createContact = findViewById(R.id.AddContact);
        createContact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Whatever you want button to do.
                Intent i = new Intent(getApplicationContext(), add_contact.class);
                i.putExtra("username", username);
                startActivity(i);
            }});

        theList = findViewById(R.id.list);
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, new ArrayList<String>());
        theList.setAdapter(arrayAdapter);

        makeStringReq(username);
//        getContactIds(userId);

        /*
        arrayAdapter.add adds contacts to contact list, will eventually
        get contact list from server.
         */
        arrayAdapter.add("Evan");
        arrayAdapter.add("Aaron");
        arrayAdapter.add("Luke");

        theList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                clickedItem = (String) theList.getItemAtPosition(position);

                Intent i = new Intent(getApplicationContext(), instant_message.class);
                i.putExtra("otherUsername", clickedItem);
                i.putExtra("myUsername", username);
                startActivity(i);
            }
        });

        returnButton = (Button) findViewById(R.id.returnButton);
        returnButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i;
                switch (accountType) {
                    case "Patient":
                        i = new Intent(contact_list.this, patient_home.class);
                        i.putExtra("username", username);
                        startActivity(i);
                        break;
                    case "Doctor":
                        i = new Intent(contact_list.this, doctor_home.class);
                        startActivity(i);
                        break;
                    case "Admin":
                        i = new Intent(contact_list.this, admin_home.class);
                        startActivity(i);
                        break;
                }
            }
        });
    }

    public void getContactIds(final String inputId) {
        String url = "http://coms-309-sk-3.cs.iastate.edu:8080/Users/" + inputId + "/contact";
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        String[] temp = response.split("@");
                        for (int i = 0; i < temp.length; i++) {
                            contactIds.add(temp[i]);
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.i("VolleyError", error.toString());
            }
        });
        Singleton.getInstance(contact_list.this).addToRequestQueue(stringRequest);
    }

    private void makeStringReq(String unameInput) {
        String url = "http://coms-309-sk-3.cs.iastate.edu:8080/Users/get/" + unameInput + "/id";
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        userId = response;
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.i("VolleyError", error.toString());
            }
        });
        Singleton.getInstance(contact_list.this).addToRequestQueue(stringRequest);
    }
}
