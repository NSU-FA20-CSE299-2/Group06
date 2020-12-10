package com.nsu.group06.cse299.sec02.firebasesdk.database.firebase_database;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.nsu.group06.cse299.sec02.firebasesdk.database.Database;

/*
    Firebase Realtime-database SDK integration class
 */
@SuppressWarnings("unchecked")
public class FirebaseRDBRealtime<T> extends Database.RealtimeDatabase<T> {

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

            realtimeChangesDatabaseCallback.onDatabaseOperationFailed("failed to detect realtime changes at = "+mApiEndPoint.getmUrl());
        }
    };

    @Override
    public void listenForDataChange() {

        mApiEndPoint.toApiEndPoint().addChildEventListener(mChildEventListener);
    }


    @Override
    public void stopListeningForDataChange() {

        mApiEndPoint.toApiEndPoint().removeEventListener(mChildEventListener);
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
