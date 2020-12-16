package com.nsu.group06.cse299.sec02.nearbyconnectionsapi.nearbyConnections;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

public class NearbyHelpPost implements NearbyConnectionData {

    private String mAuthor, mContent;
    private double mLatitude, mLongitude;
    private String mWebPageUrl;

    public NearbyHelpPost(String mAuthor, String mContent, double mLatitude, double mLongitude) {
        this.mAuthor = mAuthor;
        this.mContent = mContent;
        this.mLatitude = mLatitude;
        this.mLongitude = mLongitude;

        mWebPageUrl = "";
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

    public double getmLatitude() {
        return mLatitude;
    }

    public void setmLatitude(double mLatitude) {
        this.mLatitude = mLatitude;
    }

    public double getmLongitude() {
        return mLongitude;
    }

    public void setmLongitude(double mLongitude) {
        this.mLongitude = mLongitude;
    }

    public String getmWebPageUrl() {
        return mWebPageUrl;
    }

    public void setmWebPageUrl(String mWebPageUrl) {
        this.mWebPageUrl = mWebPageUrl;
    }

    @Override
    public byte[] toByteArray() throws IOException {
    // courtesy- <https://www.tutorialspoint.com/How-to-convert-an-object-to-byte-array-in-java>

        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(bos);
        oos.writeObject(this);
        oos.flush();

        return bos.toByteArray();
    }
}
