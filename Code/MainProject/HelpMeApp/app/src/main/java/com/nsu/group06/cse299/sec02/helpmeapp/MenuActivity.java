package com.nsu.group06.cse299.sec02.helpmeapp;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.view.View;

import com.google.android.material.snackbar.Snackbar;
import com.nsu.group06.cse299.sec02.helpmeapp.auth.FirebaseEmailPasswordAuthentication;
import com.nsu.group06.cse299.sec02.helpmeapp.broadcastReceivers.NetworkConnectionBroadcastReceiver;
import com.nsu.group06.cse299.sec02.helpmeapp.utils.SessionUtils;

/**
 * App home page, which is a menu
 */
public class MenuActivity extends AppCompatActivity implements NetworkConnectionBroadcastReceiver.InternetStatusCallback {

    private NetworkConnectionBroadcastReceiver mNetworkConnBroadcastReceiver = null;

    private Snackbar mSnackbar;

    private boolean mIsInternetAvailable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        init();
    }

    private void init() {

        mIsInternetAvailable = true;

        View view = findViewById(R.id.menu_main_layout);
        mSnackbar = Snackbar.make(view, R.string.internet_connection_lost, Snackbar.LENGTH_INDEFINITE);
        mSnackbar.setAction("dismiss", v -> mSnackbar.dismiss());

        mNetworkConnBroadcastReceiver =
                new NetworkConnectionBroadcastReceiver(this);
        // register the broadcast receiver
        registerReceiver(mNetworkConnBroadcastReceiver, new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION));
    }

    @Override
    protected void onResume() {
        super.onResume();

        if(mNetworkConnBroadcastReceiver!=null) {
            // register the broadcast receiver
            registerReceiver(mNetworkConnBroadcastReceiver, new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION));
        }

    }

    @Override
    protected void onStop() {

        if(mNetworkConnBroadcastReceiver!=null) unregisterReceiver(mNetworkConnBroadcastReceiver);

        super.onStop();
    }

    /**
     * Menu option click listeners
     */

    public void emergencyContactsClick(View view) {

        if(!mIsInternetAvailable){
            showNoInternetDialog();
            return;
        }

        startActivity(new Intent(this, EmergencyContactsActivity.class));
    }

    public void setupProfileClick(View view) {

        if(!mIsInternetAvailable){
            showNoInternetDialog();
            return;
        }

        startActivity(new Intent(this, SetupProfileActivity.class));
    }

    public void postHelpClick(View view) {

        if(!mIsInternetAvailable){
            showNoInternetDialog();
            return;
        }

        startActivity(new Intent(this, HelpPostActivity.class));
    }

    public void helpFeedClick(View view) {

        if(!mIsInternetAvailable){
            showNoInternetDialog();
            return;
        }

        startActivity(new Intent(this, HelpFeedActivity.class));
    }

    public void settingsClick(View view) {

        if(!mIsInternetAvailable){
            showNoInternetDialog();
            return;
        }

        startActivity(new Intent(this, SettingsActivity.class));
    }

    private void showNoInternetDialog() {

        AlertDialog alertDialog = new AlertDialog.Builder(MenuActivity.this).create();

        String title = getString(R.string.no_internet);
        String explanation = getString(R.string.please_enable_internet_to_continue);

        alertDialog.setTitle(title);
        alertDialog.setMessage(explanation);
        alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, getString(R.string.ok),
                (dialog, which) -> dialog.dismiss() );

        alertDialog.show();
    }

    @Override
    public void onInternetAvailable() {

        mSnackbar.dismiss();
        mIsInternetAvailable = true;
    }

    @Override
    public void onInternetNotAvailable() {

        mSnackbar.show();
        mIsInternetAvailable = false;
    }
}