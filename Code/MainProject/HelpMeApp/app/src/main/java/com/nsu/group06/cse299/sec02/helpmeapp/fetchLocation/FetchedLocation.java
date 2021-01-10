package com.nsu.group06.cse299.sec02.helpmeapp.fetchLocation;

/*
model for fetched location data
 */
public class FetchedLocation {

    private double mLatitude, mLongitude, mAltitude, mAccuracy;
    private String mAddress = "";

    // TODO: calculate the values properly
    private static final double SIGNIFICANT_DIFFERENCE_DISTANCE = 20.00d; // in meters
    private static final double MINIMUM_ACCURACY_REQUIREMENT = 30.00d; // in meters

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

    /*
    Return true if there is significant difference between the two locations
    i.e distance between them is more than SIGNIFICANT_DIFFERENCE_DISTANCE(= 20 meters)
     */
    public static boolean isLocationSignificantlyDifferent(FetchedLocation location1, FetchedLocation location2){

        return distanceBetween(location1.getmLatitude(), location2.getmLatitude(), location1.getmLongitude(), location2.getmLongitude())
                >= SIGNIFICANT_DIFFERENCE_DISTANCE;
    }

    /**
     * check a location is accurate enough
     * @param fetchedLocation location object to be checked
     * @return accurate enough or not
     */
    public static boolean isLocationAccurateEnough(FetchedLocation fetchedLocation){

        return fetchedLocation.getmAccuracy() <= MINIMUM_ACCURACY_REQUIREMENT;
    }

    /*
    Returns distance in meters between two latLng points
    courtesy- <https://www.geeksforgeeks.org/program-distance-two-points-earth/>
     */
    private static double distanceBetween(double lat1, double lat2, double lon1, double lon2)
    {

        // The math module contains a function
        // named toRadians which converts from
        // degrees to radians.
        lon1 = Math.toRadians(lon1);
        lon2 = Math.toRadians(lon2);
        lat1 = Math.toRadians(lat1);
        lat2 = Math.toRadians(lat2);

        // Haversine formula
        double dlon = lon2 - lon1;
        double dlat = lat2 - lat1;
        double a = Math.pow(Math.sin(dlat / 2), 2)
                + Math.cos(lat1) * Math.cos(lat2)
                * Math.pow(Math.sin(dlon / 2),2);

        double c = 2 * Math.asin(Math.sqrt(a));

        // Radius of earth in meters.
        double r = 6371*1000;

        // calculate the result
        return(c * r);
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
                ",\nmAddress=" + mAddress +
                '}';
    }

    private String roundTwoDecimal(double d){

        return String.format("%.2f", d);
    }
}
