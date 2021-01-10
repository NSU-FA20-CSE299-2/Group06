package com.nsu.group06.cse299.sec02.helpmeapp.imageUpload;

import android.net.Uri;

/**
 * Abstraction for file upload/download codes
 * @param <UploadFileType> file type that is to be directly uploaded
 * @param <DownloadLinkType> link type that can be directly downloaded from
 * @param <UploadPathType> uploading remote storage path type
 */
public abstract class FileUploader<UploadFileType, DownloadLinkType, UploadPathType> {

    public FileUploader() {
    }

    public abstract void uploadFile(UploadFileType file, UploadPathType path, FileUploadCallbacks fileUploadCallbacks);
    public abstract void downloadFile(DownloadLinkType downloadLink, FileDownloadCallbacks fileDownloadCallbacks);

    public interface FileUploadCallbacks{

        void onUploadComplete(Uri uploadedImageLink);
        void onUploadFailed(String message);
    }

    public interface FileDownloadCallbacks{

        void onDownloadComplete(Uri downloadedImage);
        void onDownloadFailed(String message);
    }

}
