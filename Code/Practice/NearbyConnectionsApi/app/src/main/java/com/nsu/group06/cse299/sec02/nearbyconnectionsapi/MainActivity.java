package com.nsu.group06.cse299.sec02.nearbyconnectionsapi;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.os.Bundle;
import android.util.Log;

import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MA-debug";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();
    }

    private void init() {

        getNearbyPermissions();
    }

    /*
    Ask user for permissions required to user nearby api
    using the open source library- <https://github.com/Karumi/Dexter>
     */
    private void getNearbyPermissions() {

        Dexter.withContext(this)
                .withPermissions(
                        Manifest.permission.BLUETOOTH,
                        Manifest.permission.BLUETOOTH_ADMIN,
                        Manifest.permission.ACCESS_WIFI_STATE,
                        Manifest.permission.CHANGE_WIFI_STATE,
                        Manifest.permission.ACCESS_COARSE_LOCATION,
                        Manifest.permission.ACCESS_FINE_LOCATION
                )
                .withListener(new MultiplePermissionsListener() {
                    @Override
                    public void onPermissionsChecked(MultiplePermissionsReport multiplePermissionsReport) {

                        if(multiplePermissionsReport.areAllPermissionsGranted()){

                            startNearbyConnections();
                        }

                        else{

                            showNearbyPermissionExplanationDialog(multiplePermissionsReport.isAnyPermissionPermanentlyDenied());
                        }
                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(List<PermissionRequest> list, PermissionToken permissionToken) {
                    // ignore for now
                        permissionToken.continuePermissionRequest();
                    }

                }).check();
    }

    /*
    start advertising device for nearby connections
     */
    private void startNearbyConnections() {
    }

    /*
    show alert dialog explaining why location permission is a MUST
    with a simple dialog, quit activity if permission is permanently denied
    courtesy - <https://stackoverflow.com/questions/26097513/android-simple-alert-dialog
     */
    private void showNearbyPermissionExplanationDialog(boolean isPermissionPermanentlyDenied) {

        AlertDialog alertDialog = new AlertDialog.Builder(MainActivity.this).create();

        // TODO: bring in the strings from res/values/strings.xml
        String title = "Nearby Connections Permission";
        String explanation;

        if(isPermissionPermanentlyDenied)
            explanation = "Please allow required permissions from for the app from your device Settings.";
        else
            explanation = "Location, WiFi and BLE access permission is required to connection with nearby devices, permission MUST be provided!";

        alertDialog.setTitle(title);
        alertDialog.setMessage(explanation);
        alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                (dialog, which) -> {
                    if(!isPermissionPermanentlyDenied)
                        getNearbyPermissions();
                    else
                        finish();
                });

        alertDialog.show();
    }




}