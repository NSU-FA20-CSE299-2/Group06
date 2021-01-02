package com.nsu.group06.cse299.sec02.helpmeapp.recyclerViewAdapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.nsu.group06.cse299.sec02.helpmeapp.R;
import com.nsu.group06.cse299.sec02.helpmeapp.database.Database;
import com.nsu.group06.cse299.sec02.helpmeapp.database.firebase_database.FirebaseRDBApiEndPoint;
import com.nsu.group06.cse299.sec02.helpmeapp.database.firebase_database.FirebaseRDBRealtime;
import com.nsu.group06.cse299.sec02.helpmeapp.models.EmergencyContact;
import com.nsu.group06.cse299.sec02.helpmeapp.utils.NosqlDatabasePathUtils;

import java.util.ArrayList;

public class EmergencyContactsAdapter extends RecyclerView.Adapter<EmergencyContactsAdapter.ViewHolder> {

    // calling activity/fragment
    private Context mContext;

    // model for this adapter
    private ArrayList<EmergencyContact> mEmergencyContacts;

    // variables used to read existing emergency contacts from database
    private Database.RealtimeDatabase mReadEmergencyContactsRealtimeDatabase;
    private FirebaseRDBApiEndPoint mReadEmergencyContactsApiEndPoint;
    private String mUid;

    public EmergencyContactsAdapter(Context context, String uid) {
        this.mContext = context;
        this.mUid = uid;
        this.mEmergencyContacts = new ArrayList<>();

        loadEmergencyContacts();
    }

    /*
    Read existing emergency contacts from remote database
     */
    private void loadEmergencyContacts() {

        mEmergencyContacts = new ArrayList<>();

        mReadEmergencyContactsApiEndPoint = new FirebaseRDBApiEndPoint(
                "/" + NosqlDatabasePathUtils.EMERGENCY_CONTACTS_NODE
                        + "/" + mUid
                        + "/" + NosqlDatabasePathUtils.EMERGENCY_CONTACTS_PHONE_NODE);

        mReadEmergencyContactsRealtimeDatabase = new FirebaseRDBRealtime<EmergencyContact>(

                EmergencyContact.class,

                mReadEmergencyContactsApiEndPoint,

                new Database.RealtimeDatabase.RealtimeChangesDatabaseCallback<EmergencyContact>() {
                    @Override
                    public void onDataAddition(EmergencyContact data) {

                        mEmergencyContacts.add(data);

                        EmergencyContactsAdapter.this.notifyItemInserted(mEmergencyContacts.size()-1);
                    }

                    @Override
                    public void onDataUpdate(EmergencyContact data) {

                        int updatePosition = -1;
                        for(int i=0; i<mEmergencyContacts.size(); i++) {

                            if (mEmergencyContacts.get(i).getmPhoneNumber().equals(data.getmPhoneNumber())){

                                updatePosition = i;
                                break;
                            }
                        }
                        if(updatePosition==-1) return;

                        mEmergencyContacts.get(updatePosition).setmName(data.getmName());
                        EmergencyContactsAdapter.this.notifyItemChanged(updatePosition);
                    }

                    @Override
                    public void onDataDeletion(EmergencyContact data) {

                        int removePosition = -1;
                        for(int i=0; i<mEmergencyContacts.size(); i++) {

                            if (mEmergencyContacts.get(i).getmPhoneNumber().equals(data.getmPhoneNumber())){

                                removePosition = i;
                                break;
                            }
                        }
                        if(removePosition==-1) return;

                        mEmergencyContacts.remove(removePosition);
                        EmergencyContactsAdapter.this.notifyItemRemoved(removePosition);
                    }

                    @Override
                    public void onDatabaseOperationSuccess() {
                        // not required
                    }

                    @Override
                    public void onDatabaseOperationFailed(String message) {

                        Toast.makeText(mContext, R.string.failed_to_connect, Toast.LENGTH_SHORT)
                                .show();
                    }
                }
        );

        mReadEmergencyContactsRealtimeDatabase.listenForListDataChange();
    }

    /**
     * delete emergency contact from database
     * @param emergencyContact to be deleted
     */
    private void deleteEmergencyContact(EmergencyContact emergencyContact){

        Toast.makeText(mContext, "Deleting "+emergencyContact.getmPhoneNumber(), Toast.LENGTH_SHORT)
                .show();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(mContext)
                .inflate(R.layout.emergency_contact_phonenumber_itemview, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        EmergencyContact emergencyContact = mEmergencyContacts.get(position);
        holder.nameTextView.setText(emergencyContact.getmName());
        holder.phoneNumberTextView.setText(emergencyContact.getmPhoneNumber());

        // delete emergency contact click listener
        holder.deleteButton.setOnClickListener(v -> deleteEmergencyContact(emergencyContact));
    }

    public void onDestroy(){

        // MUST CALL THIS TO AVOID UNNECESSARY DOWNLOAD
        mReadEmergencyContactsRealtimeDatabase.stopListeningForDataChange();
    }

    @Override
    public int getItemCount() {
        return mEmergencyContacts.size();
    }

    // Provide a direct reference to each of the views within a data item
    // Used to cache the views within the item layout for fast access
    public class ViewHolder extends RecyclerView.ViewHolder {
        // Your holder should contain a member variable
        // for any view that will be set as you render a row
        public TextView nameTextView, phoneNumberTextView;
        public ImageButton deleteButton;

        // We also create a constructor that accepts the entire item row
        // and does the view lookups to find each subview
        public ViewHolder(View itemView) {
            // Stores the itemView in a public final member variable that can be used
            // to access the context from any ViewHolder instance.
            super(itemView);

            nameTextView = itemView.findViewById(R.id.emergencyContactName_itemView__TextView);
            phoneNumberTextView = itemView.findViewById(R.id.emergencyContactPhone_itemView__TextView);
            deleteButton = itemView.findViewById(R.id.emergencyContactDelete_itemView_Button);
        }
    }

}
