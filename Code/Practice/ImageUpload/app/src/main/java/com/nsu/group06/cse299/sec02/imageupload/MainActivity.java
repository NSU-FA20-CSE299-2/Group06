package com.nsu.group06.cse299.sec02.imageupload;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.nsu.group06.cse299.sec02.imageupload.imageUpload.FileUploader;
import com.nsu.group06.cse299.sec02.imageupload.imageUpload.FirebaseStorageFileUploader;
import com.nsu.group06.cse299.sec02.imageupload.imageUpload.CapturedImage;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MA-debug";

    // request code for camera intent
    private static final int REQUEST_IMAGE_CAPTURE = 589;

    // auth to access taken image file
    private static final String FILE_PROVIDER_AUTHORITY = "com.nsu.group06.cse299.sec02.imageupload.fileprovider";

    // ui
    private Button mTakeImageButton, mUploadImageButton;
    private ImageView mTakenImageImageView;

    // model
    private CapturedImage mCapturedImage;
    private boolean mImageIsTaken = false;

    // variables to upload photo to firebase storage
    private FirebaseStorageFileUploader mFirebaseStorageFileUploader;
    private FileUploader.FileUploadCallbacks mFileUploadCallbacks = new FileUploader.FileUploadCallbacks() {
        @Override
        public void onUploadComplete(Uri uploadedImageLink) {

            imageUploadFinishedUI(true);

            try {
                URL link = new URL(uploadedImageLink.toString());
                Log.d(TAG, "onUploadComplete: upload link -> "+link.toString());
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onUploadFailed(String message) {

            imageUploadFinishedUI(false);

            Log.d(TAG, "onUploadFailed: error->" + message);
        }
    };

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

                mImageIsTaken = true;

                mTakenImageImageView.setImageURI(mCapturedImage.getmPhotoUri());

                break;
        }
    }

    private void init(){

        mTakeImageButton = findViewById(R.id.takePhoto_Button);
        mUploadImageButton = findViewById(R.id.uploadPhoto_Button);
        mTakenImageImageView = findViewById(R.id.takenImage_ImageView);

        mFirebaseStorageFileUploader = new FirebaseStorageFileUploader();
    }

    public void takePhotoClick(View view) {

        try {

            mCapturedImage = CapturedImage.build(this);

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

            File photoFile = mCapturedImage.getmPhotoFile();

            if (photoFile != null) {

                Uri photoURI = FileProvider.getUriForFile(
                        this,
                        FILE_PROVIDER_AUTHORITY,
                        photoFile
                );

                mCapturedImage.setmPhotoUri(photoURI);

                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);

                startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
            }
        }
    }

    public void uploadPhotoClick(View view) {

        if(mCapturedImage !=null && mImageIsTaken){

            mImageIsTaken = false;

            uploadingImageUI();

            mFirebaseStorageFileUploader.uploadFile(
                    mCapturedImage,
                    "testImages/"+ mCapturedImage.getmPhotoFileName(),
                    mFileUploadCallbacks
            );
        }

        else showToast("Take a photo first man");
    }


    private void uploadingImageUI() {

        mTakeImageButton.setEnabled(false);

        mUploadImageButton.setEnabled(false);
        mUploadImageButton.setText("uploading...");
    }

    private void imageUploadFinishedUI(boolean success) {

        mTakeImageButton.setEnabled(true);

        mUploadImageButton.setEnabled(true);
        mUploadImageButton.setText("Upload Photo");

        if(success) showToast("image uploaded successfully!");

        else showToast("Failed to upload image");
    }

    private void showToast(String message){

        Toast.makeText(this, message, Toast.LENGTH_SHORT)
                .show();
    }
}