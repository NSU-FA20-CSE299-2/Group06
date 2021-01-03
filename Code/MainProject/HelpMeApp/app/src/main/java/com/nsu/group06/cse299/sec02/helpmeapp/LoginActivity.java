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
import com.nsu.group06.cse299.sec02.helpmeapp.utils.UserSignupInputValidator;

/**
 * User login Activity
 */
public class LoginActivity extends AppCompatActivity {

    // ui
    private EditText mEmailEditText, mPasswordEditText;
    private Button mLoginButton;

    // authentication variables
    private Authentication mAuth;
    private EmailPasswordAuthUser mEmailPasswordAuthUser;
    private Authentication.AuthenticationCallbacks mAuthenticationCallbacks = new Authentication.AuthenticationCallbacks() {
        @Override
        public void onAuthenticationSuccess(AuthenticationUser user) {

            startActivity(new Intent(LoginActivity.this, MenuActivity.class));

            //progressCompleteUI();

            // prevent user to come back to this activity
            // by pressing back button
            finish();
        }

        @Override
        public void onAuthenticationFailure(String message) {

            progressCompleteUI();

            showToast(getString(R.string.login_auth_failed));
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        init();
    }

    private void init() {

        mEmailEditText = findViewById(R.id.loginEmailEditText);
        mPasswordEditText = findViewById(R.id.loginPasswordEditText);
        mLoginButton = findViewById(R.id.loginButton);
    }

    public void loginClick(View view) {

        String email = mEmailEditText.getText().toString().trim().toLowerCase();
        String password = mPasswordEditText.getText().toString();

        if(validateInputs(email, password)){

            inProgressUI();

            loginUser(email, password);
        }

        else showToast(getString(R.string.login_auth_failed));
    }

    /*
    check input validity
     */
    private boolean validateInputs(String email, String password) {

        return UserSignupInputValidator.isEmailValid(email) && UserSignupInputValidator.isPasswordValid(password);
    }

    /*
    authenticate user with valid email and password
    using the mAuth variable
     */
    private void loginUser(String email, String password) {

        mEmailPasswordAuthUser = new EmailPasswordAuthUser(email, password);

        mAuth = new FirebaseEmailPasswordAuthentication(mAuthenticationCallbacks, mEmailPasswordAuthUser);
        mAuth.authenticateUser();
    }

    /*
    UI to show user while authentication is in progress
     */
    private void inProgressUI() {

        mLoginButton.setText(getString(R.string.login_button_label_logging_in));
        mLoginButton.setEnabled(false);
    }

    /*
    UI to show to user when authentication process yields a result
    i.e process is complete
     */
    private void progressCompleteUI() {

        mLoginButton.setText(getString(R.string.login_button_label));
        mLoginButton.setEnabled(true);
    }

    public void dontHaveAccountClick(View view) {

        startActivity(new Intent(this, SignupActivity.class));

        // to prevent possible infinite back stack formation
        finish();
    }

    private void showToast(String message){

        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}