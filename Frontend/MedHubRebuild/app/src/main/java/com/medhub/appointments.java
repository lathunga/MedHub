package com.medhub;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;

public class appointments extends AppCompatActivity {
    Button makeAnAppointment, viewAppointments;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.appointments);

        makeAnAppointment = (Button) findViewById(R.id.make_appointment);
        makeAnAppointment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(appointments.this, make_appointment.class);
                startActivity(i);
            }
        });

        viewAppointments = (Button) findViewById(R.id.view_appointments);
        viewAppointments.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(appointments.this, view_appointments.class);
                startActivity(i);
            }
        });

    }
}
