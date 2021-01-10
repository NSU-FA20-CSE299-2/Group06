package com.nsu.group06.cse299.sec02.helpmeapp.models;

/**
 * Model class for a help post
 */
public class HelpPost {

    private String mPostId;
    private String mAuthorId;
    private String mAuthor;
    private String mContent;
    private double mLongitude, mLatitude, mAltitude;
    private String mAddress;
    private String mPhotoURL;
    private String mTimeStamp;

    public HelpPost() {
    }

    public HelpPost(String mPostId, String mAuthorId, String mAuthor, String mContent,
                    double mLongitude, double mLatitude, double mAltitude, String mAddress,
                    String mPhotoURL, String mTimeStamp) {

        this.mPostId = mPostId;
        this.mAuthorId = mAuthorId;
        this.mAuthor = mAuthor;
        this.mContent = mContent;
        this.mLongitude = mLongitude;
        this.mLatitude = mLatitude;
        this.mAltitude = mAltitude;
        this.mAddress = mAddress;
        this.mPhotoURL = mPhotoURL;
        this.mTimeStamp = mTimeStamp;
    }

    public String getmPostId() {
        return mPostId;
    }

    public void setmPostId(String mPostId) {
        this.mPostId = mPostId;
    }

    public String getmAuthorId() {
        return mAuthorId;
    }

    public void setmAuthorId(String mAuthorId) {
        this.mAuthorId = mAuthorId;
    }

    public String getmAuthor() {
        return mAuthor;
    }

    public void setmAuthor(String mAuthor) {
        this.mAuthor = mAuthor;
    }

    public String getmContent() {
        return mContent;
    }

    public void setmContent(String mContent) {
        this.mContent = mContent;
    }

    public double getmLongitude() {
        return mLongitude;
    }

    public void setmLongitude(double mLongitude) {
        this.mLongitude = mLongitude;
    }

    public double getmLatitude() {
        return mLatitude;
    }

    public void setmLatitude(double mLatitude) {
        this.mLatitude = mLatitude;
    }

    public double getmAltitude() {
        return mAltitude;
    }

    public void setmAltitude(double mAltitude) {
        this.mAltitude = mAltitude;
    }

    public String getmAddress() {
        return mAddress;
    }

    public void setmAddress(String mAddress) {
        this.mAddress = mAddress;
    }

    public String getmPhotoURL() {
        return mPhotoURL;
    }

    public void setmPhotoURL(String mPhotoURL) {
        this.mPhotoURL = mPhotoURL;
    }

    public String getmTimeStamp() {
        return mTimeStamp;
    }

    public void setmTimeStamp(String mTimeStamp) {
        this.mTimeStamp = mTimeStamp;
    }
}
