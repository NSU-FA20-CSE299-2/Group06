package com.nsu.group06.cse299.sec02.imageupload;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import android.Manifest;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.nsu.group06.cse299.sec02.imageupload.imageUpload.TakenImage;

import java.io.File;
import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MA-debug";

    // request code for camera intent
    private static final int REQUEST_IMAGE_CAPTURE = 589;

    // auth to access taken image file
    private static final String FILE_PROVIDER_AUTHORITY = "com.nsu.group06.cse299.sec02.imageupload.fileprovider";

    // ui
    private ImageView mTakenImageView;

    // model
    private TakenImage mTakenImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(resultCode!=RESULT_OK) return;

        switch (requestCode){

            case REQUEST_IMAGE_CAPTURE:

                showToast("image taken!");

                break;
        }
    }

    private void init(){

        mTakenImageView = findViewById(R.id.photoTaken_ImageView);
    }

    public void takePhotoClick(View view) {

        try {

            mTakenImage = TakenImage.build(this);

            dispatchTakePictureIntent();

        } catch (IOException e) {

            showToast("An unexpected error occured");

            Log.d(TAG, "takePhotoClick: error->"+e.getMessage());
        }
    }

    /*
    open device default camera app to take photo
     */
    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // Ensure that there's a camera activity to handle the intent
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {

            File photoFile = mTakenImage.getmPhotoFile();

            if (photoFile != null) {

                Uri photoURI = FileProvider.getUriForFile(
                        this,
                        FILE_PROVIDER_AUTHORITY,
                        photoFile
                );

                mTakenImage.setmPhotoUri(photoURI);

                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);

                startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
            }
        }
    }

    public void uploadPhotoClick(View view) {
    }

    private void showToast(String message){

        Toast.makeText(this, message, Toast.LENGTH_SHORT)
                .show();
    }
}