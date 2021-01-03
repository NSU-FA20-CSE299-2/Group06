package com.nsu.group06.cse299.sec02.helpmeapp.database.firebase_database;

import android.util.Log;

import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.nsu.group06.cse299.sec02.helpmeapp.database.ApiEndPoint;
import com.nsu.group06.cse299.sec02.helpmeapp.utils.NosqlDatabasePathUtils;

/*
    Convert api endpoint path to firebase realtime database reference node
 */
public class FirebaseRDBApiEndPoint extends ApiEndPoint<Query> {

    private static final String TAG = "FRDBAEP-debug";

    public FirebaseRDBApiEndPoint(String mUrl) {
        super(mUrl);
    }

    /*
    Breaks down urls into firebase realtime database understandable reference (DatabaseReference type)
     */
    @Override
    public Query toApiEndPoint() {

        if(mUrl == null) return null;

        Log.d(TAG, "toApiEndPoint: querying at = "+mUrl);

        Query query = null;
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();

        Log.d(TAG, "toApiEndPoint: endpoint = "+mUrl);

        /*
        TEMPLATE FOR API ENDPOINT USAGE START
         */
        if(mUrl.equals("/dummyDataSet")){
        // first child at node;

            query = firebaseDatabase.getReference().child("dummyDataSet");
        }

        else if(mUrl.startsWith("/dummyDataSet/data:")){
        // child inside node with specific key/id

            String id = mUrl.substring(mUrl.lastIndexOf(":")+1).trim();

            Log.d(TAG, "toApiEndPoint: searching criteria: id == "+id);

            query = firebaseDatabase.getReference("dummyDataSet")
                    .orderByKey().equalTo(id); //databaseReference.child("dummyDataSet/"+id);
        }

        else if(mUrl.startsWith("/dummyDataSet/data?textData=")){
        // child inside node with equal to particular field value

            String equalValue = mUrl.substring(mUrl.lastIndexOf("=")+1).trim();

            Log.d(TAG, "toApiEndPoint: searching criteria: textData == "+equalValue);

            query = firebaseDatabase.getReference("dummyDataSet")
                    .orderByChild("textData").equalTo(equalValue);
        }
        /*
        TEMPLATE FOR API ENDPOINT USAGE END
         */

        /*
          endpoint for 'users' node START
         */

        // reference to the whole "users" node
        else if( mUrl.equals("/"+NosqlDatabasePathUtils.USER_NODE) ){

            query = firebaseDatabase.getReference().child(NosqlDatabasePathUtils.USER_NODE);
        }

        // reference to "emergencyContacts/uid/phoneNumbers"
        else if(mUrl.startsWith("/" + NosqlDatabasePathUtils.EMERGENCY_CONTACTS_NODE)
                && mUrl.endsWith("/" + NosqlDatabasePathUtils.EMERGENCY_CONTACTS_PHONE_NODE)){

            query = firebaseDatabase.getReference().child(mUrl.substring(1));
        }

        // reference to "emergencyContacts/uid/phoneNumbers/phoneNumber:"
        else if(mUrl.startsWith("/" + NosqlDatabasePathUtils.EMERGENCY_CONTACTS_NODE)
                && mUrl.substring(mUrl.lastIndexOf('/')+1, mUrl.lastIndexOf('/')+1+11).equals("phoneNumber")){

            String phoneNumber = mUrl.substring(mUrl.lastIndexOf(':')+1);
            String dbPath = mUrl.substring(0, mUrl.lastIndexOf('/')) + "/" + phoneNumber;

            query = firebaseDatabase.getReference().child(dbPath);
        }

        /*
          endpoint for 'users' node END
         */

        else{

            Log.d(TAG, "toApiEndPoint: unknown URL!");
        }
        
        return query;
    }
}
