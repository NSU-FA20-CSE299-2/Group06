package com.nsu.group06.cse299.sec02.imageupload.imageUpload;

import android.net.Uri;

/**
 * Class for firestore upload image implementation
 */
public class FirestoreFileUploader extends FileUploader<Uri, String> {

    private TakenImage mImage;

    public FirestoreFileUploader(Uri mFileToUpload, FileUploadCallbacks mFileUploadCallbacks, TakenImage mImage) {
        super(mFileToUpload, mFileUploadCallbacks);

        this.mImage = mImage;
    }

    public FirestoreFileUploader(String mDownloadLink, FileDownloadCallbacks<TakenImage> mFileDownloadCallbacks, TakenImage mImage) {
        super(mDownloadLink, mFileDownloadCallbacks);

        this.mImage = mImage;
    }

    @Override
    public void uploadFile() {

    }

    @Override
    public void downloadFile() {

    }

    public TakenImage getmImage() {
        return mImage;
    }

    public void setmImage(TakenImage mImage) {
        this.mImage = mImage;
    }
}
