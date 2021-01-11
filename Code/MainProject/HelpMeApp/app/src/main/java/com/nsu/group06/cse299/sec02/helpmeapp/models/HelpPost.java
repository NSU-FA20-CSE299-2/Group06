package com.nsu.group06.cse299.sec02.helpmeapp.models;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.Date;

/**
 * Model class for a help post
 */
public class HelpPost {

    private String mPostId;
    private String mAuthorId;
    private String mAuthor = "anonymous";
    private String mContent;
    private double mLongitude, mLatitude, mAltitude;
    private String mAddress = "";
    private String mPhotoURL = "";
    private String mTimeStamp;
    private boolean mIsPublic = false;

    public HelpPost() {
    }

    public HelpPost(String mPostId, String mAuthorId, String mAuthor, String mContent,
                    double mLongitude, double mLatitude, double mAltitude, String mAddress,
                    String mPhotoURL, String mTimeStamp, boolean mIsPublic) {

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
        this.mIsPublic = mIsPublic;
    }

    /**
     * generate an unique help post by combining user auth id with current time
     * @param uid user auth id
     * @return unique post id
     */
    public static String generateUniquePostId(String uid){

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            Instant instant = Instant.now();

            return uid+instant.toEpochMilli();
        }

        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());

        return uid+timeStamp;
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

    public boolean ismIsPublic() {
        return mIsPublic;
    }

    public void setmIsPublic(boolean mIsPublic) {
        this.mIsPublic = mIsPublic;
    }
}
