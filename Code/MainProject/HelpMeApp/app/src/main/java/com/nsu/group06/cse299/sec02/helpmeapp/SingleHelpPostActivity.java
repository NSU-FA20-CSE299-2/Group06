package com.nsu.group06.cse299.sec02.helpmeapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.nsu.group06.cse299.sec02.helpmeapp.database.Database;
import com.nsu.group06.cse299.sec02.helpmeapp.database.firebase_database.FirebaseRDBApiEndPoint;
import com.nsu.group06.cse299.sec02.helpmeapp.database.firebase_database.FirebaseRDBSingleOperation;
import com.nsu.group06.cse299.sec02.helpmeapp.models.HelpPost;
import com.nsu.group06.cse299.sec02.helpmeapp.utils.NosqlDatabasePathUtils;

/**
 * Activity for showing a single help post
 * this activity is linked to URL received by emergency contacts through SMS
 */
public class SingleHelpPostActivity extends AppCompatActivity {

    private static final String TAG = "SHPA-debug";

    // ui
    private View main_layout;
    private TextView mPhoneNumberTextView, mAddressTextView, mTimeTextView;
    private TextView mContentTextView, mSearchingTextView;
    private ImageView mPhotoImageView;

    // model
    private HelpPost mHelpPost;
    private String mRetrievedPostId;

    // variables to read the help post from database
    private Database.SingleOperationDatabase<HelpPost> mReadHelpPostSingleOperationDatabase;
    private FirebaseRDBApiEndPoint mApiEndPoint;
    private Database.SingleOperationDatabase.SingleOperationDatabaseCallback<HelpPost> mReadHelpPostSingleOperationDatabaseCallback =
            new Database.SingleOperationDatabase.SingleOperationDatabaseCallback<HelpPost>() {
                @Override
                public void onDataRead(HelpPost data) {

                    mHelpPost = data;
                    showHelpPostUI(mHelpPost);
                }

                @Override
                public void onDatabaseOperationSuccess() {
                    // kept blank intentionally
                }

                @Override
                public void onDatabaseOperationFailed(String message) {

                    failedToLoadHelpPostUI();

                    Log.d(TAG, "onDatabaseOperationFailed: help post read error -> "+message);
                }
            };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_help_post);

        handleIntent(getIntent());

        init();
    }

    private void init() {

        main_layout = findViewById(R.id.singleHelpPost_mainLayout);
        mSearchingTextView = findViewById(R.id.searching_singleHelpPost_TextView);
        mPhoneNumberTextView = findViewById(R.id.phoneNumber_singleHelpPost_TextView);
        mAddressTextView = findViewById(R.id.address_singleHelpPost_TextView);
        mTimeTextView = findViewById(R.id.time_singleHelpPost_TextView);
        mContentTextView = findViewById(R.id.content_singleHelpPost_TextView);
        mPhotoImageView = findViewById(R.id.photo_singleHelpPost_ImageView);

    }

    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        handleIntent(intent);
    }

    private void handleIntent(Intent intent) {

        String appLinkAction = intent.getAction();
        Uri appLinkData = intent.getData();

        String pid = "";

        if (Intent.ACTION_VIEW.equals(appLinkAction) && appLinkData != null){

            pid = appLinkData.getQueryParameter("pid");
        }

        mRetrievedPostId = pid;
        loadHelpPost(mRetrievedPostId);
    }

    /**
     * start fetching the help post from database
     * @param postId ID of the post to be shown
     */
    private void loadHelpPost(String postId) {

        mApiEndPoint = new FirebaseRDBApiEndPoint("/"+ NosqlDatabasePathUtils.HELP_POSTS_NODE+"/post:"+postId);

        mReadHelpPostSingleOperationDatabase = new FirebaseRDBSingleOperation<HelpPost>(
                HelpPost.class,
                mApiEndPoint,
                mReadHelpPostSingleOperationDatabaseCallback
        );

        mReadHelpPostSingleOperationDatabase.readSingle();
    }

    /**
     * show help post data to user
     * @param helpPost post model object to be shown
     */
    private void showHelpPostUI(HelpPost helpPost) {

        mSearchingTextView.setVisibility(View.GONE);

        main_layout.setVisibility(View.VISIBLE);

        mPhoneNumberTextView.setText(helpPost.getAuthorPhoneNumber());

        if( helpPost.getAddress() !=null && !helpPost.getAddress().isEmpty()) mAddressTextView.setText(helpPost.getAddress());
        else mAddressTextView.setText(R.string.no_address);

        mTimeTextView.setText(mHelpPost.getTimeStamp());

        mContentTextView.setText(mHelpPost.getContent());

    }

    /*
    "Contact" button click listener
     */
    public void contactClick(View view) {
    }

    /*
    "Show Location" button click listener
     */
    public void showLocationClick(View view) {
    }

    private void failedToLoadHelpPostUI() {

        mSearchingTextView.setText(R.string.failed_to_load_post);
    }

    public void retryClick(View view) {

        loadHelpPost(mRetrievedPostId);
    }
}