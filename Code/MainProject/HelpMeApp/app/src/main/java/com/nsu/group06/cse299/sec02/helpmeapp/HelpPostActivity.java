package com.nsu.group06.cse299.sec02.helpmeapp;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import android.Manifest;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;
import com.nsu.group06.cse299.sec02.helpmeapp.auth.Authentication;
import com.nsu.group06.cse299.sec02.helpmeapp.auth.AuthenticationUser;
import com.nsu.group06.cse299.sec02.helpmeapp.auth.FirebaseEmailPasswordAuthentication;
import com.nsu.group06.cse299.sec02.helpmeapp.fetchLocation.FetchedLocation;
import com.nsu.group06.cse299.sec02.helpmeapp.fetchLocation.LocationFetcher;
import com.nsu.group06.cse299.sec02.helpmeapp.fetchLocation.fusedLocationApi.FusedLocationFetcherApiAdapter;
import com.nsu.group06.cse299.sec02.helpmeapp.imageUpload.CapturedImage;
import com.nsu.group06.cse299.sec02.helpmeapp.models.HelpPost;
import com.nsu.group06.cse299.sec02.helpmeapp.utils.SessionUtils;

import java.io.File;
import java.io.IOException;

public class HelpPostActivity extends AppCompatActivity {

    private static final String TAG = "HPA-debug";

    // request code for camera intent
    private static final int REQUEST_IMAGE_CAPTURE = 935;

    // auth to access taken image file
    private static final String FILE_PROVIDER_AUTHORITY = "com.nsu.group06.cse299.sec02.helpmeapp.fileprovider";

    // ui
    private ImageView mCapturedImageView;
    private Button mTakeImageButton, mFetchLocationButton, mPostButton;

    // model
    private CapturedImage mCapturedImage;
    private boolean mImageWasCaptured = false; // necessary for when user presses take photo but doesn't take a photo
    private FetchedLocation mFetchedLocation;
    private HelpPost mHelpPost;

    // variables used for fetching user uid
    private Authentication mAuth;
    private Authentication.AuthenticationCallbacks mAuthCallbacks =
            new Authentication.AuthenticationCallbacks() {
                @Override
                public void onAuthenticationSuccess(AuthenticationUser user) {

                    mHelpPost.setmAuthorId(user.getmUid());

                    Log.d(TAG, "onAuthenticationSuccess: uid = "+user.getmUid());
                }

                @Override
                public void onAuthenticationFailure(String message) {

                    SessionUtils.doHardLogout(HelpPostActivity.this, mAuth);
                }
            };

    // variables used to fetch location
    private LocationFetcher mLocationFetcher;

    private LocationFetcher.LocationSettingsSetupListener mLocationSettingsSetupListener =
            new LocationFetcher.LocationSettingsSetupListener() {
                @Override
                public void onLocationSettingsSetupSuccess() {

                    startLocationUpdates(mLocationFetcher);
                }

                @Override
                public void onLocationSettingsSetupFailed(String message) {

                    // user will be automatically be asked to enable location settings
                    // see method in HelpPostActivity: 'onActivityResult(...)'

                    Log.d(TAG, "onLocationSettingsSetupFailed: location settings setup failed ->" + message);
                }
            };

