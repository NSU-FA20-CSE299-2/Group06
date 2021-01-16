package com.nsu.group06.cse299.sec02.helpmeapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.nsu.group06.cse299.sec02.helpmeapp.auth.FirebaseEmailPasswordAuthentication;
import com.nsu.group06.cse299.sec02.helpmeapp.utils.SessionUtils;

/**
 * App home page, which is a menu
 */
public class MenuActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
    }


    /**
     * Menu option click listeners
     */

    public void emergencyContactsClick(View view) {

        startActivity(new Intent(this, EmergencyContactsActivity.class));
    }

    public void setupProfileClick(View view) {

        startActivity(new Intent(this, SetupProfileActivity.class));
    }

    public void postHelpClick(View view) {

        startActivity(new Intent(this, HelpPostActivity.class));
    }

    public void helpFeedClick(View view) {

        startActivity(new Intent(this, HelpFeedActivity.class));
    }

    public void settingsClick(View view) {

        startActivity(new Intent(this, SettingsActivity.class));
    }
}