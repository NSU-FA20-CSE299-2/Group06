package com.nsu.group06.cse299.sec02.helpmeapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.nsu.group06.cse299.sec02.helpmeapp.auth.Authentication;
import com.nsu.group06.cse299.sec02.helpmeapp.auth.AuthenticationUser;
import com.nsu.group06.cse299.sec02.helpmeapp.auth.FirebaseEmailPasswordAuthentication;
import com.nsu.group06.cse299.sec02.helpmeapp.database.Database;
import com.nsu.group06.cse299.sec02.helpmeapp.database.firebase_database.FirebaseRDBApiEndPoint;
import com.nsu.group06.cse299.sec02.helpmeapp.database.firebase_database.FirebaseRDBSingleOperation;
import com.nsu.group06.cse299.sec02.helpmeapp.models.User;
import com.nsu.group06.cse299.sec02.helpmeapp.utils.NosqlDatabasePathUtils;
import com.nsu.group06.cse299.sec02.helpmeapp.utils.SessionUtils;

public class SetupProfileActivity extends AppCompatActivity {

    private static final String TAG = "SPA-debug";

    // ui
    private EditText mUsernameEditText, mDateOfBirhtEditText, mAddressEditText, mPhoneNumberEditText;

    // model
    private User mUser;

    // variable used for fetching user uid
    private Authentication mAuth;

    // variables to read existing information of users from the database
    private Database.SingleOperationDatabase<User> mReadUserInfoFirebaseRDBSingleOperation;
    private FirebaseRDBApiEndPoint mReadUserInfoApiEndPoint;

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

        mUser = new User();

        // authenticate user, because we need uid here
        mAuth = new FirebaseEmailPasswordAuthentication(new Authentication.AuthenticationCallbacks() {
            @Override
            public void onAuthenticationSuccess(AuthenticationUser user) {

                mUser.setmUid(user.getmUid());

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

        mReadUserInfoApiEndPoint =
                new FirebaseRDBApiEndPoint(
                        "/"+ NosqlDatabasePathUtils.USER_NODE +
                                ":" + mUser.getmUid()
                );

        mReadUserInfoFirebaseRDBSingleOperation =
                new FirebaseRDBSingleOperation(

                        User.class,

                        mReadUserInfoApiEndPoint,

                        new Database.SingleOperationDatabase.SingleOperationDatabaseCallback<User>() {
                            @Override
                            public void onDataRead(User data) {

                                mUser = data;
                                showReadUserInfoInUI();
                            }

                            @Override
                            public void onDatabaseOperationSuccess() {
                                // not required
                            }

                            @Override
                            public void onDatabaseOperationFailed(String message) {

                                showToast(getString(R.string.failed_to_connect));

                                Log.d(TAG, "onDatabaseOperationFailed: user data read error -> "+message);
                            }
                        }

                );
        mReadUserInfoFirebaseRDBSingleOperation.read();
    }

    /*
      Show read user info from database to UI
     */
    private void showReadUserInfoInUI() {

        mUsernameEditText.setText(mUser.getmUsername());
        mDateOfBirhtEditText.setText(mUser.getmDateOfBirth());
        mAddressEditText.setText(mUser.getmAddress());
        mPhoneNumberEditText.setText(mUser.getmPhoneNumber());
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