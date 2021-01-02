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
import com.nsu.group06.cse299.sec02.helpmeapp.database.firebase_database.FirebaseRDBRealtime;
import com.nsu.group06.cse299.sec02.helpmeapp.database.firebase_database.FirebaseRDBSingleOperation;
import com.nsu.group06.cse299.sec02.helpmeapp.dialogFragments.AddEmergencyContactDialogFragment;
import com.nsu.group06.cse299.sec02.helpmeapp.models.EmergencyContact;
import com.nsu.group06.cse299.sec02.helpmeapp.models.User;
import com.nsu.group06.cse299.sec02.helpmeapp.sharedPreferences.EmergencyContactsSharedPref;
import com.nsu.group06.cse299.sec02.helpmeapp.utils.NosqlDatabasePathUtils;

import java.util.ArrayList;

/**
 * Activity for "Emergency Contact" screen
 */
public class EmergencyContactsActivity extends AppCompatActivity implements AddEmergencyContactDialogFragment.InputListener{

    private static final String TAG = "ECA-debug";

    // ui
    private AddEmergencyContactDialogFragment mAddEmergencyContactDialogFragment;

    // models
    private ArrayList<EmergencyContact> mExistingEmergencyContacts;

    // variables used for fetching user uid
    private AuthenticationUser mAuthenticationUser;
    private Authentication mAuth;

    // variables used to read existing emergency contacts from database
    // TODO: this should be done inside Adapter?
    private Database.RealtimeDatabase mReadEmergencyContactsRealtimeDatabase;
    private FirebaseRDBApiEndPoint mReadEmergencyContactsApiEndPoint;

    // variables used to save emergency contact to database
    private Database.SingleOperationDatabase<EmergencyContact> mAddEmergencyContactSingleOperationDatabase;
    private FirebaseRDBApiEndPoint mAddEmergencyContactApiEndPoint;

    // variables used to save emergency contact locally
    private EmergencyContactsSharedPref mEmergencyContactsSharedPref;


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

                loadExistingEmergencyContacts();
            }

            @Override
            public void onAuthenticationFailure(String message) {

                doHardLogout();
            }
        });
        mAuth.authenticateUser();
    }

    /*
    Read existing emergency contacts from remote database
     */
    private void loadExistingEmergencyContacts() {

        // TODO: this should be done inside Adapter?

        mExistingEmergencyContacts = new ArrayList<>();

        mReadEmergencyContactsApiEndPoint = new FirebaseRDBApiEndPoint(
                "/" + NosqlDatabasePathUtils.EMERGENCY_CONTACTS_NODE
                        + "/" + mAuthenticationUser.getmUid()
                        + "/" + NosqlDatabasePathUtils.EMERGENCY_CONTACTS_PHONE_NODE);

        mReadEmergencyContactsRealtimeDatabase = new FirebaseRDBRealtime<EmergencyContact>(

                EmergencyContact.class,

                mReadEmergencyContactsApiEndPoint,

                new Database.RealtimeDatabase.RealtimeChangesDatabaseCallback<EmergencyContact>() {
                    @Override
                    public void onDataAddition(EmergencyContact data) {

                        mExistingEmergencyContacts.add(data);

                        //showToast(data.toString());
                    }

                    @Override
                    public void onDataUpdate(EmergencyContact data) {
                        // not required
                    }

                    @Override
                    public void onDataDeletion(EmergencyContact data) {

                        mExistingEmergencyContacts.remove(data);
                    }

                    @Override
                    public void onDatabaseOperationSuccess() {
                        // not required
                    }

                    @Override
                    public void onDatabaseOperationFailed(String message) {

                        showToast(getString(R.string.failed_to_connect));
                    }
                }
        );

        mReadEmergencyContactsRealtimeDatabase.listenForListDataChange();
    }

    @Override
    protected void onStop() {

        // MUST CALL THIS TO AVOID UNNECESSARY DOWNLOAD
        mReadEmergencyContactsRealtimeDatabase.stopListeningForDataChange();

        super.onStop();
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

        saveToLocalDatabase(emergencyContact);

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

        mEmergencyContactsSharedPref = EmergencyContactsSharedPref.build(this);
        mEmergencyContactsSharedPref.addPhoneNumber(emergencyContact.getmPhoneNumber());
    }


    private void showToast(String message){

        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}