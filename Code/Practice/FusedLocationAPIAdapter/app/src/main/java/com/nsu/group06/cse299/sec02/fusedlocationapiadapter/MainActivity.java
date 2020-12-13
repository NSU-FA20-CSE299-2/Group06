package com.nsu.group06.cse299.sec02.fusedlocationapiadapter;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;
import com.nsu.group06.cse299.sec02.fusedlocationapiadapter.fetchLocation.FetchedLocation;
import com.nsu.group06.cse299.sec02.fusedlocationapiadapter.fetchLocation.FusedLocationFetcherApiAdapter;
import com.nsu.group06.cse299.sec02.fusedlocationapiadapter.fetchLocation.LocationFetcher;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MA-debug";

    private String LOCATION_PERMISSION = Manifest.permission.ACCESS_FINE_LOCATION;

    // ui elements
    private TextView mLocationTextView = null;

    // model
    private FetchedLocation mFetchedLocation = null;

    // location fetchers
    private LocationFetcher mLocationFetcher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        init();
    }

    private void init() {

        mLocationTextView = findViewById(R.id.locationTextView);

        mLocationFetcher =
                new FusedLocationFetcherApiAdapter(1000, this,

                        new LocationFetcher.LocationSettingsSetupListener() {
                            @Override
                            public void onLocationSettingsSetupSuccess() {

                                mLocationFetcher.startLocationUpdate();
                            }

                            @Override
                            public void onLocationSettingsSetupFailed(String message) {

                                Log.d(TAG, "onLocationSettingsSetupFailed: location settings setup failed ->" + message);
                            }
                        },

                        new LocationFetcher.LocationUpdateListener() {
                            @Override
                            public void onNewLocationUpdate(FetchedLocation fetchedLocation) {

                                boolean locationDataUpdated = false;

                                if(mFetchedLocation==null) {
                                    mFetchedLocation = fetchedLocation;
                                    locationDataUpdated = true;
                                }
                                else if(fetchedLocation.getmAccuracy() < mFetchedLocation.getmAccuracy()
                                        || FetchedLocation.isLocationSignificantlyDifferent(mFetchedLocation, fetchedLocation)) {

                                    mFetchedLocation = fetchedLocation;
                                    locationDataUpdated = true;
                                }

                                if(locationDataUpdated)
                                    mLocationTextView.setText(mFetchedLocation.toString());
                            }

                            @Override
                            public void onPermissionNotGranted() {

                                getLocationPermissions();
                            }

                            @Override
                            public void onError(String message) {

                                Toast.makeText(MainActivity.this, "An unexpected error occured", Toast.LENGTH_LONG).show();
                                Log.d(TAG, "onError: location update error -> "+message);
                            }
                        });

        getLocationPermissions();
    }

    @Override
    protected void onStop() {
        super.onStop();

        if(mLocationFetcher!=null)
            mLocationFetcher.stopLocationUpdate();
    }

    /*
    Required for reacting to user response when setting up required location settings
    user response to default "turn on location" dialog is handled through this method
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        switch (requestCode){

            case LocationFetcher.REQUEST_CHECK_SETTINGS:

                if(resultCode==RESULT_OK){
                // user enabled location settings

                    mLocationFetcher.startLocationUpdate();
                }

                else{
                // location settings not met

                    showLocationSettingsExplanationDialog();
                }
                break;
        }

        super.onActivityResult(requestCode, resultCode, data);
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

                        mLocationFetcher.setupLocationSettings(MainActivity.this);
                    }

                    @Override
                    public void onPermissionDenied(PermissionDeniedResponse permissionDeniedResponse) {

                        showLocationPermissionExplanationDialog(permissionDeniedResponse.isPermanentlyDenied());
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
    show alert dialog explaining why location permission is a MUST
    with a simple dialog, quit activity if permission is permanently denied
    courtesy - <https://stackoverflow.com/questions/26097513/android-simple-alert-dialog
     */
    private void showLocationPermissionExplanationDialog(boolean isPermissionPermanentlyDenied) {

        AlertDialog alertDialog = new AlertDialog.Builder(MainActivity.this).create();

        // TODO: bring in the strings from res/values/strings.xml
        String title = "Location Permission";
        String explanation;

        if(isPermissionPermanentlyDenied)
            explanation = "Please allow location from for the app from your device Settings.";
        else
            explanation = "Location permission is required to fetch your current location, permission MUST be provided!";

        alertDialog.setTitle(title);
        alertDialog.setMessage(explanation);
        alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                (dialog, which) -> {
                    if(!isPermissionPermanentlyDenied)
                        getLocationPermissions();
                    else
                        finish();
                });

        alertDialog.show();
    }

    /*
    show alert dialog explaining why location settings MUST be enabled
    courtesy - <https://stackoverflow.com/questions/26097513/android-simple-alert-dialog
     */
    private void showLocationSettingsExplanationDialog() {

        AlertDialog alertDialog = new AlertDialog.Builder(MainActivity.this).create();

        // TODO: bring in the strings from res/values/strings.xml
        String title = "Location Settings";
        String explanation = "We need location enabled to get your accurate location";

        alertDialog.setTitle(title);
        alertDialog.setMessage(explanation);
        alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                (dialog, which) -> mLocationFetcher.setupLocationSettings(MainActivity.this));

        alertDialog.show();
    }

}