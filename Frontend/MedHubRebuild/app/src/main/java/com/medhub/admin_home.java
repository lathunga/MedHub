package com.medhub;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;

public class admin_home extends AppCompatActivity {
    Button appointment,viewChats,logOut;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin_home);

        Intent in = getIntent();
        final String username = in.getStringExtra("username");

        logOut = (Button) findViewById(R.id.logOutButton);
        logOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(admin_home.this, login.class);
                startActivity(i);
            }
        });

        viewChats = (Button) findViewById(R.id.viewChatButton);
        viewChats.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(admin_home.this, contact_list.class);
                i.putExtra("username", username);
                i.putExtra("accountType", "Patient");
                startActivity(i);
            }
        });
    }
}
