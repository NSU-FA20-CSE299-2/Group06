package com.nsu.group06.cse299.sec02.nearbyconnectionsapi.nearbyConnections.nearbyConnectionsApiAdapters;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.nearby.Nearby;
import com.google.android.gms.nearby.connection.ConnectionInfo;
import com.google.android.gms.nearby.connection.ConnectionLifecycleCallback;
import com.google.android.gms.nearby.connection.ConnectionResolution;
import com.google.android.gms.nearby.connection.ConnectionsStatusCodes;
import com.google.android.gms.nearby.connection.DiscoveredEndpointInfo;
import com.google.android.gms.nearby.connection.DiscoveryOptions;
import com.google.android.gms.nearby.connection.EndpointDiscoveryCallback;
import com.google.android.gms.nearby.connection.Payload;
import com.google.android.gms.nearby.connection.PayloadCallback;
import com.google.android.gms.nearby.connection.PayloadTransferUpdate;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.nsu.group06.cse299.sec02.nearbyconnectionsapi.nearbyConnections.NearbyConnection;
import com.nsu.group06.cse299.sec02.nearbyconnectionsapi.nearbyConnections.NearbyConnection.Sender;
import com.nsu.group06.cse299.sec02.nearbyconnectionsapi.nearbyConnections.NearbyConnectionPeer;
import com.nsu.group06.cse299.sec02.nearbyconnectionsapi.nearbyConnections.NearbyHelpPost;

import java.io.IOException;

/*
Sender(discoverer) implementation of Nearby Connections Api
 */
public class NearbyConnectionsApiAdapterSender extends Sender {

    private static final String TAG = "NCAAS-debug";

    private Context mContext;

    private final EndpointDiscoveryCallback mEndpointDiscoveryCallback = new EndpointDiscoveryCallback() {
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

    private final ConnectionLifecycleCallback mConnectionLifecycleCallback =
            new ConnectionLifecycleCallback() {
                @Override
                public void onConnectionInitiated(@NonNull String endPointId, @NonNull ConnectionInfo connectionInfo) {

                    connectionCallbacks.onConnectionInitiated(new NearbyConnectionPeer(endPointId));
                }

                @Override
                public void onConnectionResult(@NonNull String endPointId, @NonNull ConnectionResolution connectionResolution) {

                    NearbyConnectionPeer peer = new NearbyConnectionPeer(endPointId);

                    switch (connectionResolution.getStatus().getStatusCode()) {
                        case ConnectionsStatusCodes.STATUS_OK:
                            // We're connected! Can now start sending and receiving data.

                            connectionCallbacks.onConnectionEstablished(peer);
                            break;

                        case ConnectionsStatusCodes.STATUS_CONNECTION_REJECTED:
                            // The connection was rejected by one or both sides.

                            connectionCallbacks.onConnectionRejected(peer);
                            break;

                        case ConnectionsStatusCodes.STATUS_ERROR:
                            // The connection broke before it was able to be accepted.

                            connectionCallbacks.onConnectionSetupError("failed to connect with -> " + peer.getmPeerId());
                            break;

                        default:
                            // Unknown status code
                    }

                }

                @Override
                public void onDisconnected(@NonNull String endPointId) {

                    NearbyConnectionPeer peer = new NearbyConnectionPeer(endPointId);
                    connectionCallbacks.onConnectionDisconnected(peer);
                }
            };

    private final PayloadCallback mPayloadCallback =
            new PayloadCallback() {
                @Override
                public void onPayloadReceived(@NonNull String endPointId, @NonNull Payload payload) {
                    // empty because the sender will not be receiving any data
                }

                @Override
                public void onPayloadTransferUpdate(@NonNull String endPointId, @NonNull PayloadTransferUpdate payloadTransferUpdate) {

                    NearbyConnectionPeer receiver = new NearbyConnectionPeer(endPointId);

                    switch (payloadTransferUpdate.getStatus()){

                        case ConnectionsStatusCodes.SUCCESS:

                            senderCallbacks.onDataSendSuccess(receiver);
                            break;

                        case ConnectionsStatusCodes.ERROR:

                            senderCallbacks.onDataSendFailed(receiver, "data send failed to peer -> " +endPointId);
                            break;

                        default:
                    }
                }
            };

    public NearbyConnectionsApiAdapterSender(Context mContext, NearbyConnectionPeer me,
                                             NearbyConnection.SenderCallbacks senderCallbacks,
                                             NearbyConnection.ConnectionCallbacks connectionCallbacks,
                                             NearbyConnection.AuthenticationCallbacks authenticationCallbacks) {

        super(me, senderCallbacks, connectionCallbacks, authenticationCallbacks);
        this.mContext = mContext;
    }

    @Override
    public void discoverReceivers() {

        DiscoveryOptions discoveryOptions = new DiscoveryOptions.Builder().setStrategy(NearbyConnection.STRATEGY).build();

        Nearby.getConnectionsClient(mContext)
                .startDiscovery(NearbyConnection.SERVICE_ID, mEndpointDiscoveryCallback, discoveryOptions)
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
    public void authenticate(String authToken, NearbyConnectionPeer peer) {
        // do no authentication

        if(authToken!=null) authenticationCallbacks.onAuthenticationSuccess(peer);

        else authenticationCallbacks.onAuthenticationFailed("no authentication token", peer);
    }

    @Override
    public void sendDataToConnectedReceiver(NearbyConnectionPeer peer, byte[] dataToSend) {

        if(peer == null){

            senderCallbacks.onDataSendFailed(peer, "no connected peer connected");
            return;
        }

        Payload bytesPayload = Payload.fromBytes(dataToSend);
        String peerId = peer.getmPeerId();
        Nearby.getConnectionsClient(mContext).sendPayload(peerId, bytesPayload);
    }

    @Override
    public void requestConnection(NearbyConnectionPeer peer) {

        Nearby.getConnectionsClient(mContext).requestConnection(me.getUsername(), peer.getmPeerId(), mConnectionLifecycleCallback)

                .addOnSuccessListener(aVoid -> {
                    // We successfully requested a connection. Now both sides
                    // must accept before the connection is established
                    Log.d(TAG, "requestConnection: connection requested");
                })

                .addOnFailureListener(e -> {

                    Log.d(TAG, "requestConnection: connection request failed -> "+e.getMessage());
                    connectionCallbacks.onConnectionSetupError(e.getMessage());
                });
    }


    @Override
    public void connect(NearbyConnectionPeer peer) {

        Nearby.getConnectionsClient(mContext).acceptConnection(peer.getmPeerId(), mPayloadCallback);
    }

    @Override
    public void disconnect(NearbyConnectionPeer peer) {

        Nearby.getConnectionsClient(mContext).disconnectFromEndpoint(peer.getmPeerId());
    }

    @Override
    public void stopReceiversDiscovery() {

        Nearby.getConnectionsClient(mContext).stopDiscovery();
    }
}
