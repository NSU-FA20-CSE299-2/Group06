package com.nsu.group06.cse299.sec02.nearbyconnectionsapi.nearbyConnections;

/*
Peer model class for nearby connection
 */
public class NearbyConnectionPeer {

    private String username;
    private String mPeerId; // uniquely identifies a peer

    public NearbyConnectionPeer(String username, String mPeerId) {
        this.username = username;
        this.mPeerId = mPeerId;
    }

    public NearbyConnectionPeer(String mPeerId) {
        this.mPeerId = mPeerId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getmPeerId() {
        return mPeerId;
    }

    public void setmPeerId(String mPeerId) {
        this.mPeerId = mPeerId;
    }
}
