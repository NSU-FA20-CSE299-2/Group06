package com.nsu.group06.cse299.sec02.imageupload.imageUpload;

/**
 * Abstraction for file upload/download codes
 * @param <UploadFileType> file type that is to be directly uploaded
 * @param <DownloadLinkType> link type that can be directly downloaded from
 */
public abstract class FileUploader<UploadFileType, DownloadLinkType> {

    private UploadFileType mFileToUpload;
    private DownloadLinkType mDownloadLink;

    private FileUploadCallbacks mFileUploadCallbacks;
    private FileDownloadCallbacks mFileDownloadCallbacks;

    public FileUploader(UploadFileType mFileToUpload, FileUploadCallbacks mFileUploadCallbacks) {
        this.mFileToUpload = mFileToUpload;
        this.mFileUploadCallbacks = mFileUploadCallbacks;
    }

    public FileUploader(DownloadLinkType mDownloadLink, FileDownloadCallbacks mFileDownloadCallbacks) {
        this.mDownloadLink = mDownloadLink;
        this.mFileDownloadCallbacks = mFileDownloadCallbacks;
    }

    public UploadFileType getmFileToUpload() {
        return mFileToUpload;
    }

    public void setmFileToUpload(UploadFileType mFileToUpload) {
        this.mFileToUpload = mFileToUpload;
    }

    public DownloadLinkType getmDownloadLink() {
        return mDownloadLink;
    }

    public void setmDownloadLink(DownloadLinkType mDownloadLink) {
        this.mDownloadLink = mDownloadLink;
    }

    public abstract void uploadFile();
    public abstract void downloadFile();

    public interface FileUploadCallbacks{

        void uploadComplete();
        void uploadFailed(String message);
    }

    public interface FileDownloadCallbacks<DownloadedImageType>{

        void downloadComplete(DownloadedImageType downloadedImage);
        void downloadFailed(String message);
    }

}
