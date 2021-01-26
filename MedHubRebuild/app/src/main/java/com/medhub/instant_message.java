package com.medhub;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Random;
import tech.gusavila92.websocketclient.WebSocketClient;

public class instant_message extends AppCompatActivity {
    WebSocketClient webSocketClient;
    ArrayAdapter<String> adapter;
    String myName,otherName, myId = "1", otherId = "2",key = "@";
    int id = (new Random()).nextInt(10000);
    ListView textList;
    Button SendBtn, ReturnButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.instant_message);

        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1);
        textList = findViewById(R.id.TextList);
        textList.setAdapter(adapter);

        Intent intent = getIntent();
        otherName = intent.getStringExtra("otherUsername");
        myName = intent.getStringExtra("myUsername");

        createWebSocketClient();

        ((TextView) findViewById(R.id.NameBox)).setText(otherName);
        //TODO
        ((TextView) findViewById(R.id.SpecialityBox)).setText("Doctor");

        SendBtn = findViewById(R.id.SendButton);
        SendBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String lastMessage = ((TextView) findViewById(R.id.MessageBox)).getText().toString();
                if (!lastMessage.isEmpty()) {
                    getUserId(myName, true);
                    getUserId(otherName, false);

                    // Order to send items in: userId, otherUserId, message, name, timestamp
                    webSocketClient.send(myId + key + otherId + key + lastMessage + key + myName + key + "Time");
                    id = id + 1;
                    ((TextView) findViewById(R.id.MessageBox)).setText("");
                }
            }
        });

        ReturnButton = findViewById(R.id.returnFromChatsButton);
        ReturnButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), contact_list.class);
                i.putExtra("username", myName);
                i.putExtra("accountType", "Patient");
                startActivity(i);
            }
        });
    }

    private void createWebSocketClient() {
        URI uri;
        try {
            uri = new URI("ws://coms-309-sk-3.cs.iastate.edu:8080/websocket");
        } catch (URISyntaxException e) {
            e.printStackTrace();
            return;
        }

        webSocketClient = new WebSocketClient(uri) {
            @Override
            public void onOpen() {
                Log.i("WebSocket", "Session is starting");
                webSocketClient.send(key);
            }

            @Override
            public void onTextReceived(String s) {
                Log.i("WebSocket", "Message received");
                final String message = s;
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            // Figure out who sent the message and put the correct prefix to it ie "Me:"
                            String[] arrOfStr = message.split(":", 2);

                            if (arrOfStr[0].contains(myName)) {
                                adapter.add("Me: " + arrOfStr[1]);
                            } else {
                                adapter.add(arrOfStr[0] + arrOfStr[1]);
                            }

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });
            }

            @Override
            public void onBinaryReceived(byte[] data) {
            }

            @Override
            public void onPingReceived(byte[] data) {
            }

            @Override
            public void onPongReceived(byte[] data) {
            }

            @Override
            public void onException(Exception e) {
                System.out.println(e.getMessage());
            }

            @Override
            public void onCloseReceived() {
                Log.i("WebSocket", "Closed ");
                System.out.println("onCloseReceived");
            }
        };
        webSocketClient.setConnectTimeout(10000);
        webSocketClient.setReadTimeout(60000);
        webSocketClient.enableAutomaticReconnection(5000);
        webSocketClient.connect();
    }

    public boolean isThereText() {
        return adapter.getCount() != 0;
    }

    public void AddMessage(String s) {
        adapter.add(s);
    }

    public void getUserId(final String username, final boolean myUsername) {
        String url = "http://coms-309-sk-3.cs.iastate.edu:8080/Users/get/" + username + "/id";
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if (myUsername) {
                            myId = response;
                        } else {
                            otherId = response;
                        }
                        Log.i("LocalValuesInMethod", myId + " | " + otherId);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.i("VolleyError", error.toString());
            }
        });
        Singleton.getInstance(instant_message.this).addToRequestQueue(stringRequest);
    }
}
