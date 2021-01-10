package com.nsu.group06.cse299.sec02.helpmeapp;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.nsu.group06.cse299.sec02.helpmeapp.imageUpload.CapturedImage;

import java.io.File;
import java.io.IOException;

public class HelpPostActivity extends AppCompatActivity {

    private static final String TAG = "MA-debug";

    // request code for camera intent
    private static final int REQUEST_IMAGE_CAPTURE = 935;

    // auth to access taken image file
    private static final String FILE_PROVIDER_AUTHORITY = "com.nsu.group06.cse299.sec02.helpmeapp.fileprovider";

    // ui
    private ImageView mCapturedImageView;

    // model
    private CapturedImage mCapturedImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help_post);

        init();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(resultCode!=RESULT_OK) return;

        switch (requestCode){

            case REQUEST_IMAGE_CAPTURE:

                mCapturedImageView.setVisibility(View.VISIBLE);
                mCapturedImageView.setImageURI(mCapturedImage.getmPhotoUri());

                break;
        }
    }

    private void init() {

        mCapturedImageView = findViewById(R.id.helpPost_capturedPhoto_ImageView);
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
    }

    /*
    'Post' button click
     */
    public void postClick(View view) {
    }



    private void showToast(String message){

        Toast.makeText(this, message, Toast.LENGTH_SHORT)
                .show();
    }
}