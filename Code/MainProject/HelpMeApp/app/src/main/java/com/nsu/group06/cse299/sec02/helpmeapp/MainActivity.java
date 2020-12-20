package com.nsu.group06.cse299.sec02.helpmeapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.nsu.group06.cse299.sec02.helpmeapp.auth.Authentication;
import com.nsu.group06.cse299.sec02.helpmeapp.auth.AuthenticationUser;
import com.nsu.group06.cse299.sec02.helpmeapp.auth.FirebaseEmailPasswordAuthentication;

/**
 * Launching activity that decides whether user is logged in or not
 * will contains welcome screen shown to user only once after installation
 */
public class MainActivity extends AppCompatActivity {

    // auth variables
    private Authentication mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();
    }

    private void init() {

        mAuth = new FirebaseEmailPasswordAuthentication(new Authentication.AuthenticationCallbacks() {
            @Override
            public void onAuthenticationSuccess(AuthenticationUser user) {

                // user is logged in start the menu activity
                startActivity(new Intent(MainActivity.this, MenuActivity.class));

                // disable going back to MainActivity
                finish();
            }

            @Override
            public void onAuthenticationFailure(String message) {

                // user needs to login or signup
                startActivity(new Intent(MainActivity.this, LoginActivity.class));

                // disable going back to MainActivity
                finish();
            }
        });

        mAuth.authenticateUser();
    }
}