package com.nsu.group06.cse299.sec02.nearbyconnectionsapi.nearbyConnections;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

/*
Model class for data to be sent/received via nearby connections
 */
                            // because we need to convert the model to byte array
public class NearbyHelpPost implements Serializable {

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

    /*
    Validate if all fields of the object
     */
    public boolean isValid(){

        return this.getmAuthor()!=null && this.getmContent()!=null
                && this.getmLatitude()<=180 && this.getmLatitude()>=-180
                && this.getmLongitude()<=180 && this.getmLongitude()>=-180;
    }

    /*
    convert an object of NearbyHelpPost to byte array
    courtesy- <https://www.tutorialspoint.com/How-to-convert-an-object-to-byte-array-in-java>
     */
    public static byte[] toByteArray(NearbyHelpPost nearbyHelpPost) throws IOException {

        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(bos);
        oos.writeObject(nearbyHelpPost);
        oos.flush();

        return bos.toByteArray();
    }

    /*
    get an object of this class from byte code convert byte
    courtesy- <https://stackoverflow.com/questions/3736058/java-object-to-byte-and-byte-to-object-converter-for-tokyo-cabinet>
     */
    public static NearbyHelpPost toNearbyHelpPost(byte[] bytes) throws IOException, ClassNotFoundException {

        ByteArrayInputStream in = new ByteArrayInputStream(bytes);
        ObjectInputStream is = new ObjectInputStream(in);
        return (NearbyHelpPost) is.readObject();
    }

}
