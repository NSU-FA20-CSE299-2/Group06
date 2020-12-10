package com.nsu.group06.cse299.sec02.firebasesdk;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.nsu.group06.cse299.sec02.firebasesdk.database.Database;
import com.nsu.group06.cse299.sec02.firebasesdk.database.firebase_database.FirebaseRDBRealtime;
import com.nsu.group06.cse299.sec02.firebasesdk.database.firebase_database.FirebaseRDBSingleOperation;
import com.nsu.group06.cse299.sec02.firebasesdk.database.firebase_database.FirebaseRDBApiEndPoint;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MA-debug";

    private TextView mTv;

    private Database.SingleOperationDatabase<DummyModel> mSingleOperationDatabase;
    private Database.RealtimeDatabase<DummyModel> mRealtimeDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();
    }

    private void init() {


        mTv = findViewById(R.id.delete_text);

        //testRead();
        //testWrite();

        testReadRealtime();
    }


    /*
    methods for testing newly written database classes
     */

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

    private void testReadRealtime() {

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

    @Override
    protected void onStop() {
        super.onStop();

        mRealtimeDatabase.stopListeningForDataChange();
    }
}