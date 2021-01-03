package com.nsu.group06.cse299.sec02.helpmeapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class SetupProfileActivity extends AppCompatActivity {

    // ui
    private EditText mUsernameEditText, mDateOfBirhtEditText, mAddressEditText, mPhoneNumberEditText;

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
    }

    /*
    "save profile" click listener
     */
    public void saveProfileClick(View view) {
    }
}