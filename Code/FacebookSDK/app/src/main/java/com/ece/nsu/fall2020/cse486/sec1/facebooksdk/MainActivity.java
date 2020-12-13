package com.ece.nsu.fall2020.cse486.sec1.facebooksdk;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

public class MainActivity extends AppCompatActivity {

    TextView authIdTV;
    LoginButton loginButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        authIdTV = findViewById(R.id.fbauthid);
        loginButton = findViewById(R.id.loginBtn);

        CallbackManager callbackManager = CallbackManager.Factory.create();
        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                authIdTV.setText(getString(R.string.id).concat(loginResult.getAccessToken().getUserId()));
            }

            @Override
            public void onCancel() {
                Toast.makeText(MainActivity.this, "Cancelled!", Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onError(FacebookException error) {
                Toast.makeText(MainActivity.this, "Error Occurred!", Toast.LENGTH_SHORT).show();
            }
        });

        
    }
}