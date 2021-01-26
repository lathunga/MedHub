package com.example.medhub;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

public class DoctorMain extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_main);

        //sees if the key has been sent already by checking the contents
        //also helps if the app already starts loading on this screen to retrace pathing
        if (getIntent().hasExtra("edu.iastate.medhublogin.doctorinfo"))
        {
            TextView doctextview = (TextView) findViewById(R.id.doctorTextView);
            //variable "text" going to get the string associated with that intent key (HELLO WORLD!)
            String text = getIntent().getExtras().getString("edu.iastate.medhublogin.doctorinfo");
            //set text the is recieved from bundle of information to textview button
            doctextview.setText(text);
        }
    }
}
