package com.nsu.group06.cse299.sec02.helpmeapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.nsu.group06.cse299.sec02.helpmeapp.auth.Authentication;
import com.nsu.group06.cse299.sec02.helpmeapp.auth.AuthenticationUser;
import com.nsu.group06.cse299.sec02.helpmeapp.auth.EmailPasswordAuthUser;
import com.nsu.group06.cse299.sec02.helpmeapp.auth.FirebaseEmailPasswordAuthentication;
import com.nsu.group06.cse299.sec02.helpmeapp.utils.UserInputValidator;

/**
 * Activity for new users to sign up
 */
public class SignupActivity extends AppCompatActivity {

    // ui
    private EditText mEmailEditText, mPasswordEditText;
    private Button mSignupButton;

    // user registration authentication variables
    private Authentication mAuth;
    private EmailPasswordAuthUser mEmailPasswordAuthUser;
    private Authentication.RegisterUserAuthenticationCallbacks mRegistrationAuthCallbacks = new Authentication.RegisterUserAuthenticationCallbacks() {
        @Override
        public void onRegistrationSuccess(AuthenticationUser user) {

            progressCompleteUI();

            startActivity(new Intent(SignupActivity.this, MenuActivity.class));

            // user can't return back to this activity by pressing back button
            // after signing up
            finish();
        }

        @Override
        public void onRegistrationFailure(String message) {

            progressCompleteUI();

            showToast(getString(R.string.registration_error));
        }
    };


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

            inProgressUI();

            registerUser(email, password);
        }
    }

    /*
    UI to show user while registration is in progress
     */
    private void inProgressUI() {

        mSignupButton.setText(getString(R.string.signup_button_label_signing_up));
        mSignupButton.setEnabled(false);
    }

    /*
    UI to show to user when registration process yields a result
    i.e process is complete
     */
    private void progressCompleteUI() {

        mSignupButton.setText(getString(R.string.signup_button_label));
        mSignupButton.setEnabled(true);
    }

    /*
    register user with valid email and password
    using the mAuth variable
     */
    private void registerUser(String email, String password) {

        mEmailPasswordAuthUser = new EmailPasswordAuthUser(email, password);

        mAuth = new FirebaseEmailPasswordAuthentication(mRegistrationAuthCallbacks, mEmailPasswordAuthUser);
        mAuth.registerUserAuthentication();
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

        // to prevent possible infinite back stack formation
        finish();
    }

    private void showToast(String message){

        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}