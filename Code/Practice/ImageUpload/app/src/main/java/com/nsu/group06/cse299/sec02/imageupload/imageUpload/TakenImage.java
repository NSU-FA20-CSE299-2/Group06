package com.nsu.group06.cse299.sec02.imageupload.imageUpload;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Model class for image taken for an help post
 */
public class TakenImage {

    private String mPhotoFilePath;
    private File mPhotoFile;
    private Uri mPhotoUri;

    public static TakenImage build(Context context) throws IOException {

        return new TakenImage(context);
    }

    private TakenImage(Context context) throws IOException {

        createImageFile(context);
    }

    /**
     * Create an temporary file where image is going to be saved
     * the name should not collide with any existing image
     * @param context Activity from where this was called
     * @throws IOException exception in temp file creation
     */
    private void createImageFile(Context context) throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = context.getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );

        // Save a file: path for use with ACTION_VIEW intents
        mPhotoFilePath = image.getAbsolutePath();
        mPhotoFile = image;
    }

    public String getmPhotoFilePath() {
        return mPhotoFilePath;
    }

    public File getmPhotoFile() {
        return mPhotoFile;
    }

    public Uri getmPhotoUri() {
        return mPhotoUri;
    }

    public void setmPhotoUri(Uri mPhotoUri) {
        this.mPhotoUri = mPhotoUri;
    }
}
