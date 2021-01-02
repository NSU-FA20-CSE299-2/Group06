package com.nsu.group06.cse299.sec02.helpmeapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.nsu.group06.cse299.sec02.helpmeapp.auth.Authentication;
import com.nsu.group06.cse299.sec02.helpmeapp.auth.AuthenticationUser;
import com.nsu.group06.cse299.sec02.helpmeapp.auth.FirebaseEmailPasswordAuthentication;
import com.nsu.group06.cse299.sec02.helpmeapp.dialogFragments.AddEmergencyContactDialogFragment;
import com.nsu.group06.cse299.sec02.helpmeapp.models.EmergencyContact;

public class EmergencyContactsActivity extends AppCompatActivity implements AddEmergencyContactDialogFragment.InputListener{

    // ui
    private AddEmergencyContactDialogFragment mAddEmergencyContactDialogFragment;

    // models

    // variables used for fetching user uid
    private AuthenticationUser mAuthenticationUser;
    private Authentication mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_emergency_contacts);

        init();
    }

    private void init() {

        mAddEmergencyContactDialogFragment =
                new AddEmergencyContactDialogFragment(this);

        mAuth = new FirebaseEmailPasswordAuthentication();
    }

    /*
    Add new emergency contact click
     */
    public void addClick(View view) {

        // show dialog to take emergency contact as input
        mAddEmergencyContactDialogFragment
                .show(getSupportFragmentManager(), "emergency-contact-input-dialog");
    }

    /**
     * New emergency contact got from user input
     * in the EmergencyContactsDialogFragment fragment
     * @param emergencyContact user inputted emergency contact
     */
    @Override
    public void validInputTaken(EmergencyContact emergencyContact) {

        // authenticate user, emergency contacts will be save after authentication
        mAuth.setmAuthenticationCallbacks(new Authentication.AuthenticationCallbacks() {
            @Override
            public void onAuthenticationSuccess(AuthenticationUser user) {

                mAuthenticationUser = user;
                saveEmergencyContact(emergencyContact);
            }

            @Override
            public void onAuthenticationFailure(String message) {

                doHardLogout();
            }
        });
        mAuth.authenticateUser();
    }


    /*
    save emergency contact to local and remote database
     */
    private void saveEmergencyContact(EmergencyContact emergencyContact) {

        //TODO: store emergency contact locally and in remote database

        showToast(mAuthenticationUser.getmUid());
    }


    /*
    Authentication failed, logout immediately
     */
    private void doHardLogout() {

        showToast(getString(R.string.hard_logout));

        mAuth.signOut();

        Intent intent = new Intent(this, LoginActivity.class);

        // clear out all activities on the back stack and open LoginActivity
        // so that back press from this point on closes the app
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);

        startActivity(intent);
    }

    private void showToast(String message){

        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}