package com.nsu.group06.cse299.sec02.nearbyconnectionsapi.nearbyConnections.nearbyConnectionsApiAdapters;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.nearby.Nearby;
import com.google.android.gms.nearby.connection.AdvertisingOptions;
import com.google.android.gms.nearby.connection.ConnectionInfo;
import com.google.android.gms.nearby.connection.ConnectionLifecycleCallback;
import com.google.android.gms.nearby.connection.ConnectionResolution;
import com.google.android.gms.nearby.connection.ConnectionsStatusCodes;
import com.google.android.gms.nearby.connection.Payload;
import com.google.android.gms.nearby.connection.PayloadCallback;
import com.google.android.gms.nearby.connection.PayloadTransferUpdate;
import com.nsu.group06.cse299.sec02.nearbyconnectionsapi.nearbyConnections.NearbyConnection;
import com.nsu.group06.cse299.sec02.nearbyconnectionsapi.nearbyConnections.NearbyConnectionPeer;
import com.nsu.group06.cse299.sec02.nearbyconnectionsapi.nearbyConnections.NearbyHelpPost;

import java.io.IOException;

/*
Receiver adapter of nearby connections api
disconnects immediately after receiveing data
 */
public class NearbyConnectionsApiAdapterReceiver extends NearbyConnection.Receiver {

    private static final String TAG = "NCAAR-debug";

    private Context mContext;

    private final ConnectionLifecycleCallback mConnectionLifecycleCallback =
            new ConnectionLifecycleCallback() {
                @Override
                public void onConnectionInitiated(@NonNull String endPointId, @NonNull ConnectionInfo connectionInfo) {

                    connectionCallbacks.onConnectionInitiated();
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

                    try {

                        receiverCallbacks.onDataReceived(NearbyHelpPost.toNearbyHelpPost(payload.asBytes()));
                        Log.d(TAG, "onPayloadReceived: receved payload!");

                    } catch (IOException | ClassNotFoundException e) {

                        receiverCallbacks.onDataReceiveFailed(e.getMessage());
                        Log.d(TAG, "onPayloadReceived: failed to receive payload -> "+e.getMessage());

                    }
                }

                @Override
                public void onPayloadTransferUpdate(@NonNull String endPointId, @NonNull PayloadTransferUpdate payloadTransferUpdate) {

                    NearbyConnectionPeer receiver = new NearbyConnectionPeer(endPointId);

                    if (payloadTransferUpdate.getStatus() == ConnectionsStatusCodes.ERROR){

                        receiverCallbacks.onDataReceiveFailed("connection error! " +
                                "data receive failed from peer -> " +receiver.getmPeerId());
                    }

                }
            };

    public NearbyConnectionsApiAdapterReceiver(Context mContext, NearbyConnectionPeer me,
                                               NearbyConnection.ReceiverCallbacks receiverCallbacks,
                                               NearbyConnection.AuthenticationCallbacks authenticationCallbacks,
                                               NearbyConnection.ConnectionCallbacks connectionCallbacks) {
        super(me, receiverCallbacks, authenticationCallbacks, connectionCallbacks);
        this.mContext = mContext;
    }

    @Override
    public void advertiseToSenders() {

        AdvertisingOptions advertisingOptions =
                new AdvertisingOptions.Builder().setStrategy(NearbyConnection.STRATEGY).build();

        Nearby.getConnectionsClient(mContext)
                .startAdvertising(
                        me.getUsername(), NearbyConnection.SERVICE_ID,
                        mConnectionLifecycleCallback, advertisingOptions
                )

                .addOnSuccessListener(

                        (Void unused) -> {
                            // We're advertising!
                            Log.d(TAG, "advertiseToSenders: advertising!");
                        }
                )

                .addOnFailureListener(

                        (Exception e) -> {
                            // We were unable to start advertising.
                            Log.d(TAG, "advertiseToSenders: failed to advertise -> "+e.getMessage());
                            receiverCallbacks.onAdvertisementError(e.getMessage());
                        }

                );
    }

    @Override
    public void stopAdvertising() {

        Nearby.getConnectionsClient(mContext).stopAdvertising();
    }

    @Override
    public void authenticate() {
    // no authentication just call authentication success

        authenticationCallbacks.onAuthenticationSuccess();
    }

    @Override
    public void connect(NearbyConnectionPeer peer) {

        Nearby.getConnectionsClient(mContext).acceptConnection(peer.getmPeerId(), mPayloadCallback);
    }

    @Override
    public void rejectConnection(NearbyConnectionPeer peer) {

        Nearby.getConnectionsClient(mContext).rejectConnection(peer.getmPeerId());
    }

    @Override
    public void disconnect(NearbyConnectionPeer peer) {

        Nearby.getConnectionsClient(mContext).disconnectFromEndpoint(peer.getmPeerId());
    }
}
