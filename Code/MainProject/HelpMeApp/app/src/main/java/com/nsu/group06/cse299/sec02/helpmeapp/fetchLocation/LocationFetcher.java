package com.nsu.group06.cse299.sec02.helpmeapp.fetchLocation;

import android.app.Activity;
import android.content.Context;

/*
Abstraction for location fetching
 */
public abstract class LocationFetcher {

    // request code for showing dialog when location request fails
    public static final int REQUEST_CHECK_SETTINGS = 694;

    // location update intervals in milliseconds
    protected long interval;

    // calling activity/context
    protected Context context;

    protected LocationSettingsSetupListener locationSettingsSetupListener;
    protected LocationUpdateListener locationUpdateListener;

    public LocationFetcher(long interval, Context context, LocationSettingsSetupListener locationSettingsSetupListener,
                           LocationUpdateListener locationUpdateListener) {
        this.interval = interval;
        this.context = context;
        this.locationSettingsSetupListener = locationSettingsSetupListener;
        this.locationUpdateListener = locationUpdateListener;
    }

    // activity on top of which location settings setup needs to be made
    abstract public void setupLocationSettings(Activity activity);
    abstract public void startLocationUpdate();
    abstract public void stopLocationUpdate();

    public long getInterval() {
        return interval;
    }

    public void setInterval(long interval) {
        this.interval = interval;
    }

    public LocationSettingsSetupListener getLocationSettingsSetupListener() {
        return locationSettingsSetupListener;
    }

    public void setLocationSettingsSetupListener(LocationSettingsSetupListener locationSettingsSetupListener) {
        this.locationSettingsSetupListener = locationSettingsSetupListener;
    }

    public LocationUpdateListener getLocationUpdateListener() {
        return locationUpdateListener;
    }

    public void setLocationUpdateListener(LocationUpdateListener locationUpdateListener) {
        this.locationUpdateListener = locationUpdateListener;
    }

    public interface LocationSettingsSetupListener{

        void onLocationSettingsSetupSuccess();
        void onLocationSettingsSetupFailed(String message);
    }

    public interface LocationUpdateListener{

        void onNewLocationUpdate(FetchedLocation fetchedLocation);

        void onPermissionNotGranted();

        void onError(String message);
    }
}
