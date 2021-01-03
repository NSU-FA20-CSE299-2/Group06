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
import com.nsu.group06.cse299.sec02.helpmeapp.utils.UserInputValidator;

public class SetupProfileActivity extends AppCompatActivity {

    private static final String TAG = "SPA-debug";

    // ui
    private EditText mUsernameEditText, mDateOfBirhtEditText, mAddressEditText, mPhoneNumberEditText;

    // model
    private User mUser;


    // variable used for fetching user uid
    private Authentication mAuth;
    private Authentication.AuthenticationCallbacks mAuthenticationCallbacks = new Authentication.AuthenticationCallbacks() {
        @Override
        public void onAuthenticationSuccess(AuthenticationUser user) {

            mUser.setmUid(user.getmUid());

            // setup database variables
            initDatabaseVars();

            loadUserProfileInformation();
        }

        @Override
        public void onAuthenticationFailure(String message) {

            SessionUtils.doHardLogout(SetupProfileActivity.this, mAuth);
        }
    };


    // variables to read/write information of users to/from the database
    private Database.SingleOperationDatabase<User> mUserInfoFirebaseRDBSingleOperation;
    private Database.SingleOperationDatabase.SingleOperationDatabaseCallback<User> mUserInfoSingleOperationDatabaseCallback =
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
            };

    private FirebaseRDBApiEndPoint mUserInfoApiEndPoint;

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
        mAuth = new FirebaseEmailPasswordAuthentication(mAuthenticationCallbacks);
        mAuth.authenticateUser();
    }

    /*
    Initialize database variables
     */
    private void initDatabaseVars() {

        mUserInfoApiEndPoint = new FirebaseRDBApiEndPoint(
                "/"+ NosqlDatabasePathUtils.USER_NODE +
                        ":" + mUser.getmUid());

        mUserInfoFirebaseRDBSingleOperation =
                new FirebaseRDBSingleOperation(User.class, mUserInfoApiEndPoint, mUserInfoSingleOperationDatabaseCallback);
    }

    /*
     Download user profile information from the database
     */
    private void loadUserProfileInformation() {

        mUserInfoFirebaseRDBSingleOperation.readSingle();
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

        if(validateInputs()==false) return;

        //TODO: update database
        mUserInfoFirebaseRDBSingleOperation.update(mUser);
    }

    /*
    Validate user input
     */
    private boolean validateInputs() {

        String name = mUsernameEditText.getText().toString();
        String dateOfBirth = mDateOfBirhtEditText.getText().toString();
        String address = mAddressEditText.getText().toString();
        String phoneNumber = mPhoneNumberEditText.getText().toString();
        if(phoneNumber.charAt(0)=='0') phoneNumber = "+88" + phoneNumber;

        if(!UserInputValidator.isNameValid(name)) mUsernameEditText.setError(getString(R.string.invalid_username));
        if(!UserInputValidator.isDateOfBirthValid(dateOfBirth)) mDateOfBirhtEditText.setError(getString(R.string.invalid_date_of_birth));
        if(!UserInputValidator.isAddressValid(address)) mAddressEditText.setError(getString(R.string.invalid_address));
        if(!UserInputValidator.isPhoneNumberValid(phoneNumber)) mPhoneNumberEditText.setError(getString(R.string.invalid_phone_number));

        else{

            if(!mUser.getmUsername().equals(name)
                    || !mUser.getmDateOfBirth().equals(dateOfBirth)
                    || !mUser.getmAddress().equals(address)
                    || !mUser.getmPhoneNumber().equals(phoneNumber)
            ){

                mUser.setmUsername(name);
                mUser.setmDateOfBirth(dateOfBirth);
                mUser.setmAddress(address);
                mUser.setmPhoneNumber(phoneNumber);

                return true;
            }
        }

        return false;
    }

    private void showToast(String message){

        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}