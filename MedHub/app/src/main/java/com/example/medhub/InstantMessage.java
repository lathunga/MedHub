package com.example.medhub;



import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.EditText;
import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Random;

import tech.gusavila92.websocketclient.WebSocketClient;


public class InstantMessage extends AppCompatActivity
{
    String key = "@";
    WebSocketClient webSocketClient;
    ArrayAdapter<String> adapter;
    String myName;
    Random rand = new Random();
    int id = rand.nextInt(1000000000);
    ListView textList;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_instant_message);
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1);
        textList = findViewById(R.id.TextList);
        textList.setAdapter(adapter);
        Intent intent = getIntent();
        String name = intent.getStringExtra(ContactList.EXTRA_MESSAGE);
        myName = intent.getStringExtra(ContactList.EXTRA_MESSAGE2);
        createWebSocketClient();
        Button SendBtn = findViewById(R.id.SendButton);
        ((TextView)findViewById(R.id.NameBox)).setText(name);
        ((TextView)findViewById(R.id.SpecialityBox)).setText("Whatever server gives us");
        ((TextView)findViewById(R.id.PhoneBox)).setText("Whatever server gives us");
        SendBtn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                String lastMessage = ((TextView) findViewById(R.id.MessageBox)).getText().toString();
                if(!lastMessage.isEmpty())
                {
                    //need to add the users name to the message, ex. "Evan:"+lastMessage
                    //adapter.add("Me: "+lastMessage);
                    //Send message to server
                    webSocketClient.send(id + key+myName+key+"Time"+key+lastMessage);
                    id = id + 1;
                    ((TextView) findViewById(R.id.MessageBox)).setText("");
                }
                //need to also check size before sending to server to make sure the server can handle the size of the message
            }
        });
        //constantly receive messages from server
        //figure out how to show all the messages
    }
    private void createWebSocketClient(){
        URI uri;
        try {
            uri = new URI("ws://coms-309-sk-3.cs.iastate.edu:8080/websocket");
        }
        catch (URISyntaxException e){
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
                        try{
//                            figure out who sent the message and put the correct prefix to it ie "Me:"
                            String[] arrOfStr = message.split(":",2);
                            if(arrOfStr[0].contains(myName))
                            {
                                adapter.add("Me: " + arrOfStr[1]);
                            }
                            else
                            {
                                adapter.add(arrOfStr[0] + arrOfStr[1]);
                            }
                            //adapter.add(message);
                        } catch (Exception e){
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
    public boolean isThereText(){
        return adapter.getCount()!=0;
    }
    public void AddMessage(String s)
    {
        adapter.add(s);
    }
}
