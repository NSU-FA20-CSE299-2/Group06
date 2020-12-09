package com.nsu.group06.cse299.sec02.firebasesdk.database.firebase_database;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.nsu.group06.cse299.sec02.firebasesdk.database.Database;
import com.nsu.group06.cse299.sec02.firebasesdk.database.ApiEndPoint;

/*
    Firebase Realtime-database SDK integration class
 */
public class FirebaseRealtimeDatabase<T> extends Database<T> {

    // needed for turning dataSnapshots to model class's object
    // courtesy-
    // https://stackoverflow.com/questions/3437897/how-do-i-get-a-class-instance-of-generic-type-t
    final Class<T> typeParameterClass;

    FirebaseRealtimeDatabaseApiEndPoint apiEndPoint;

    public FirebaseRealtimeDatabase(Class<T> typeParameterClass, FirebaseRealtimeDatabaseApiEndPoint apiEndPoint,
                                    DatabaseCallbacks<T> databaseCallbacks) {

        super(databaseCallbacks);

        this.typeParameterClass = typeParameterClass;
        this.apiEndPoint = apiEndPoint;
    }

    @Override
    public void create(T data) {

        apiEndPoint.toApiEndPoint().setValue(data,
                (OnFailureListener) e -> databaseCallbacks.onDatabaseOperationFailed("failed to create entry = " + data.toString()));
    }

    @Override
    public void read() {

        apiEndPoint.toApiEndPoint().addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                databaseCallbacks.onDataRead(
                        snapshot.getValue(FirebaseRealtimeDatabase.this.typeParameterClass)
                );
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

                databaseCallbacks.onDatabaseOperationFailed("failed to read at = "+apiEndPoint.getmUrl());
            }
        });
    }

    @Override
    public void update(T data) {

        apiEndPoint.toApiEndPoint().setValue(data,
                (OnFailureListener) e -> databaseCallbacks.onDatabaseOperationFailed("failed to update entry = " + data.toString()));
    }

    @Override
    public void delete(T data) {

        apiEndPoint.toApiEndPoint().setValue(null,
                (OnFailureListener) e -> databaseCallbacks.onDatabaseOperationFailed("failed to delete entry = " + data.toString()));
    }

    // Realtime triggers
    private ChildEventListener mChildEventListener = new ChildEventListener() {
        @Override
        public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            databaseCallbacks.onDataAddition(
                    snapshot.getValue(FirebaseRealtimeDatabase.this.typeParameterClass)
            );
        }

        @Override
        public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            databaseCallbacks.onDataUpdate(
                    snapshot.getValue(FirebaseRealtimeDatabase.this.typeParameterClass)
            );
        }

        @Override
        public void onChildRemoved(@NonNull DataSnapshot snapshot) {

            databaseCallbacks.onDataDeletion(
                    snapshot.getValue(FirebaseRealtimeDatabase.this.typeParameterClass)
            );
        }

        @Override
        public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
            // ignore for now
        }

        @Override
        public void onCancelled(@NonNull DatabaseError error) {

            databaseCallbacks.onDatabaseOperationFailed("failed to detect realtime changes at = "+apiEndPoint.getmUrl());
        }
    };

    @Override
    public void listenForDataChange() {

        apiEndPoint.toApiEndPoint().addChildEventListener(mChildEventListener);
    }

    @Override
    public void stopListeningForDataChange() {

        apiEndPoint.toApiEndPoint().removeEventListener(mChildEventListener);
    }

}