    private LocationFetcher.LocationUpdateListener mLocationUpdateListener =
            new LocationFetcher.LocationUpdateListener() {
                @Override
                public void onNewLocationUpdate(FetchedLocation fetchedLocation) {

                    // TODO: save most accurate fetched location

                    Log.d(TAG, "onNewLocationUpdate: location -> "+fetchedLocation.toString());
                }

                @Override
                public void onPermissionNotGranted() {

                    fetchLocationFailedUI();
                    mLocationFetcher.stopLocationUpdate();

                    Log.d(TAG, "onPermissionNotGranted: location permission not granted");
                }

                @Override
                public void onError(String message) {

                    fetchLocationFailedUI();
                    mLocationFetcher.stopLocationUpdate();

                    Log.d(TAG, "onError: location update error -> "+message);
                }
            };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help_post);

        init();
    }

    /*
    Required for reacting to photo captured

    Required for reacting to user response when setting up required location settings
    user response to default "turn on location" dialog is handled through this method
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode){

            case REQUEST_IMAGE_CAPTURE:

                if(resultCode!=RESULT_OK) return; // image was not captured

                mImageWasCaptured = true;

                mCapturedImageView.setVisibility(View.VISIBLE);
                mCapturedImageView.setImageURI(mCapturedImage.getmPhotoUri());

                break;

            case LocationFetcher.REQUEST_CHECK_LOCATION_SETTINGS:

                if(resultCode==RESULT_OK){
                    // user enabled location settings

                    startLocationUpdates(mLocationFetcher);
                }

                else{
                    // location settings not met

                    showLocationSettingsExplanationDialog();
                }
                break;
        }
    }

    @Override
    protected void onStop() {
        super.onStop();

        try{

            stopLocationUpdates(mLocationFetcher);

        }catch (Exception e){

            Log.d(TAG, "onStop: failed to stop location updates -> "+e.getStackTrace());
        }
    }


    private void init() {

        mCapturedImageView = findViewById(R.id.helpPost_capturedPhoto_ImageView);
        mTakeImageButton = findViewById(R.id.helpPost_takePhoto_Button);
        mFetchLocationButton = findViewById(R.id.helpPost_PickLocation_Button);
        mPostButton = findViewById(R.id.helpPost_Post_Button);

        mHelpPost = new HelpPost();
        mHelpPost.setmAuthor("anonymous");

        mAuth = new FirebaseEmailPasswordAuthentication();
        authenticateUserLoginState(mAuth, mAuthCallbacks);

        mImageWasCaptured = false;

        mLocationFetcher = new FusedLocationFetcherApiAdapter(
                1000, this,
                mLocationSettingsSetupListener,
                mLocationUpdateListener
        );
    }

    /**
     * authenticate if user is logged in
     * @param auth authenticator object
     * @param authCallbacks authentication sucess/failure callback
     */
    private void authenticateUserLoginState(Authentication auth, Authentication.AuthenticationCallbacks authCallbacks) {

        auth.setmAuthenticationCallbacks(authCallbacks);
        auth.authenticateUser();
    }

    /*
    'Take Photo' button click
     */
    public void takePhotoClick(View view) {

        try {

            // initialize an mImage object only the first time 'addFoodPhoto' button was pressed
            if(mCapturedImage==null) mCapturedImage = CapturedImage.build(this);

            dispatchTakePictureIntent(mCapturedImage);

        } catch (IOException e) {

            showToast(getString(R.string.no_default_camera_app_found));

            Log.d(TAG, "takePhotoClick: error->"+e.getMessage());
        }
    }

    /**
     * open default camera app to take an image
     * @param image model for image to be capture, with info of temporarily created local file info
     */
    private void dispatchTakePictureIntent(CapturedImage image) {

        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // Ensure that there's a camera activity to handle the intent
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {

            File photoFile = image.getmPhotoFile();

            if (photoFile != null) {

                Uri photoURI = FileProvider.getUriForFile(
                        this,
                        FILE_PROVIDER_AUTHORITY,
                        photoFile
                );

                image.setmPhotoUri(photoURI);

                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);

                startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
            }
        }
    }



    /*
    'Pick Location' button click
     */
    public void pickLocationClick(View view) {

        fetchLocationInProgressUI();
        startFetchingLocation();
    }

    private void startFetchingLocation() {

        // after getting the location permissions the location fetching starts
        getLocationPermissions();
    }

    /*
    Ask for location access permission
    using the open source library- <https://github.com/Karumi/Dexter>
     */
    private void getLocationPermissions() {

        Dexter.withContext(this)
                .withPermission(Manifest.permission.ACCESS_FINE_LOCATION)
                .withListener(new PermissionListener() {
                    @Override
                    public void onPermissionGranted(PermissionGrantedResponse permissionGrantedResponse) {

                        requestLocationSettings(mLocationFetcher);
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

    /**
     * request necessary location settings
     * @param locationFetcher location fetcher/settings requesting object
     */
    private void requestLocationSettings(LocationFetcher locationFetcher) {

        mLocationFetcher.setupLocationSettings(this);
    }

    /**
     * start location updates/fetching (after permission has been granted and settings are met)
     * @param locationFetcher location fetcher object
     */
    private void startLocationUpdates(LocationFetcher locationFetcher) {

        locationFetcher.startLocationUpdate();
    }

    /**
     * stop fetching locations
     * @param locationFetcher location fetcher object
     */
    private void stopLocationUpdates(LocationFetcher locationFetcher) {

        locationFetcher.stopLocationUpdate();
    }


    /*
    show alert dialog explaining why location permission is a MUST
    with a simple dialog, quit activity if permission is permanently denied
    courtesy - <https://stackoverflow.com/questions/26097513/android-simple-alert-dialog
     */
    private void showLocationPermissionExplanationDialog(boolean isPermissionPermanentlyDenied) {

        AlertDialog alertDialog = new AlertDialog.Builder(HelpPostActivity.this).create();

        String title = getString(R.string.location_permission);
        String explanation;

        if(isPermissionPermanentlyDenied)
            explanation = getString(R.string.location_permission_permanantely_denied_explanation);
        else
            explanation = getString(R.string.location_permission_explanation);

        alertDialog.setTitle(title);
        alertDialog.setMessage(explanation);
        alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, getString(R.string.ok),
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

        AlertDialog alertDialog = new AlertDialog.Builder(HelpPostActivity.this).create();

        String title = getString(R.string.location_settings);
        String explanation = getString(R.string.location_settings_explanation);

        alertDialog.setTitle(title);
        alertDialog.setMessage(explanation);
        alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, getString(R.string.ok),
                (dialog, which) -> mLocationFetcher.setupLocationSettings(HelpPostActivity.this));

        alertDialog.show();
    }


    /*
    'Post' button click
     */
    public void postClick(View view) {
    }



    /*
    UI event for when location is being fetched
     */
    private void fetchLocationInProgressUI(){

        mFetchLocationButton.setEnabled(false);
        mFetchLocationButton.setText(R.string.fetching_location);
    }

    /*
    UI event for when location fetch success
     */
    private void fetchLocationSuccessUI(){

        mFetchLocationButton.setEnabled(false);
        mFetchLocationButton.setText(R.string.location_fetched);
    }

    /*
    UI event for when location fetch failed
     */
    private void fetchLocationFailedUI(){

        showToast(getString(R.string.location_fetch_failed));

        mFetchLocationButton.setEnabled(true);
        mFetchLocationButton.setText(R.string.helpPost_PickLocation_Button_label);
    }

    private void showToast(String message){

        Toast.makeText(this, message, Toast.LENGTH_SHORT)
                .show();
    }
}