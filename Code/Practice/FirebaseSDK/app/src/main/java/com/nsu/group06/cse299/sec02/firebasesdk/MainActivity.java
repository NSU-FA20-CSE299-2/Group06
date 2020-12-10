package com.nsu.group06.cse299.sec02.firebasesdk;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.nsu.group06.cse299.sec02.firebasesdk.auth.Authentication;
import com.nsu.group06.cse299.sec02.firebasesdk.auth.AuthenticationUser;
import com.nsu.group06.cse299.sec02.firebasesdk.auth.EmailPasswordAuthUser;
import com.nsu.group06.cse299.sec02.firebasesdk.auth.FirebaseEmailPasswordAuthentication;
import com.nsu.group06.cse299.sec02.firebasesdk.database.Database;
import com.nsu.group06.cse299.sec02.firebasesdk.database.firebase_database.FirebaseRDBRealtime;
import com.nsu.group06.cse299.sec02.firebasesdk.database.firebase_database.FirebaseRDBSingleOperation;
import com.nsu.group06.cse299.sec02.firebasesdk.database.firebase_database.FirebaseRDBApiEndPoint;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MA-debug";

    private TextView mTv;

    // database access variables
    private Database.SingleOperationDatabase<DummyModel> mSingleOperationDatabase;
    private Database.RealtimeDatabase<DummyModel> mRealtimeDatabase;

    // authentication variables
    private Authentication mAuthentication;
    private EmailPasswordAuthUser mEmailPasswordAuthUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();
    }

    private void init() {


        mTv = findViewById(R.id.delete_text);

        //testFirebaseRealtimeDatabase();

        //testFirebaseAuth();
    }

    /*
    methods for testing authentication classes
     */
    private void testFirebaseAuth() {

        //testSignUp();
        //testSignIn();
    }

    private void testSignUp() {

        mEmailPasswordAuthUser = new EmailPasswordAuthUser("testmail@mail.co", "Abc12345!");

        mAuthentication =
                new FirebaseEmailPasswordAuthentication(

                        new Authentication.RegisterUserAuthenticationCallbacks() {
                            @Override
                            public void onRegistrationSuccess(AuthenticationUser user) {

                                Toast.makeText(MainActivity.this, "user = "+user.getmUid()+" signed up!", Toast.LENGTH_SHORT).show();
                            }

                            @Override
                            public void onRegistrationFailure(String message) {

                                Toast.makeText(MainActivity.this, "user sign up failed", Toast.LENGTH_SHORT).show();
                                Log.d(TAG, "onRegistrationFailure: "+message);
                            }
                        },

                        mEmailPasswordAuthUser
                );

        mAuthentication.registerUserAuthentication();
    }

    private void testSignIn() {

        mEmailPasswordAuthUser = new EmailPasswordAuthUser("testmail@mail.co", "Abc12345!");

        mAuthentication =
                new FirebaseEmailPasswordAuthentication(

                        new Authentication.AuthenticationCallbacks() {
                            @Override
                            public void onAuthenticationSuccess(AuthenticationUser user) {

                                Toast.makeText(MainActivity.this, "user = "+user.getmUid()+" logged in.", Toast.LENGTH_SHORT).show();
                            }

                            @Override
                            public void onAuthenticationFailure(String message) {

                                Toast.makeText(MainActivity.this, "user login failed", Toast.LENGTH_SHORT).show();
                                Log.d(TAG, "onAuthenticationFailure: "+message);
                            }
                        },

                        mEmailPasswordAuthUser
                );

        mAuthentication.authenticateUser();
    }


    /*
    methods for testing database classes
     */
    private void testFirebaseRealtimeDatabase() {

        //testRead();
        //testWrite();

        //testSingleDataChangeRealtime();
        //testListDataChangeRealtime();
    }

    private void testRead() {

        mSingleOperationDatabase =
                new FirebaseRDBSingleOperation(

                        DummyModel.class,

                        new FirebaseRDBApiEndPoint("/dummyDataSet/data?textData=someename"),

                        new Database.SingleOperationDatabase.SingleOperationDatabaseCallback<DummyModel>() {
                            @Override
                            public void onDataRead(DummyModel data) {

                                if(data!=null){

                                    mTv.setText(data.getTextData());

                                    Log.d(TAG, "onDataRead: data read = "+data.toString());
                                }

                                else{

                                    mTv.setText("no data found!");
                                }

                            }

                            @Override
                            public void onDatabaseOperationFailed(String message) {

                                Log.d(TAG, "onDatabaseOperationFailed: "+message);
                            }
                        }
                );

        mSingleOperationDatabase.read();
    }

    private void testWrite() {

        mSingleOperationDatabase =
                new FirebaseRDBSingleOperation(

                        DummyModel.class,

                        new FirebaseRDBApiEndPoint("/dummyDataSet"),

                        new Database.SingleOperationDatabase.SingleOperationDatabaseCallback<DummyModel>() {
                            @Override
                            public void onDataRead(DummyModel data) {
                                // keep blank not reading anything from here
                            }

                            @Override
                            public void onDatabaseOperationFailed(String message) {

                                Log.d(TAG, "onDatabaseOperationFailed: "+message);
                            }
                        }
                );

        mSingleOperationDatabase.create(new DummyModel("new data"));

    }

    private void testSingleDataChangeRealtime() {

        mRealtimeDatabase =
                new FirebaseRDBRealtime(

                        DummyModel.class,

                        new FirebaseRDBApiEndPoint("/dummyDataSet/data:data-1-id"),

                        new Database.RealtimeDatabase.RealtimeChangesDatabaseCallback<DummyModel>() {
                            @Override
                            public void onDataAddition(DummyModel data) {
                            }

                            @Override
                            public void onDataUpdate(DummyModel data) {

                                mTv.setText(data.getTextData());
                            }

                            @Override
                            public void onDataDeletion(DummyModel data) {
                            }

                            @Override
                            public void onDatabaseOperationFailed(String message) {

                                Log.d(TAG, "onDatabaseOperationFailed: "+message);
                            }
                        }

                );

        mRealtimeDatabase.listenForSingleDataChange();
    }

    private void testListDataChangeRealtime() {

        mRealtimeDatabase =
                new FirebaseRDBRealtime(

                        DummyModel.class,

                        new FirebaseRDBApiEndPoint("/dummyDataSet"),

                        new Database.RealtimeDatabase.RealtimeChangesDatabaseCallback<DummyModel>() {
                            @Override
                            public void onDataAddition(DummyModel data) {

                                Toast.makeText(MainActivity.this, "new data added -> "+data.toString(), Toast.LENGTH_SHORT).show();
                            }

                            @Override
                            public void onDataUpdate(DummyModel data) {

                                Toast.makeText(MainActivity.this, "data updated -> "+data.toString(), Toast.LENGTH_SHORT).show();
                            }

                            @Override
                            public void onDataDeletion(DummyModel data) {

                                Toast.makeText(MainActivity.this, "data deleted -> "+data.toString(), Toast.LENGTH_SHORT).show();
                            }

                            @Override
                            public void onDatabaseOperationFailed(String message) {

                                Log.d(TAG, "onDatabaseOperationFailed: "+message);
                            }
                        }

                );

        mRealtimeDatabase.listenForListDataChange();
    }

    @Override
    protected void onStop() {
        super.onStop();

        if(mAuthentication!=null) mAuthentication.signOut();

        if(mRealtimeDatabase!=null) {
            // clear out any attached listeners
            mRealtimeDatabase.stopListeningForDataChange();
        }
    }
}