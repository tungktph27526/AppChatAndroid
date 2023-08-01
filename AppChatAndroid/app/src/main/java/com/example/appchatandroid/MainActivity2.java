package com.example.appchatandroid;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import io.socket.client.IO;
import io.socket.client.Socket;
import io.socket.emitter.Emitter;

public class MainActivity2 extends AppCompatActivity {
    private EditText edLog;
    private Button btnLog;
    private Button btnChat;
    private RecyclerView idRcv;
    List<String> list = new ArrayList<>();
    public static final String URL_SVR = "http://192.168.1.4:3000";
    Socket msg_socket;
    MessageAdapter messageAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        edLog = (EditText) findViewById(R.id.ed_log);
        btnLog = (Button) findViewById(R.id.btn_log);
        btnChat = (Button) findViewById(R.id.btn_chat);
        idRcv = (RecyclerView) findViewById(R.id.id_rcv);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        idRcv.setLayoutManager(layoutManager);

        ConnectSocket();
        msg_socket.on("receiver_massage", setOnNewMessage);

        btnLog.setOnClickListener(v ->{
            msg_socket.emit("user_login", edLog.getText().toString().trim());
        });
        btnChat.setOnClickListener(v ->{
            msg_socket.emit("send_message", edLog.getText().toString().trim());
        });
        messageAdapter = new MessageAdapter(this, list);
        idRcv.setAdapter(messageAdapter);
    }
    private Emitter.Listener setOnNewMessage = new Emitter.Listener() {
        @Override
        public void call(final Object... args) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    JSONObject data = (JSONObject) args[0];
                    String msg = null;
                    try {
                        msg = data.getString("data");
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    messageAdapter.addMessage(msg);
                }
            });
        }
    };
    void ConnectSocket(){
        try {
            msg_socket = IO.socket(URL_SVR);
            msg_socket.connect();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}