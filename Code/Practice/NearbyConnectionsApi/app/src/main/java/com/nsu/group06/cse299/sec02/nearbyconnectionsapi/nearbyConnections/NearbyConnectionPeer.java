package com.nsu.group06.cse299.sec02.nearbyconnectionsapi.nearbyConnections;

/*
Peer model class for nearby connection
 */
public class NearbyConnectionPeer<PeerIdType> {

    private String username;
    private PeerIdType mPeerId; // uniquely identifies a peer

    public NearbyConnectionPeer(String username, PeerIdType mPeerId) {
        this.username = username;
        this.mPeerId = mPeerId;
    }

    public NearbyConnectionPeer(PeerIdType mPeerId) {
        this.mPeerId = mPeerId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public PeerIdType getmPeerId() {
        return mPeerId;
    }

    public void setmPeerId(PeerIdType mPeerId) {
        this.mPeerId = mPeerId;
    }
}
