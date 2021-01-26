package com.medhub;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import sun.bob.mcalendarview.MCalendarView;
import sun.bob.mcalendarview.listeners.OnDateClickListener;
import sun.bob.mcalendarview.vo.DateData;
import sun.bob.mcalendarview.vo.MarkedDates;

public class make_appointment extends AppCompatActivity {
    Button doctor, timeButton, submit;
    String[] listItems;
    MCalendarView cal;
    static ArrayList<String> dates;
    static String monthFinal;
    static String dayFinal;
    static String yearFinal;
    static String timeFinal;
    static String doctorFinal;



    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.make_appointment);
        String url5 = "http://coms-309-sk-3.cs.iastate.edu:8080/Users/accountType/doctor";
        JsonArrayRequest stringRequest3 = new JsonArrayRequest(Request.Method.GET, url5, null,
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
                            GlobalVariables gb = new GlobalVariables();
                            gb = gb.getInstance();
                            gb.setOtherValue(listdata);
                        }
                    }
                }
                , new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.i("VolleyError", error.toString());
            }
        });
        Singleton.getInstance(make_appointment.this).addToRequestQueue(stringRequest3);

        cal = ((MCalendarView) findViewById(R.id.squareCal));
        cal.setOnDateClickListener(new OnDateClickListener() {
            @Override
            public void onDateClick(View view, DateData date) {
                MarkedDates markedDates = cal.getMarkedDates();
                ArrayList markData = markedDates.getAll();
                for (int k=0; k<markData.size();k++){
                    cal.unMarkDate((DateData) markData.get(k));
                }
                cal.markDate(date);
//                Toast.makeText(make_appointment.this, String.format("%d-%d", date.getMonth(), date.getDay()), Toast.LENGTH_SHORT).show();
                //Toast.makeText(make_appointment.this, dates.get(0), Toast.LENGTH_SHORT).show();
                final ArrayList<String> arr = new ArrayList<String>();
                monthFinal = date.getMonthString();
                dayFinal = date.getDayString();
                yearFinal = date.getYear() + "";
                for(int i=0; i<dates.size(); i++) {
                    String time = "";
                    char currChar = dates.get(i).charAt(0);
                    int j = 0;
                    String currDate = "";
                    while (currChar != ',') {
                        currDate += currChar;
                        j++;
                        currChar = dates.get(i).charAt(j);
                    }
                    time = dates.get(i).substring(++j, dates.get(i).length());
                    String month = "";
                    currChar = currDate.charAt(0);
                    j = 0;
                    while (currChar != '/') {
                        month += currChar;
                        j++;
                        currChar = currDate.charAt(j);
                    }
                    String day = "";
                    currChar = currDate.charAt(++j);
                    while (currChar != '/') {
                        day += currChar;
                        j++;
                        currChar = currDate.charAt(j);
                    }
                    String year = currDate.substring(++j, currDate.length());
                    String actYear = "" + date.getYear();
                    if (month.equals(date.getMonthString()) && year.equals(actYear) && day.equals(date.getDayString())) {
                        arr.add(time);
                    }
                }

                submit = (Button) findViewById(R.id.submit);
                submit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        GlobalVariables gb = new GlobalVariables();
                        gb = gb.getInstance();
                        String username = gb.getValue();

                        String url2 = "http://coms-309-sk-3.cs.iastate.edu:8080/Users/get/" + username + "/id";
                        StringRequest stringRequest = new StringRequest(Request.Method.GET, url2,
                                new Response.Listener<String>() {
                                    @Override
                                    public void onResponse(final String response) {
                                        String url = "http://coms-309-sk-3.cs.iastate.edu:8080/users/" + response + "/appointments";
                                        Toast.makeText(getApplicationContext(), response, Toast.LENGTH_LONG).show();
                                        StringRequest stringRequest2 = new StringRequest(Request.Method.POST, url,
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
                                        }) {
                                            @Override
                                            protected Map<String, String> getParams() throws AuthFailureError {
                                                Map<String, String> params = new HashMap<String, String>();
                                                String userId = response;
                                                String otherUserId = "sdf";

                                                params.put("userId", userId);
                                                params.put("otherUserId", doctorFinal);
                                                params.put("datetime", monthFinal + "/" + dayFinal + "/" + yearFinal);
                                                params.put("location", "NA");
                                                params.put("time", timeFinal);
                                                return params;
                                            }
                                        };

                                        Singleton.getInstance(make_appointment.this).addToRequestQueue(stringRequest2);
                                    }
                                }, new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Log.i("VolleyError", "That didn't work!");
                            }
                        });
                        Singleton.getInstance(make_appointment.this).addToRequestQueue(stringRequest);
                    }
                });


                timeButton = (Button) findViewById(R.id.time);
                timeButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        GlobalVariables gb = new GlobalVariables();
                        gb = gb.getInstance();
                        ArrayList<String> temp = gb.getOtherValue();
                        listItems = temp.toArray(new String[temp.size()]);
                        AlertDialog.Builder mBuilder = new AlertDialog.Builder(make_appointment.this);
                        mBuilder.setTitle("Choose an item");
                        mBuilder.setIcon(R.drawable.icon);
                        final String[] array = arr.toArray(new String[0]);
                        final ArrayList<String> array2 = new ArrayList<String>();
                        array2.add("9:00");array2.add("10:00");array2.add("11:00");array2.add("12:00");array2.add("1:00");array2.add("2:00");array2.add("3:00");array2.add("4:00");array2.add("5:00");
                        for(int i=0; i<array2.size(); i++)
                        {
                            String temp2 = array2.get(i);
                            for(int j=0; j<arr.size(); j++)
                            {
                                if(temp2.equals(arr.get(j)))
                                {
                                    array2.remove(i);
                                    j = arr.size();
                                }
                            }
                        }
                        final String[] array3 = array2.toArray(new String[0]);
                        mBuilder.setSingleChoiceItems(array3, -1, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, final int i) {
                                timeButton.setText(array3[i]);
                                timeFinal = array3[i];
                                dialogInterface.dismiss();
                            }
                        });
                        mBuilder.setNeutralButton("Cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                            }
                        });
                        AlertDialog mDialog = mBuilder.create();
                        mDialog.show();

                    }
                });
            }
        });



        doctor = (Button) findViewById(R.id.doctor);
        doctor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                GlobalVariables gb = new GlobalVariables();
                gb = gb.getInstance();
                ArrayList<String> temp = gb.getOtherValue();
                listItems = temp.toArray(new String[temp.size()]);
                AlertDialog.Builder mBuilder = new AlertDialog.Builder(make_appointment.this);
                mBuilder.setTitle("Choose an item");
                mBuilder.setIcon(R.drawable.icon);
                mBuilder.setSingleChoiceItems(listItems, -1, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, final int i) {
                        doctor.setText(listItems[i]);
                        String url = "http://coms-309-sk-3.cs.iastate.edu:8080/Users/get/" + listItems[i] + "/id";
                        final String otherUsername = listItems[i];
                        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                                new Response.Listener<String>() {
                                    @Override
                                    public void onResponse(String response) {
                                        Toast.makeText(getApplicationContext(), response, Toast.LENGTH_LONG).show();
                                        //here we should search for the appointments in which the other_user's id is that of the doctor that is currently chosen.

                                        GlobalVariables gb2 = new GlobalVariables();
                                        gb2 = gb2.getInstance();
                                        String username = gb2.getValue();
                                        Log.d("user_id", username);
                                        Log.d("other_user_id", otherUsername);
                                        //here we should find the id's of both of these users
                                        String url = "http://coms-309-sk-3.cs.iastate.edu:8080/Users/get/" + otherUsername;
                                        StringRequest stringRequest1 = new StringRequest(Request.Method.GET, url,
                                                new Response.Listener<String>() {
                                                    @Override
                                                    public void onResponse(String response) {
                                                        doctorFinal = response;
                                                        Log.d("yup", response);
                                                        String url = "http://coms-309-sk-3.cs.iastate.edu:8080/users/get/" + response + "/appointments";
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
                                                                            dates = listdata;
                                                                        }
//                                                                    Log.d("this", response);
//                                                                    dates = response;
                                                                    }
                                                                }
                                                                , new Response.ErrorListener() {
                                                            @Override
                                                            public void onErrorResponse(VolleyError error) {
                                                                Log.i("VolleyError", error.toString());
                                                            }
                                                        });
                                                        Singleton.getInstance(make_appointment.this).addToRequestQueue(stringRequest2);
                                                    }
                                                }
                                                , new Response.ErrorListener() {
                                            @Override
                                            public void onErrorResponse(VolleyError error) {
                                                Log.i("VolleyError", error.toString());
                                            }
                                        });
                                        Singleton.getInstance(make_appointment.this).addToRequestQueue(stringRequest1);
                                    }
                                }, new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Log.i("VolleyError", "That didn't work!");
                            }
                        });
                        Singleton.getInstance(make_appointment.this).addToRequestQueue(stringRequest);
                        dialogInterface.dismiss();
                    }
                });
                mBuilder.setNeutralButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                });
                AlertDialog mDialog = mBuilder.create();
                mDialog.show();
            }
        });


    }
}
