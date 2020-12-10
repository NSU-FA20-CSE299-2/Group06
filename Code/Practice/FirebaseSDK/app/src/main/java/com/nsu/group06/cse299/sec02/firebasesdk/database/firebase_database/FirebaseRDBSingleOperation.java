package com.nsu.group06.cse299.sec02.firebasesdk.database.firebase_database;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.nsu.group06.cse299.sec02.firebasesdk.DummyModel;
import com.nsu.group06.cse299.sec02.firebasesdk.database.Database;

public class FirebaseRDBSingleOperation<T> extends Database.SingleOperationDatabase<T> {

    private static final String TAG = "FRDBSO-debug";

    // needed for turning dataSnapshots to model class's object
    // courtesy-
    // https://stackoverflow.com/questions/3437897/how-do-i-get-a-class-instance-of-generic-type-t
    private final Class<T> typeParameterClass;

    private  FirebaseRDBApiEndPoint apiEndPoint;

    public FirebaseRDBSingleOperation(Class<T> typeParameterClass, FirebaseRDBApiEndPoint apiEndPoint,
                                      Database.SingleOperationDatabase.SingleOperationDatabaseCallback singleOperationDatabaseCallback) {

        super(singleOperationDatabaseCallback);

        this.typeParameterClass = typeParameterClass;
        this.apiEndPoint = apiEndPoint;
    }

    @Override
    public void create(T data) {

        apiEndPoint.toApiEndPoint().setValue(data,
                (OnFailureListener) e -> singleOperationDatabaseCallback.onDatabaseOperationFailed("failed to create entry = " + data.toString()));
    }

    @Override
    public void read() {

        apiEndPoint.toApiEndPoint().addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                if(!snapshot.exists()){
                    Log.d(TAG, "onDataChange: data doesn't exist");
                    singleOperationDatabaseCallback.onDataRead(null);
                }

                for(DataSnapshot dataSnapshot: snapshot.getChildren()){

                    T t = dataSnapshot.getValue(FirebaseRDBSingleOperation.this.typeParameterClass);

                    if(t!=null){

                        Log.d(TAG, "onDataChange: "+t.toString());

                        singleOperationDatabaseCallback.onDataRead(
                                dataSnapshot.getValue(FirebaseRDBSingleOperation.this.typeParameterClass)
                        );
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

                singleOperationDatabaseCallback.onDatabaseOperationFailed("failed to read at = "+apiEndPoint.getmUrl());
            }
        });
    }

    @Override
    public void update(T data) {

        apiEndPoint.toApiEndPoint().setValue(data,
                (OnFailureListener) e -> singleOperationDatabaseCallback.onDatabaseOperationFailed("failed to update entry = " + data.toString()));
    }

    @Override
    public void delete(T data) {

        apiEndPoint.toApiEndPoint().setValue(null,
                (OnFailureListener) e -> singleOperationDatabaseCallback.onDatabaseOperationFailed("failed to delete entry = " + data.toString()));
    }
}
