package com.nsu.group06.cse299.sec02.helpmeapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.nsu.group06.cse299.sec02.helpmeapp.models.HelpPost;
import com.nsu.group06.cse299.sec02.helpmeapp.recyclerViewAdapters.EmergencyContactsAdapter;
import com.nsu.group06.cse299.sec02.helpmeapp.recyclerViewAdapters.HelpPostsAdapter;

public class HelpFeedActivity extends AppCompatActivity implements HelpPostsAdapter.CallerCallbacks {

    // ui
    private TextView mNoDataFoundTextView;
    private RecyclerView mHelpPostsRecyclerView;
    private HelpPostsAdapter mHelpPostsAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help_feed);

        init();
    }

    private void init() {

        mNoDataFoundTextView = findViewById(R.id.helpFeed_empty_TextView);
        mHelpPostsRecyclerView = findViewById(R.id.helpPosts_RecyclerView);
        mHelpPostsAdapter = new HelpPostsAdapter(this, this);
        mHelpPostsRecyclerView.setAdapter(mHelpPostsAdapter);
        mHelpPostsRecyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override
    public void onFailedToLoadData() {

        Toast.makeText(this, R.string.failed_to_connect, Toast.LENGTH_LONG)
                .show();
    }

    @Override
    public void onShowLocationClick(HelpPost helpPost) {

        Uri geolocation = Uri.parse("geo:0,0?q="+helpPost.getLatitude()+","+helpPost.getLongitude()+"(Distress Location)&z=17");
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(geolocation);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
    }

    @Override
    public void onListNotEmpty() {

        mNoDataFoundTextView.setVisibility(View.GONE);
        mHelpPostsRecyclerView.setVisibility(View.VISIBLE);
    }
}