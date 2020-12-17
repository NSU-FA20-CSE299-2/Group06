package com.nsu.group06.cse299.sec02.nearbyconnectionsapi.nearbyConnections;

import com.google.android.gms.nearby.connection.Strategy;

import java.util.ArrayList;
import java.util.List;

/*
Wrapper interface for for all nearby connection classes

- One-to-one connections
- sender looks for recevers
- connects to one receiver and sends data
- sender looks for another receiver and repeats

transfer of data is only bytes(Serializable Object)
 */
public interface NearbyConnection {

    // TODO: set to app package name
    // id that advertiser is advertising with, MUST be unique to the app
    String SERVICE_ID = "com.nsu.group06.cse299.sec02";

    // only setup one-to-one connections
    // <https://developers.google.com/nearby/connections/strategies#p2p_point_to_point>
    Strategy STRATEGY = Strategy.P2P_POINT_TO_POINT;



    /*
    Abstract class for senders
    who are discoverers i.e search for nearby receivers
     */
    abstract class Sender{

        protected NearbyConnectionPeer me;
        protected List<NearbyConnectionPeer> alreadySentReceivers = new ArrayList<>();

        protected SenderCallbacks senderCallbacks;
        protected ConnectionCallbacks connectionCallbacks;

        protected AuthenticationCallbacks authenticationCallbacks;

        public Sender(NearbyConnectionPeer me, SenderCallbacks senderCallbacks,
                      ConnectionCallbacks connectionCallbacks, AuthenticationCallbacks authenticationCallbacks) {

            this.me = me;
            this.senderCallbacks = senderCallbacks;
            this.connectionCallbacks = connectionCallbacks;
            this.authenticationCallbacks = authenticationCallbacks;
        }

        public abstract void discoverReceivers();
        public abstract void stopReceiversDiscovery();
        public abstract void authenticate(String authToken, NearbyConnectionPeer peer);
        public abstract void sendDataToConnectedReceiver(NearbyConnectionPeer peer, byte[] data);
        public abstract void requestConnection(NearbyConnectionPeer peer); // request for new connection
        public abstract void connect(NearbyConnectionPeer peer); // establish a connection
        public abstract void disconnect(NearbyConnectionPeer peer); // disconnect an active connection

        public NearbyConnectionPeer getMe() {
            return me;
        }

        public void setMe(NearbyConnectionPeer me) {
            this.me = me;
        }

        public void setSenderCallbacks(SenderCallbacks senderCallbacks) {
            this.senderCallbacks = senderCallbacks;
        }

        public void setConnectionCallbacks(ConnectionCallbacks connectionCallbacks) {
            this.connectionCallbacks = connectionCallbacks;
        }

        public void setAuthenticationCallbacks(AuthenticationCallbacks authenticationCallbacks) {
            this.authenticationCallbacks = authenticationCallbacks;
        }

        public void addToAlreadySentReceivers(NearbyConnectionPeer receiver){

            alreadySentReceivers.add(receiver);
        }

        public boolean isAlreadySentReceiver(NearbyConnectionPeer receiver){

            return alreadySentReceivers.contains(receiver);
        }

        public void clearAlreadySentReceivers(){

            alreadySentReceivers.clear();
        }
    }
    /*
    Interface for NearbyConnection.Sender callbacks
     */
    interface SenderCallbacks{

        void onReceiverDiscovered(NearbyConnectionPeer receiver);
        void onDiscoveryError(String message);

        void onDataSendSuccess(NearbyConnectionPeer receiver);
        void onDataSendFailed(NearbyConnectionPeer receiver, String message);
    }



    /*
    Abstract class for receiver
    who are advertisers i.e advertise for nearby senders to find them
     */
    abstract class Receiver{

        protected NearbyConnectionPeer me;

        protected ReceiverCallbacks receiverCallbacks;
        protected AuthenticationCallbacks authenticationCallbacks;
        protected ConnectionCallbacks connectionCallbacks;

        public Receiver(NearbyConnectionPeer me, ReceiverCallbacks receiverCallbacks,
                        AuthenticationCallbacks authenticationCallbacks, ConnectionCallbacks connectionCallbacks) {

            this.me = me;
            this.receiverCallbacks = receiverCallbacks;
            this.authenticationCallbacks = authenticationCallbacks;
            this.connectionCallbacks = connectionCallbacks;
        }

        public abstract void advertiseToSenders();
        public abstract void stopAdvertising();
        public abstract void authenticate(String authToken, NearbyConnectionPeer peer);
        public abstract void connect(NearbyConnectionPeer peer); // establish a connection
        public abstract void rejectConnection(NearbyConnectionPeer peer); // reject incoming connection request
        public abstract void disconnect(NearbyConnectionPeer peer); // disconnect an active connection

        public NearbyConnectionPeer getMe() {
            return me;
        }

        public void setMe(NearbyConnectionPeer me) {
            this.me = me;
        }

        public void setReceiverCallbacks(ReceiverCallbacks receiverCallbacks) {
            this.receiverCallbacks = receiverCallbacks;
        }

        public void setAuthenticationCallbacks(AuthenticationCallbacks authenticationCallbacks) {
            this.authenticationCallbacks = authenticationCallbacks;
        }

        public void setConnectionCallbacks(ConnectionCallbacks connectionCallbacks) {
            this.connectionCallbacks = connectionCallbacks;
        }
    }
    /*
    Interface for NearbyConnection.Receiver callbacks
     */
    interface ReceiverCallbacks{

        void onAdvertisementError(String message);

        void onDataReceived(NearbyHelpPost receivedData);
        void onDataReceiveFailed(String message);
    }


    /*
    Interface for connection status callbacks
     */
    interface ConnectionCallbacks{

        void onConnectionInitiated(NearbyConnectionPeer peer); // new connection initiated
        void onConnectionEstablished(NearbyConnectionPeer peer);
        void onConnectionRejected(NearbyConnectionPeer peer);
        void onConnectionDisconnected(NearbyConnectionPeer peer);
        void onConnectionSetupError(String message);
    }


    /*
    Interface for Authenticating a connection
     */
    interface AuthenticationCallbacks{

        void onAuthenticationSuccess(NearbyConnectionPeer peer);
        void onAuthenticationFailed(String message, NearbyConnectionPeer peer);
    }

}
