package com.nsu.group06.cse299.sec02.helpmeapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_help_post);

        handleOpenFromURLIntent(getIntent());

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
        handleOpenFromURLIntent(intent);
    }

    private void handleOpenFromURLIntent(Intent intent) {

        String appLinkAction = intent.getAction();

        Uri appLinkData = intent.getData();

        if (Intent.ACTION_VIEW.equals(appLinkAction) && appLinkData != null){

            /*
            String recipeId = appLinkData.getLastPathSegment();
            Uri appData = Uri.parse("content://com.recipe_app/recipe/").buildUpon()
                    .appendPath(recipeId).build();
            */

            showHelpPost(appLinkData);
        }
    }

    private void showHelpPost(Uri appData) {

        Log.d(TAG, "showHelpPost: pid = "+appData.getQueryParameter("pid"));
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
}