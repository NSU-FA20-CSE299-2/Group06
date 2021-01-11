package com.nsu.group06.cse299.sec02.helpmeapp.models;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.Date;

/**
 * Model class for a help post
 */
public class HelpPost {

    private String postId;
    private String authorId;
    private String author = "anonymous";
    private String authorPhoneNumber;
    private String content;
    private double longitude, latitude, altitude;
    private String address = "";
    private String photoURL = "";
    private String timeStamp;
    private boolean isPublic = false;

    private static final String URL = "https://helpmeappweb.herokuapp.com/help/";

    public HelpPost() {
    }

    public HelpPost(String postId, String authorId, String author, String content,
                    double longitude, double latitude, double altitude, String address,
                    String authorPhoneNumber, String photoURL, String timeStamp, boolean isPublic) {

        this.postId = postId;
        this.authorId = authorId;
        this.author = author;
        this.authorPhoneNumber = authorPhoneNumber;
        this.content = content;
        this.longitude = longitude;
        this.latitude = latitude;
        this.altitude = altitude;
        this.address = address;
        this.photoURL = photoURL;
        this.timeStamp = timeStamp;
        this.isPublic = isPublic;
    }

    public static String getSMSBody(HelpPost helpPost){

        String message = "Distress call from " + helpPost.getAuthorPhoneNumber() + "!";

        if(helpPost.getAddress()!=null && !helpPost.getAddress().isEmpty()) {
            message += ", " + helpPost.getAddress();
        }

        String url = URL + "?pid="+helpPost.getPostId();
        message+=", "+url;

        return message;
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

    public String getPostId() {
        return postId;
    }

    public void setPostId(String mPostId) {
        this.postId = mPostId;
    }

    public String getAuthorId() {
        return authorId;
    }

    public void setAuthorId(String mAuthorId) {
        this.authorId = mAuthorId;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String mAuthor) {
        this.author = mAuthor;
    }

    public String getAuthorPhoneNumber() {
        return authorPhoneNumber;
    }

    public void setAuthorPhoneNumber(String authorPhoneNumber) {
        this.authorPhoneNumber = authorPhoneNumber;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String mContent) {
        this.content = mContent;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double mLongitude) {
        this.longitude = mLongitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double mLatitude) {
        this.latitude = mLatitude;
    }

    public double getAltitude() {
        return altitude;
    }

    public void setAltitude(double mAltitude) {
        this.altitude = mAltitude;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String mAddress) {
        this.address = mAddress;
    }

    public String getPhotoURL() {
        return photoURL;
    }

    public void setPhotoURL(String mPhotoURL) {
        this.photoURL = mPhotoURL;
    }

    public String getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(String mTimeStamp) {
        this.timeStamp = mTimeStamp;
    }

    public boolean isPublic() {
        return isPublic;
    }

    public void setIsPublic(boolean mIsPublic) {
        this.isPublic = mIsPublic;
    }
}
