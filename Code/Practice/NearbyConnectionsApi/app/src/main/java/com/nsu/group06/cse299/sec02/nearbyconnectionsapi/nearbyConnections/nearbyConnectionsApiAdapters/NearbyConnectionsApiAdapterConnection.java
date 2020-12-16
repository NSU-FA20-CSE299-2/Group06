package com.nsu.group06.cse299.sec02.nearbyconnectionsapi.nearbyConnections.nearbyConnectionsApiAdapters;

import com.nsu.group06.cse299.sec02.nearbyconnectionsapi.nearbyConnections.NearbyConnection;
import com.nsu.group06.cse299.sec02.nearbyconnectionsapi.nearbyConnections.NearbyConnectionPeer;

public class NearbyConnectionsApiAdapterConnection extends NearbyConnection.Connection {

    public NearbyConnectionsApiAdapterConnection(NearbyConnectionPeer me, NearbyConnection.ConnectionCallbacks connectionCallbacks) {
        super(me, connectionCallbacks);
    }

    @Override
    public void requestConnection(NearbyConnectionPeer peer) {

    }

    @Override
    public void rejectConnection(NearbyConnectionPeer peer) {

    }

    @Override
    public void connect(NearbyConnectionPeer peer) {

    }

    @Override
    public void disconnect(NearbyConnectionPeer peer) {

    }
}
