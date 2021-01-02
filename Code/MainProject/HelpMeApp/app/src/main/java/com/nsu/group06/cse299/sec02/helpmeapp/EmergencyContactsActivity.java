package com.nsu.group06.cse299.sec02.helpmeapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.nsu.group06.cse299.sec02.helpmeapp.auth.Authentication;
import com.nsu.group06.cse299.sec02.helpmeapp.auth.AuthenticationUser;
import com.nsu.group06.cse299.sec02.helpmeapp.auth.FirebaseEmailPasswordAuthentication;
import com.nsu.group06.cse299.sec02.helpmeapp.database.Database;
import com.nsu.group06.cse299.sec02.helpmeapp.database.firebase_database.FirebaseRDBApiEndPoint;
import com.nsu.group06.cse299.sec02.helpmeapp.database.firebase_database.FirebaseRDBSingleOperation;
import com.nsu.group06.cse299.sec02.helpmeapp.dialogFragments.AddEmergencyContactDialogFragment;
import com.nsu.group06.cse299.sec02.helpmeapp.models.EmergencyContact;
import com.nsu.group06.cse299.sec02.helpmeapp.utils.NosqlDatabasePathUtils;

/**
 * Activity for "Emergency Contact" screen
 */
public class EmergencyContactsActivity extends AppCompatActivity implements AddEmergencyContactDialogFragment.InputListener{

    private static final String TAG = "ECA-debug";

    // ui
    private AddEmergencyContactDialogFragment mAddEmergencyContactDialogFragment;

    // models


    // variables used for fetching user uid
    private AuthenticationUser mAuthenticationUser;
    private Authentication mAuth;

    // variables used to save emergency contact to database
    private Database.SingleOperationDatabase<EmergencyContact> mAddEmergencyContactSingleOperationDatabase;
    private FirebaseRDBApiEndPoint mAddEmergencyContactApiEndPoint;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_emergency_contacts);

        init();
    }

    private void init() {

        mAddEmergencyContactDialogFragment =
                new AddEmergencyContactDialogFragment(this);

        // authenticate user, because we need uid here
        mAuth = new FirebaseEmailPasswordAuthentication(new Authentication.AuthenticationCallbacks() {
            @Override
            public void onAuthenticationSuccess(AuthenticationUser user) {

                mAuthenticationUser = user;

                showToast(mAuthenticationUser.getmUid());
            }

            @Override
            public void onAuthenticationFailure(String message) {

                doHardLogout();
            }
        });
        mAuth.authenticateUser();
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


    /*
    Add new emergency contact click
     */
    public void addClick(View view) {

        if(mAuthenticationUser!=null) {

            // show dialog to take emergency contact as input
            mAddEmergencyContactDialogFragment
                    .show(getSupportFragmentManager(), "emergency-contact-input-dialog");
        }
    }

    /**
     * New emergency contact got from user input
     * in the EmergencyContactsDialogFragment fragment
     * @param emergencyContact user inputted emergency contact
     */
    @Override
    public void validInputTaken(EmergencyContact emergencyContact) {

        saveToRemoteDatabase(emergencyContact);

        //saveToLocalDatabase(emergencyContact);

        showToast(emergencyContact.getmName());
    }

    private void saveToRemoteDatabase(EmergencyContact emergencyContact) {

        mAddEmergencyContactApiEndPoint = new FirebaseRDBApiEndPoint(
                "/" + NosqlDatabasePathUtils.EMERGENCY_CONTACTS_NODE
                        + "/" + mAuthenticationUser.getmUid()
                        + "/" + NosqlDatabasePathUtils.EMERGENCY_CONTACTS_PHONE_NODE);

        mAddEmergencyContactSingleOperationDatabase =
                new FirebaseRDBSingleOperation(

                        EmergencyContact.class,

                        mAddEmergencyContactApiEndPoint,

                        new Database.SingleOperationDatabase.SingleOperationDatabaseCallback() {
                            @Override
                            public void onDataRead(Object data) {
                                // not reading anything
                            }

                            @Override
                            public void onDatabaseOperationSuccess() {
                                // not required in this case
                            }

                            @Override
                            public void onDatabaseOperationFailed(String message) {
                                Log.d(TAG, "onDatabaseOperationFailed: error -> " + message);
                            }
                        });

            mAddEmergencyContactSingleOperationDatabase.createWithId(emergencyContact.getmPhoneNumber(), emergencyContact);

    }

    private void saveToLocalDatabase(EmergencyContact emergencyContact) {
        //TODO: store emergency contact locally
    }


    private void showToast(String message){

        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}