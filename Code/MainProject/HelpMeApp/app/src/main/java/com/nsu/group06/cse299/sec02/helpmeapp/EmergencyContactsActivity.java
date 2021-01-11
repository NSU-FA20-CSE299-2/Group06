package com.nsu.group06.cse299.sec02.helpmeapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.nsu.group06.cse299.sec02.helpmeapp.auth.Authentication;
import com.nsu.group06.cse299.sec02.helpmeapp.auth.AuthenticationUser;
import com.nsu.group06.cse299.sec02.helpmeapp.auth.FirebaseEmailPasswordAuthentication;
import com.nsu.group06.cse299.sec02.helpmeapp.database.Database;
import com.nsu.group06.cse299.sec02.helpmeapp.database.firebase_database.FirebaseRDBApiEndPoint;
import com.nsu.group06.cse299.sec02.helpmeapp.database.firebase_database.FirebaseRDBSingleOperation;
import com.nsu.group06.cse299.sec02.helpmeapp.dialogFragments.AddEmergencyContactDialogFragment;
import com.nsu.group06.cse299.sec02.helpmeapp.models.EmergencyContact;
import com.nsu.group06.cse299.sec02.helpmeapp.recyclerViewAdapters.EmergencyContactsAdapter;
import com.nsu.group06.cse299.sec02.helpmeapp.sharedPreferences.EmergencyContactsSharedPref;
import com.nsu.group06.cse299.sec02.helpmeapp.utils.NosqlDatabasePathUtils;

/**
 * Activity for "Emergency Contact" screen
 */
public class EmergencyContactsActivity extends AppCompatActivity
        implements AddEmergencyContactDialogFragment.InputListener, EmergencyContactsAdapter.CallerActivityCallbacks{

    private static final String TAG = "ECA-debug";

    // ui
    private TextView mEmergencyContactsEmptyTextView;
    private AddEmergencyContactDialogFragment mAddEmergencyContactDialogFragment;
    private RecyclerView mEmergencyContactsRecyclerView;
    private EmergencyContactsAdapter mEmergencyContactsAdapter;

    // variables used for fetching user uid
    private AuthenticationUser mAuthenticationUser;
    private Authentication mAuth;

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

        mEmergencyContactsEmptyTextView = findViewById(R.id.emergencyContactsEmpty_TextView);

        mEmergencyContactsRecyclerView = findViewById(R.id.emergencyContacts_RecyclerView);

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

    /**
     * populate the recycler view
     */
    private void loadExistingEmergencyContacts() {

        mEmergencyContactsAdapter =
                new EmergencyContactsAdapter(this, this, mAuthenticationUser.getmUid());
        mEmergencyContactsRecyclerView.setAdapter(mEmergencyContactsAdapter);
        mEmergencyContactsRecyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    /**
     * Listener to notify that
     * existing emergency contacts is not empty
     */
    @Override
    public void onDataListNotEmpty() {

        mEmergencyContactsEmptyTextView.setVisibility(View.GONE);
    }


    @Override
    protected void onStop() {

        mEmergencyContactsAdapter.onDestroy();
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

            mAddEmergencyContactSingleOperationDatabase.createWithId(emergencyContact.getPhoneNumber(), emergencyContact);

    }

    private void saveToLocalDatabase(EmergencyContact emergencyContact) {

        mEmergencyContactsSharedPref = EmergencyContactsSharedPref.build(this);
        mEmergencyContactsSharedPref.addPhoneNumber(emergencyContact.getPhoneNumber());
    }


    private void showToast(String message){

        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}