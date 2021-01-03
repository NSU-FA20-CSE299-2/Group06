package com.nsu.group06.cse299.sec02.helpmeapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.nsu.group06.cse299.sec02.helpmeapp.auth.Authentication;
import com.nsu.group06.cse299.sec02.helpmeapp.auth.AuthenticationUser;
import com.nsu.group06.cse299.sec02.helpmeapp.auth.FirebaseEmailPasswordAuthentication;
import com.nsu.group06.cse299.sec02.helpmeapp.utils.SessionUtils;

public class SetupProfileActivity extends AppCompatActivity {

    // ui
    private EditText mUsernameEditText, mDateOfBirhtEditText, mAddressEditText, mPhoneNumberEditText;

    // variables used for fetching user uid
    private AuthenticationUser mAuthenticationUser;
    private Authentication mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setup_profile);

        init();
    }

    private void init() {

        mUsernameEditText = findViewById(R.id.username_setupProfile_EditText);
        mDateOfBirhtEditText = findViewById(R.id.dateOfBirth_setupProfile_EditText);
        mAddressEditText = findViewById(R.id.address_setupProfile_EditText);
        mPhoneNumberEditText = findViewById(R.id.phoneNumber_setupProfile_EditText);

        // authenticate user, because we need uid here
        mAuth = new FirebaseEmailPasswordAuthentication(new Authentication.AuthenticationCallbacks() {
            @Override
            public void onAuthenticationSuccess(AuthenticationUser user) {

                mAuthenticationUser = user;

                loadUserProfileInformation();
            }

            @Override
            public void onAuthenticationFailure(String message) {

                SessionUtils.doHardLogout(SetupProfileActivity.this, mAuth);
            }
        });
        mAuth.authenticateUser();
    }

    /*
     Download user profile information from the database
     */
    private void loadUserProfileInformation() {
        //TODO: implement
    }


    /*
    "save profile" click listener
     */
    public void saveProfileClick(View view) {
    }

    private void showToast(String message){

        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}