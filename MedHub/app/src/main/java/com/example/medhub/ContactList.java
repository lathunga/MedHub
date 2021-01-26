package com.example.medhub;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import java.util.ArrayList;
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

public class ContactList extends AppCompatActivity
{
    private ListView theList;
    public String clickedItem;
    public static final String EXTRA_MESSAGE = "com.example.myfirstapp.MESSAGE";
    public String thisUsername = "Sender";
    public static final String EXTRA_MESSAGE2 = "com.example.myfirstapp.MESSAGE";
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_list);
        //makeStringReq("username");
        ((TextView)findViewById(R.id.NameBox)).setText(thisUsername);
        theList = findViewById(R.id.list);
        ArrayList<String> arrayList = new ArrayList<>();
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,arrayList);
        theList.setAdapter(arrayAdapter);
        //arrayAdapter.add to add contacts from the server
        arrayAdapter.add("Evan");//These are for test and demo purposes
        arrayAdapter.add("Test2");
        arrayAdapter.add("Test3");
        theList.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id)
            {
                clickedItem=(String) theList.getItemAtPosition(position);
                Intent i = new Intent(getApplicationContext(), InstantMessage.class);
                i.putExtra(EXTRA_MESSAGE, clickedItem);
                Intent i2 = new Intent(getApplicationContext(), InstantMessage.class);
                i2.putExtra(EXTRA_MESSAGE2, thisUsername);
                startActivity(i);
                //send message to server on which contact you clicked
                //send them to instant message page for that user
            }
        });
    }
    private void makeStringReq(String requestType) {
        RequestQueue queue = Volley.newRequestQueue(this);
        String url = "http://coms-309-sk-3.cs.iastate.edu:8080/Users/" + 556 + "/" + requestType;
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response)
                    {
                        //will need to be changed later
                        thisUsername = response;
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