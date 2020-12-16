package com.nsu.group06.cse299.sec02.nearbyconnectionsapi.nearbyConnections;

/*
Abstraction for peer model class for nearby connection
 */
public abstract class NearbyConnectionPeer<PeerIdType> {

    private PeerIdType mPeerId; // uniquely identifies a peer

    public NearbyConnectionPeer(PeerIdType mPeerId) {
        this.mPeerId = mPeerId;
    }

    public PeerIdType getmPeerId() {
        return mPeerId;
    }

    public void setmPeerId(PeerIdType mPeerId) {
        this.mPeerId = mPeerId;
    }
}
