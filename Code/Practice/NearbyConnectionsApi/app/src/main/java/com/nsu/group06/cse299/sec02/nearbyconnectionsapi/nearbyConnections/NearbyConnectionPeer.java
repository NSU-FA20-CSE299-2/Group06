package com.nsu.group06.cse299.sec02.nearbyconnectionsapi.nearbyConnections;

import java.util.Objects;

/*
Peer model class for nearby connection
 */
public class NearbyConnectionPeer {

    private String username;
    private String mPeerId; // uniquely identifies a peer

    public NearbyConnectionPeer() {
    }

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        NearbyConnectionPeer peer = (NearbyConnectionPeer) o;
        return Objects.equals(username, peer.username) &&
                Objects.equals(mPeerId, peer.mPeerId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(username, mPeerId);
    }
}
