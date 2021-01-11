package com.nsu.group06.cse299.sec02.helpmeapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;

public class SingleHelpPostActivity extends AppCompatActivity {

    private static final String TAG = "SHPA-debug";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_help_post);

        handleOpenFromURLIntent(getIntent());

        init();
    }

    private void init() {
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
}