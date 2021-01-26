package com.example.medhub;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
//starts activity from here
public class MainActivity extends AppCompatActivity {
    private Button btnJson, btnString, btnImage;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //buttons to figure out the login type of the user
        Button patientBtn = (Button) findViewById(R.id.patientButton);//edit here
        Button adminBtn = (Button) findViewById(R.id.adminButton);
        Button docBtn = (Button) findViewById(R.id.doctorButton);
        //set the patient button to have a purpose which is to relay that the user is of patient type
        //then sends the info in a package key intent to the login class
        patientBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent startIntent = new Intent(getApplicationContext(), loginType.class);
                startIntent.putExtra("edu.iastate.medhublogin.patientinfo", "Welcome Patient");
                startActivity(startIntent);
            }
        });
        //set the admin button to have a purpose which is to relay that the user is of admin type
        //then sends the info in a package key intent to the login class
        adminBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent startIntent = new Intent(getApplicationContext(), loginType.class);
                startIntent.putExtra("edu.iastate.medhublogin.admininfo", "Welcome  Admin");
                startActivity(startIntent);
            }
        });
        //set the doctor button to have a purpose which is to relay that the user is of doctor type
        //then sends the info in a package key intent to the login class
        docBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent startIntent = new Intent(getApplicationContext(), loginType.class);
                startIntent.putExtra("edu.iastate.medhublogin.doctorinfo", "Welcome Doctor");
                startActivity(startIntent);
            }
        });
    }
}

