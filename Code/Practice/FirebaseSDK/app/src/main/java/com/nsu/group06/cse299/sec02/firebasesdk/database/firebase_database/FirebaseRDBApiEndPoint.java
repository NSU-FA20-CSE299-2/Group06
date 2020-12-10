package com.nsu.group06.cse299.sec02.firebasesdk.database.firebase_database;

import android.util.Log;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.nsu.group06.cse299.sec02.firebasesdk.database.ApiEndPoint;

/*
    Convert api endpoint path to firebase realtime database reference node
 */
public class FirebaseRDBApiEndPoint extends ApiEndPoint<DatabaseReference> {

    private static final String TAG = "FRDBAEP-debug";

    public FirebaseRDBApiEndPoint(String mUrl) {
        super(mUrl);
    }

    /*
    Breaks down urls into firebase realtime database understandable reference (DatabaseReference type)
     */
    @Override
    public DatabaseReference toApiEndPoint() {

        if(mUrl == null) return null;

        Log.d(TAG, "toApiEndPoint: querying at = "+mUrl);

        Query query = null;
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();

        if(mUrl.equals("/dummyDataSet")){

            query = databaseReference.child("dummyDataSet");

        }

        else if(mUrl.startsWith("/dummyDataSet/data:")){

            String id = mUrl.substring(mUrl.lastIndexOf(":")+1).trim();

            Log.d(TAG, "toApiEndPoint: searching criteria: id == "+id);

            query = databaseReference.child("dummyDataSet/"+id);

        }

        // TODO: not working as expected, fetched all data does not filter by textData field
        else if(mUrl.startsWith("/dummyDataSet/data?textData=")){

            String equalValue = mUrl.substring(mUrl.lastIndexOf("=")+1).trim();

            Log.d(TAG, "toApiEndPoint: searching criteria: textData == "+equalValue);

            query = databaseReference.child("dummyDataSet").orderByChild("textData").equalTo(equalValue);
        }

        return query.getRef();
    }
}
