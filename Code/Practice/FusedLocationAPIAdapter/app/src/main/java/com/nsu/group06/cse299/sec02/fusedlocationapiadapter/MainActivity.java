package com.nsu.group06.cse299.sec02.fusedlocationapiadapter;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.DialogInterface;
import android.os.Bundle;
import android.widget.Toast;

import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.DialogOnDeniedPermissionListener;
import com.karumi.dexter.listener.single.PermissionListener;

public class MainActivity extends AppCompatActivity {

    private String LOCATION_PERMISSION = Manifest.permission.ACCESS_FINE_LOCATION;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        init();
    }

    private void init() {

        getLocationPermissions();
    }

    /*
    Ask for location access permission
    using the open source library- <https://github.com/Karumi/Dexter>
     */
    private void getLocationPermissions() {

        Dexter.withContext(this)
                .withPermission(LOCATION_PERMISSION)
                .withListener(new PermissionListener() {
                    @Override
                    public void onPermissionGranted(PermissionGrantedResponse permissionGrantedResponse) {

                        startFetchingLocation();
                    }

                    @Override
                    public void onPermissionDenied(PermissionDeniedResponse permissionDeniedResponse) {

                        if(!permissionDeniedResponse.isPermanentlyDenied()) {

                            showLocationPermissionExplanationDialog();
                        }
                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(PermissionRequest permissionRequest, PermissionToken permissionToken) {
                    // ignore for now
                        permissionToken.continuePermissionRequest();
                    }
                })
                .check();
    }

    /*
    show alert dialog explaining why location permission is required
    with a simple alert dialog
    courtesy - <https://stackoverflow.com/questions/26097513/android-simple-alert-dialog
     */
    private void showLocationPermissionExplanationDialog() {

        AlertDialog alertDialog = new AlertDialog.Builder(MainActivity.this).create();

        // TODO: bring in strings from res/values/strings.xml
        alertDialog.setTitle("Location Permission");
        alertDialog.setMessage("Location permission is required to fetch your current location");
        alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                (dialog, which) -> getLocationPermissions());

        alertDialog.show();
    }

    /*
    Fetch user's current best location
     */
    private void startFetchingLocation(){

        Toast.makeText(this, "fetching location", Toast.LENGTH_LONG).show();
    }
}