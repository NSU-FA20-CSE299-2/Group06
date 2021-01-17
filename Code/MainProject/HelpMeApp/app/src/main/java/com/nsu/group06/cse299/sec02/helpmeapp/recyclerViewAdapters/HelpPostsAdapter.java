package com.nsu.group06.cse299.sec02.helpmeapp.recyclerViewAdapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.nsu.group06.cse299.sec02.helpmeapp.R;
import com.nsu.group06.cse299.sec02.helpmeapp.database.Database;
import com.nsu.group06.cse299.sec02.helpmeapp.database.firebase_database.FirebaseRDBApiEndPoint;
import com.nsu.group06.cse299.sec02.helpmeapp.database.firebase_database.FirebaseRDBRealtime;
import com.nsu.group06.cse299.sec02.helpmeapp.models.HelpPost;
import com.nsu.group06.cse299.sec02.helpmeapp.utils.NosqlDatabasePathUtils;

import java.util.ArrayList;

public class HelpPostsAdapter extends RecyclerView.Adapter<HelpPostsAdapter.ViewHolder> {

    private static final String TAG = "HPA-debug";

    // calling activity/fragment
    private Context mContext;

    // calling activity/fragment callbacks
    private CallerCallbacks mCallerCallbacks;

    // model
    private ArrayList<HelpPost> mHelpPosts;

    // flag to check if data list is empty or not
    private boolean mDataListEmpty = true;

    // variables to access database
    private Database.RealtimeDatabase mReadHelpPostsRealtimeDatabase;
    private FirebaseRDBApiEndPoint mApiEndPoint = new FirebaseRDBApiEndPoint("/"+ NosqlDatabasePathUtils.HELP_POSTS_NODE);
    private Database.RealtimeDatabase.RealtimeChangesDatabaseCallback<HelpPost> mHelpPostRealtimeChangesDatabaseCallback =
            new Database.RealtimeDatabase.RealtimeChangesDatabaseCallback<HelpPost>() {
                @Override
                public void onDataAddition(HelpPost data) {

                    // TODO:
                    //  put public and private posts on different nodes
                    //  and remove this client side filtration
                    if(!data.getIsPublic()) return;

                    Log.d(TAG, "onDataAddition: data added -> "+data.toString());

                    // add elements to top
                    mHelpPosts.add(0, data);
                    HelpPostsAdapter.this.notifyItemInserted(0);

                    if(mDataListEmpty){

                        mDataListEmpty = false;
                        mCallerCallbacks.onListNotEmpty();
                    }
                }

                @Override
                public void onDataUpdate(HelpPost data) {

                    int updatePosition = -1;
                    for(int i=0; i<mHelpPosts.size(); i++) {

                        if (mHelpPosts.get(i).getPostId().equals(data.getPostId())){

                            updatePosition = i;
                            break;
                        }
                    }
                    if(updatePosition==-1) return;

                    mHelpPosts.set(updatePosition, data);
                    HelpPostsAdapter.this.notifyItemChanged(updatePosition);
                }

                @Override
                public void onDataDeletion(HelpPost data) {

                    int removePosition = -1;
                    for(int i=0; i<mHelpPosts.size(); i++) {

                        if (mHelpPosts.get(i).getPostId().equals(data.getPostId())){

                            removePosition = i;
                            break;
                        }
                    }
                    if(removePosition==-1) return;

                    mHelpPosts.remove(removePosition);
                    HelpPostsAdapter.this.notifyItemRemoved(removePosition);
                }

                @Override
                public void onDatabaseOperationSuccess() {

                }

                @Override
                public void onDatabaseOperationFailed(String message) {

                    mCallerCallbacks.onFailedToLoadData();
                }
            };

    public HelpPostsAdapter(Context mContext, CallerCallbacks mCallerCallbacks) {
        this.mContext = mContext;
        this.mCallerCallbacks = mCallerCallbacks;
        this.mHelpPosts = new ArrayList<>();

        loadHelpPosts();
    }

    private void loadHelpPosts() {

        mReadHelpPostsRealtimeDatabase = new FirebaseRDBRealtime<HelpPost>(
                HelpPost.class,
                mApiEndPoint,
                mHelpPostRealtimeChangesDatabaseCallback
        );

        mReadHelpPostsRealtimeDatabase.listenForListDataChange();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.help_post_item_view, parent, false);
        return new HelpPostsAdapter.ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        HelpPost helpPost = mHelpPosts.get(position);

        holder.timeTextView.setText(helpPost.getTimeStamp());

        if(helpPost.getAddress()!=null && !helpPost.getAddress().isEmpty()) {
            holder.addressTextView.setText(helpPost.getAddress());
        }
        else holder.addressTextView.setText(R.string.no_address);

        holder.contentTextView.setText(helpPost.getContent());

        if(helpPost.getPhotoURL()!=null && !helpPost.getPhotoURL().isEmpty()){

            holder.photoImageView.setVisibility(View.VISIBLE);
            Glide.with(mContext)
                    .load(helpPost.getPhotoURL())
                    .override(250, 250)
                    .fitCenter() // scale to fit entire image within ImageView
                    .error(R.drawable.ftl_image_placeholder)
                    .into(holder.photoImageView);
        }
        else{
            Glide.with(mContext)
                    .load(R.drawable.no_image_placeholder)
                    .override(250, 250)
                    .fitCenter() // scale to fit entire image within ImageView
                    .into(holder.photoImageView);
        }

        holder.showLocationButton.setOnClickListener(v -> mCallerCallbacks.onShowLocationClick(helpPost));
    }

    public void onDestroy(){

        // MUST CALL THIS TO AVOID UNNECESSARY DOWNLOAD
        mReadHelpPostsRealtimeDatabase.stopListeningForDataChange();
    }

    @Override
    public int getItemCount() {
        return mHelpPosts.size();
    }

    // interface to communicate with the calling Activity/Fragment
    public interface CallerCallbacks{

        void onFailedToLoadData();
        void onShowLocationClick(HelpPost helpPost);
        void onListNotEmpty();
    }

    // Provide a direct reference to each of the views within a data item
    // Used to cache the views within the item layout for fast access
    public class ViewHolder extends RecyclerView.ViewHolder {
        // Your holder should contain a member variable
        // for any view that will be set as you render a row
        public TextView timeTextView, addressTextView, contentTextView;
        public ImageView photoImageView;
        public Button showLocationButton;

        // We also create a constructor that accepts the entire item row
        // and does the view lookups to find each subview
        public ViewHolder(View itemView) {
            // Stores the itemView in a public final member variable that can be used
            // to access the context from any ViewHolder instance.
            super(itemView);

            timeTextView = itemView.findViewById(R.id.time_helpPostItemView_TextView);
            addressTextView = itemView.findViewById(R.id.address_helpPostItemView_TextView);
            contentTextView = itemView.findViewById(R.id.content_helpPostItemView_TextView);
            photoImageView = itemView.findViewById(R.id.photo_helpPostItemView_ImageView);
            showLocationButton = itemView.findViewById(R.id.showLocation_helpPostItemView_Button);
        }
    }
}
