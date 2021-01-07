package com.nsu.group06.cse299.sec02.imageupload.imageUpload;

import android.net.Uri;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;


/**
 * Class for firestore upload image implementation
 */
public class FirebaseStorageFileUploader extends FileUploader<TakenImage, String> {

    private FileUploadCallbacks<Uri> mFileUploadCallbacks;
    private FileDownloadCallbacks<Uri> mFileDownloadCallbacks;

    private StorageReference mFirebaseStorageRef;
    private String mDBPath;

    public FirebaseStorageFileUploader(FileUploadCallbacks<Uri> mFileUploadCallbacks, String mDBPath) {

        this.mDBPath = mDBPath;
        mFirebaseStorageRef = FirebaseStorage.getInstance().getReference();
    }

    public FirebaseStorageFileUploader(FileDownloadCallbacks<Uri> mFileDownloadCallbacks, String mDBPath) {

        this.mDBPath = mDBPath;
        mFirebaseStorageRef = FirebaseStorage.getInstance().getReference();
    }

    @Override
    public void uploadFile(TakenImage image) {

        StorageReference ref = mFirebaseStorageRef.child(mDBPath);

        ref.putFile(image.getmPhotoUri()).continueWithTask(task -> {
            if (!task.isSuccessful()) {

                mFileUploadCallbacks.onUploadFailed("failed to upload -> "+task.getException().getMessage());
                throw task.getException();
            }

            // Continue with the task to get the download URL
            return ref.getDownloadUrl();

        }).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {

                Uri downloadUri = task.getResult();
                mFileUploadCallbacks.onUploadComplete(downloadUri);
            }

            else{

                mFileUploadCallbacks.onUploadFailed("failed to fetch uploaded image url -> "
                        + task.getException().getMessage());
            }
        });
    }

    @Override
    public void downloadFile(String downloadPath) {

        mFirebaseStorageRef.child(downloadPath).getDownloadUrl()

                .addOnSuccessListener(uri -> mFileDownloadCallbacks.onDownloadComplete(uri))

                .addOnFailureListener(e -> mFileDownloadCallbacks.onDownloadFailed(e.getMessage()));
    }

    public String getmDBPath() {
        return mDBPath;
    }

    public void setmDBPath(String mDBPath) {
        this.mDBPath = mDBPath;
    }
}
