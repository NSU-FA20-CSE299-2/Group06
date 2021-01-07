package com.nsu.group06.cse299.sec02.imageupload.imageUpload;

/**
 * Abstraction for file upload/download codes
 * @param <UploadFileType> file type that is to be directly uploaded
 * @param <DownloadLinkType> link type that can be directly downloaded from
 */
public abstract class FileUploader<UploadFileType, DownloadLinkType> {

    public abstract void uploadFile(UploadFileType file);
    public abstract void downloadFile(DownloadLinkType downloadLink);

    public interface FileUploadCallbacks<UploadedImageLinkType>{

        void onUploadComplete(UploadedImageLinkType uploadedImageLink);
        void onUploadFailed(String message);
    }

    public interface FileDownloadCallbacks<DownloadedImageType>{

        void onDownloadComplete(DownloadedImageType downloadedImage);
        void onDownloadFailed(String message);
    }

}
