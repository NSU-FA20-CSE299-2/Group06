package com.nsu.group06.cse299.sec02.helpmeapp.fetchLocation.fusedLocationApi;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Looper;
import android.util.Log;

import androidx.core.app.ActivityCompat;

import com.google.android.gms.common.api.ResolvableApiException;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationAvailability;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResponse;
import com.google.android.gms.location.SettingsClient;
import com.google.android.gms.tasks.Task;
import com.nsu.group06.cse299.sec02.helpmeapp.fetchLocation.FetchedLocation;
import com.nsu.group06.cse299.sec02.helpmeapp.fetchLocation.LocationFetcher;

/**
 * Api adapter for fused locations api
 */
public class FusedLocationFetcherApiAdapter extends LocationFetcher {

    private static final String TAG = "FLFAA-debug";

    // location access priority
    public static int PRIORITY_LEVEL = LocationRequest.PRIORITY_HIGH_ACCURACY;

    private FusedLocationProviderClient mFusedLocationProviderClient;
    private LocationRequest mLocationRequest;
    private LocationCallback mLocationCallback = new LocationCallback() {
        @Override
        public void onLocationResult(LocationResult locationResult) {
            if (locationResult == null) {
                return;
            }
            for (Location location : locationResult.getLocations()) {

                FetchedLocation fetchedLocation = new FetchedLocation(
                        location.getLatitude(), location.getLongitude(),
                        location.getAltitude(), location.getAccuracy());

                locationUpdateListener.onNewLocationUpdate(fetchedLocation);
            }
        }

        @Override
        public void onLocationAvailability(LocationAvailability locationAvailability) {

            if(!locationAvailability.isLocationAvailable()) {

                locationUpdateListener.onError("location not available");
                Log.d(TAG, "onLocationAvailability: location not available");
            }

            super.onLocationAvailability(locationAvailability);
        }
    };


    public FusedLocationFetcherApiAdapter(long interval, Context context, LocationSettingsSetupListener locationSettingsSetupListener,
                                          LocationUpdateListener locationUpdateListener) {
        super(interval, context, locationSettingsSetupListener, locationUpdateListener);

        mLocationRequest = LocationRequest.create();
        mLocationRequest.setInterval(super.interval);
        mLocationRequest.setFastestInterval(super.interval / 2);
        mLocationRequest.setPriority(PRIORITY_LEVEL);

        mFusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(context);
    }

    @Override
    public void setupLocationSettings(Activity activity) {

        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder()
                .addLocationRequest(mLocationRequest);

        SettingsClient client = LocationServices.getSettingsClient(activity);
        Task<LocationSettingsResponse> task = client.checkLocationSettings(builder.build());

        task.addOnSuccessListener(activity, locationSettingsResponse -> {
            // All location settings are satisfied. The client can initialize
            // location requests here.

            locationSettingsSetupListener.onLocationSettingsSetupSuccess();
        });

        task.addOnFailureListener(activity, e -> {

            locationSettingsSetupListener.onLocationSettingsSetupFailed("location settings not met");

            if (e instanceof ResolvableApiException) {
                // Location settings are not satisfied, but this can be fixed
                // by showing the user a dialog.
                try {
                    // Show the dialog by calling startResolutionForResult(),
                    // and check the result in onActivityResult().
                    ResolvableApiException resolvable = (ResolvableApiException) e;
                    resolvable.startResolutionForResult(activity,
                            REQUEST_CHECK_LOCATION_SETTINGS);
                } catch (IntentSender.SendIntentException sendEx) {
                    // Ignore the error.
                }
            }
        });
    }

    @Override
    public void startLocationUpdate() {

        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            locationUpdateListener.onPermissionNotGranted();
            return;
        }

        mFusedLocationProviderClient
                .requestLocationUpdates(mLocationRequest, mLocationCallback, Looper.getMainLooper());
    }

    @Override
    public void stopLocationUpdate() {

        mFusedLocationProviderClient
                .removeLocationUpdates(mLocationCallback);
    }
}
