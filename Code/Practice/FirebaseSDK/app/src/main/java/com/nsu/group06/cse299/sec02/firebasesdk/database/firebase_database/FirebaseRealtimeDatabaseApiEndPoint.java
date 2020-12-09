package com.nsu.group06.cse299.sec02.firebasesdk.database.firebase_database;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.nsu.group06.cse299.sec02.firebasesdk.database.ApiEndPoint;

/*
    Convert api endpoint path to firebase realtime database reference node
 */
public class FirebaseRealtimeDatabaseApiEndPoint extends ApiEndPoint<DatabaseReference> {


    private static final String TAG = "FRDBAEP-debug";

    public FirebaseRealtimeDatabaseApiEndPoint(String mUrl) {
        super(mUrl);
    }

    /*
    Breaks down urls into firebase realtime database understandable reference (DatabaseReference type)
     */
    @Override
    public DatabaseReference toApiEndPoint() {

        if(mUrl == null) return null;

        Query query = FirebaseDatabase.getInstance().getReference();

        if(mUrl.equals("/dummyDataset")){

            query = query.getRef().child("/dummyDataset");

        } else if(mUrl.startsWith("/dummyDataset/data?text=")){

            String equalValue = mUrl.substring(mUrl.lastIndexOf("="));
            query = query.getRef().child("dummyDataset").orderByChild("text").equalTo(equalValue);
        }

        return query.getRef();
    }
}
