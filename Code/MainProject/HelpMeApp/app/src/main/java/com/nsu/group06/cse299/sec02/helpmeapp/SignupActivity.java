package com.nsu.group06.cse299.sec02.helpmeapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class SignupActivity extends AppCompatActivity {

    // ui
    private EditText mEmailEditText, mPasswordEditText;
    private Button mSignupButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        init();
    }

    private void init() {

        mEmailEditText = findViewById(R.id.signupEmailEditText);
        mPasswordEditText = findViewById(R.id.signupPasswordEditText);
        mSignupButton = findViewById(R.id.signupButton);

    }

    public void signupClick(View view) {

        String email = mEmailEditText.getText().toString().trim().toLowerCase();
        String password = mPasswordEditText.getText().toString();

        if(validateInputs(email, password)){

            showToast("all inputs valid");
        }
    }

    /*
    check input validity,
    make UI changes to let user know of mistakes
     */
    private boolean validateInputs(String email, String password) {

        if( UserInputValidator.isEmailValid(email) && UserInputValidator.isPasswordValid(password)){

            return true;
        }

        if(!UserInputValidator.isEmailValid(email)) mEmailEditText.setError("invalid email!");

        if(!UserInputValidator.isPasswordValid(password)) mPasswordEditText.setError("invalid password!");

        return false;
    }

    public void alreadyHaveAccountClick(View view) {

        startActivity(new Intent(this, LoginActivity.class));
    }

    private void showToast(String message){

        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}