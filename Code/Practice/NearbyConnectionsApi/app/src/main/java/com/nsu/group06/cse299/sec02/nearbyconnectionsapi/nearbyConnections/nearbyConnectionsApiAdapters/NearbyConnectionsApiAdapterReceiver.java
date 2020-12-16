package com.nsu.group06.cse299.sec02.nearbyconnectionsapi.nearbyConnections.nearbyConnectionsApiAdapters;

import com.nsu.group06.cse299.sec02.nearbyconnectionsapi.nearbyConnections.NearbyConnection;

public class NearbyConnectionsApiAdapterReceiver extends NearbyConnection.Receiver {

    public NearbyConnectionsApiAdapterReceiver(NearbyConnection.ReceiverCallbacks receiverCallbacks, NearbyConnection.Authenticator authenticator, NearbyConnection.Connection connection) {
        super(receiverCallbacks, authenticator, connection);
    }

    @Override
    public void advertiseToSenders() {

    }
}
