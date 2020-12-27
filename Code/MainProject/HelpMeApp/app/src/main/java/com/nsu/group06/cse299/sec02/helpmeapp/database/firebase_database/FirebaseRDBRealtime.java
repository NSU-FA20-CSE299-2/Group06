package com.nsu.group06.cse299.sec02.helpmeapp.database.firebase_database;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.nsu.group06.cse299.sec02.helpmeapp.database.Database;

/*
    Firebase Realtime-database SDK integration class
 */
@SuppressWarnings("unchecked")
public class FirebaseRDBRealtime<T> extends Database.RealtimeDatabase {

    private static final String TAG = "FRDBR-debug";

    // needed for turning dataSnapshots to model class's object
    // courtesy-
    // https://stackoverflow.com/questions/3437897/how-do-i-get-a-class-instance-of-generic-type-t
    private final Class<T> mTypeParameterClass;

    private FirebaseRDBApiEndPoint mApiEndPoint;

    public FirebaseRDBRealtime(Class<T> mTypeParameterClass) {

        super();

        this.mTypeParameterClass = mTypeParameterClass;
    }

    public FirebaseRDBRealtime(Class<T> mTypeParameterClass, FirebaseRDBApiEndPoint apiEndPoint,
                               Database.RealtimeDatabase.RealtimeChangesDatabaseCallback<T> databaseCallback) {

        super(databaseCallback);

        this.mTypeParameterClass = mTypeParameterClass;
        this.mApiEndPoint = apiEndPoint;
    }


    // Realtime triggers

    // single data change detection using Firebase Realtime SDK provided class
    private ValueEventListener mValueEventListener = new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull DataSnapshot snapshot) {

            if(!snapshot.exists()){

                Log.d(TAG, "onDataChange: no data exists");

                return;
            }

            for (DataSnapshot dataSnapshot: snapshot.getChildren()){

                T data = dataSnapshot.getValue(FirebaseRDBRealtime.this.mTypeParameterClass);

                if(data!=null) realtimeChangesDatabaseCallback.onDataUpdate(data);
            }
        }

        @Override
        public void onCancelled(@NonNull DatabaseError error) {

            realtimeChangesDatabaseCallback.onDatabaseOperationFailed("failed to detect realtime changes at = "+mApiEndPoint.toApiEndPoint() + "error =" +error.getMessage());
        }
    };

    @Override
    public void listenForSingleDataChange() {

        mApiEndPoint.toApiEndPoint().addValueEventListener(mValueEventListener);
    }

    // list data change detection using Firebase Realtime SDK provided class
    private ChildEventListener mChildEventListener = new ChildEventListener() {
        @Override
        public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            realtimeChangesDatabaseCallback.onDataAddition(
                    snapshot.getValue(FirebaseRDBRealtime.this.mTypeParameterClass)
            );
        }

        @Override
        public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            realtimeChangesDatabaseCallback.onDataUpdate(
                    snapshot.getValue(FirebaseRDBRealtime.this.mTypeParameterClass)
            );
        }

        @Override
        public void onChildRemoved(@NonNull DataSnapshot snapshot) {

            realtimeChangesDatabaseCallback.onDataDeletion(
                    snapshot.getValue(FirebaseRDBRealtime.this.mTypeParameterClass)
            );
        }

        @Override
        public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
            // ignore for now
        }

        @Override
        public void onCancelled(@NonNull DatabaseError error) {

            realtimeChangesDatabaseCallback.onDatabaseOperationFailed("failed to detect realtime changes at = "+mApiEndPoint.getmUrl() + "error =" +error.getMessage());
        }
    };

    @Override
    public void listenForListDataChange() {

        mApiEndPoint.toApiEndPoint().addChildEventListener(mChildEventListener);
    }


    @Override
    public void stopListeningForDataChange() {

        mApiEndPoint.toApiEndPoint().removeEventListener(mChildEventListener);
        mApiEndPoint.toApiEndPoint().removeEventListener(mValueEventListener);
    }

    public Class<T> getmTypeParameterClass() {
        return mTypeParameterClass;
    }

    public FirebaseRDBApiEndPoint getmApiEndPoint() {
        return mApiEndPoint;
    }

    public void setmApiEndPoint(FirebaseRDBApiEndPoint mApiEndPoint) {
        this.mApiEndPoint = mApiEndPoint;
    }

}
