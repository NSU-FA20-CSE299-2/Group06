package com.nsu.group06.cse299.sec02.fusedlocationapiadapter.fetchLocation;

/*
model for fethced location data
 */
public class FetchedLocation {

    private double mLatitude, mLongitude, mAltitude, mAccuracy;
    private String mAddress = "";

    public FetchedLocation() {
    }

    public FetchedLocation(double mLatitude, double mLongitude) {
        this.mLatitude = mLatitude;
        this.mLongitude = mLongitude;
    }

    public FetchedLocation(double mLatitude, double mLongitude, double mAltitude, double mAccuracy) {
        this.mLatitude = mLatitude;
        this.mLongitude = mLongitude;
        this.mAltitude = mAltitude;
        this.mAccuracy = mAccuracy;
    }

    public FetchedLocation(double mLatitude, double mLongitude, double mAltitude, double mAccuracy, String mAddress) {
        this.mLatitude = mLatitude;
        this.mLongitude = mLongitude;
        this.mAltitude = mAltitude;
        this.mAccuracy = mAccuracy;
        this.mAddress = mAddress;
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

    public double getmAltitude() {
        return mAltitude;
    }

    public void setmAltitude(double mAltitude) {
        this.mAltitude = mAltitude;
    }

    public double getmAccuracy() {
        return mAccuracy;
    }

    public void setmAccuracy(double mAccuracy) {
        this.mAccuracy = mAccuracy;
    }

    public String getmAddress() {
        return mAddress;
    }

    public void setmAddress(String mAddress) {
        this.mAddress = mAddress;
    }

    @Override
    public String toString() {
        return "FetchedLocation{\n" +
                "mLatitude=" +  roundTwoDecimal(mLatitude) +
                ", mLongitude=" + roundTwoDecimal(mLongitude) +
                ", mAltitude=" + roundTwoDecimal(mAltitude) +
                ", mAccuracy=" + roundTwoDecimal(mAccuracy) +
                ",\nmAddress='" + mAddress + '\'' +
                '}';
    }

    private String roundTwoDecimal(double d){

        return String.format("%.2f", d);
    }
}
