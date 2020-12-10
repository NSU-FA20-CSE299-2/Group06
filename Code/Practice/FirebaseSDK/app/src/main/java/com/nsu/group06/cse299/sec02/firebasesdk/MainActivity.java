package com.nsu.group06.cse299.sec02.firebasesdk;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.nsu.group06.cse299.sec02.firebasesdk.database.Database;
import com.nsu.group06.cse299.sec02.firebasesdk.database.firebase_database.FirebaseRDBSingleOperation;
import com.nsu.group06.cse299.sec02.firebasesdk.database.firebase_database.FirebaseRDBApiEndPoint;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MA-debug";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        test();
    }

    /*
    method for testing newly written database classes
     */
    private void test() {

        TextView tv = findViewById(R.id.delete_text);

        Database.SingleOperationDatabase singleOperationDatabase =
                new FirebaseRDBSingleOperation(
                        DummyModel.class,
                        new FirebaseRDBApiEndPoint("/dummyDataSet/data?textData=some other name"),
                        new Database.SingleOperationDatabase.SingleOperationDatabaseCallback<DummyModel>() {
                            @Override
                            public void onDataRead(DummyModel data) {

                                if(data!=null){
                                    tv.setText(data.getTextData());

                                    Log.d(TAG, "onDataRead: data read = "+data.toString());
                                }

                                else{
                                    tv.setText("no data found!");
                                }

                            }

                            @Override
                            public void onDatabaseOperationFailed(String message) {

                                Log.d(TAG, "onDatabaseOperationFailed: "+message);
                            }
                        }
                );

        singleOperationDatabase.read();
    }
}