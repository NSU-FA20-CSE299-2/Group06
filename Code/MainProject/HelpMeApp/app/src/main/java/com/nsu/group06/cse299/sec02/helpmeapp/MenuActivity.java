package com.nsu.group06.cse299.sec02.helpmeapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.nsu.group06.cse299.sec02.helpmeapp.auth.FirebaseEmailPasswordAuthentication;

/**
 * App home page, which is a menu
 */
public class MenuActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
    }

    // METHOD JUST FOR TESTING LOGOUT BACKEND WILL BE REMOVED
    public void logoutClick(View view) {

        new FirebaseEmailPasswordAuthentication().signOut();

        startActivity(new Intent(this, LoginActivity.class));
        finish();
    }
}