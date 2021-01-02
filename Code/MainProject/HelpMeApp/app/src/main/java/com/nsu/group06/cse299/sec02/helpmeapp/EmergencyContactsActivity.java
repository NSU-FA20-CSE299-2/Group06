package com.nsu.group06.cse299.sec02.helpmeapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

import com.nsu.group06.cse299.sec02.helpmeapp.dialogFragments.AddEmergencyContactDialogFragment;
import com.nsu.group06.cse299.sec02.helpmeapp.models.EmergencyContact;

public class EmergencyContactsActivity extends AppCompatActivity implements AddEmergencyContactDialogFragment.InputListener{

    // ui
    private AddEmergencyContactDialogFragment mAddEmergencyContactDialogFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_emergency_contacts);

        init();
    }

    private void init() {

        mAddEmergencyContactDialogFragment =
                new AddEmergencyContactDialogFragment(this);
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
     * in the EmergencyContactsDialogFragmetn fragment
     * @param emergencyContact user inputted emergency contact
     */
    @Override
    public void validInputTaken(EmergencyContact emergencyContact) {

        //TODO: store emergency contact locally and in remote database
    }
}