package com.nsu.group06.cse299.sec02.nearbyconnectionsapi.nearbyConnections.nearbyConnectionsApiAdapters;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.nearby.Nearby;
import com.google.android.gms.nearby.connection.DiscoveredEndpointInfo;
import com.google.android.gms.nearby.connection.DiscoveryOptions;
import com.google.android.gms.nearby.connection.EndpointDiscoveryCallback;
import com.google.android.gms.nearby.connection.Payload;
import com.google.android.gms.nearby.connection.Strategy;
import com.nsu.group06.cse299.sec02.nearbyconnectionsapi.nearbyConnections.NearbyConnection;
import com.nsu.group06.cse299.sec02.nearbyconnectionsapi.nearbyConnections.NearbyConnection.Sender;
import com.nsu.group06.cse299.sec02.nearbyconnectionsapi.nearbyConnections.NearbyConnectionData;
import com.nsu.group06.cse299.sec02.nearbyconnectionsapi.nearbyConnections.NearbyConnectionPeer;

/*
Sender(discoverer) implementation of Nearby Connections Api
 */
public class NearbyConnectionsApiAdapterSender extends Sender {

    private static final String TAG = "NCAAS-debug";

    private Context mContext;

    // TODO: set to app package name
    // id that advertiser is advertising with, MUST be unique to the app
    private static final String SERVICE_ID = "com.nsu.group06.cse299.sec02";

    // only setup one-to-one connections
    // <https://developers.google.com/nearby/connections/strategies#p2p_point_to_point>
    private static final Strategy STRATEGY = Strategy.P2P_POINT_TO_POINT;

    private EndpointDiscoveryCallback mEndpointDiscoveryCallback = new EndpointDiscoveryCallback() {
        @Override
        public void onEndpointFound(@NonNull String endPointId, @NonNull DiscoveredEndpointInfo discoveredEndpointInfo) {

            NearbyConnectionPeer peer = new NearbyConnectionPeer(endPointId);
            senderCallbacks.onReceiverDiscovered(peer);
        }

        @Override
        public void onEndpointLost(@NonNull String endPointId) {

            Log.d(TAG, "onEndpointLost: end point lost -> "+endPointId);
        }
    };

    public NearbyConnectionsApiAdapterSender(Context context, byte[] dataToSend, NearbyConnection.SenderCallbacks senderCallbacks,
                                             NearbyConnectionCustomAuthenticator authenticator,
                                             NearbyConnectionsApiAdapterConnection connection) {

        super(dataToSend, senderCallbacks, authenticator, connection);

        this.mContext = context;
    }

    @Override
    public void discoverReceivers() {

        DiscoveryOptions discoveryOptions = new DiscoveryOptions.Builder().setStrategy(STRATEGY).build();

        Nearby.getConnectionsClient(mContext)
                .startDiscovery(SERVICE_ID, mEndpointDiscoveryCallback, discoveryOptions)
                .addOnSuccessListener(

                        (Void unused) -> Log.d(TAG, "discoverReceivers: discovery started!")
                )
                .addOnFailureListener(

                        (Exception e) -> {

                            Log.d(TAG, "discoverReceivers: discovery failed to start -> "+ e.getMessage());
                            senderCallbacks.onDiscoveryError(e.getMessage());
                        }
                );
    }

    @Override
    public void sendDataToConnectedReceiver() {

        if(connection.getPeer() == null){

            senderCallbacks.onDataSendFailed("no connected peer connected");
            return;
        }

        Payload bytesPayload = Payload.fromBytes(dataToSend);
        String peerId = connection.getPeer().getmPeerId();
        Nearby.getConnectionsClient(mContext).sendPayload(peerId, bytesPayload);
    }
}
