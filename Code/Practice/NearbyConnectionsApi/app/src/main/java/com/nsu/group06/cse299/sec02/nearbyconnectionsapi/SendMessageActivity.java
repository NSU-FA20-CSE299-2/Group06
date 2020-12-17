package com.nsu.group06.cse299.sec02.nearbyconnectionsapi;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class SendMessageActivity extends AppCompatActivity {

    // ui
    private EditText messageEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_message);

        init();
    }

    private void init() {

        messageEditText = findViewById(R.id.message_EditText);
    }

    public void sendClicked(View view) {
    }
}