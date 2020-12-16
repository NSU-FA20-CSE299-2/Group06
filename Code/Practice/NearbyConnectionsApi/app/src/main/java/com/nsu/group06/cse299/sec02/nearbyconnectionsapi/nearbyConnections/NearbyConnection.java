package com.nsu.group06.cse299.sec02.nearbyconnectionsapi.nearbyConnections;

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

    /*
    Abstract class for senders
    who are discoverers i.e search for nearby receivers
     */
    abstract class Sender{

        protected byte[] dataToSend;
        protected List<NearbyConnectionPeer> alreadySentReceivers = new ArrayList<>();

        protected SenderCallbacks senderCallbacks;

        protected Authenticator authenticator;
        protected Connection connection;

        public Sender(byte[] dataToSend, SenderCallbacks senderCallbacks,
                      Authenticator authenticator, Connection connection) {

            this.dataToSend = dataToSend;
            this.senderCallbacks = senderCallbacks;
            this.authenticator = authenticator;
            this.connection = connection;
        }

        public abstract void discoverReceivers();
        public abstract void sendDataToConnectedReceiver(NearbyConnectionData data);

        public byte[] getDataToSend() {
            return dataToSend;
        }

        public void setDataToSend(byte[] dataToSend) {
            this.dataToSend = dataToSend;
        }

        public Connection getConnection() {
            return connection;
        }

        public void setConnection(Connection connection) {
            this.connection = connection;
        }

        public void setSenderCallbacks(SenderCallbacks senderCallbacks) {
            this.senderCallbacks = senderCallbacks;
        }

        public Authenticator getAuthenticator() {
            return authenticator;
        }

        public void setAuthenticator(Authenticator authenticator) {
            this.authenticator = authenticator;
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

        void onDataSent();
        void onDataSendFailed(String message);
    }



    /*
    Abstract class for receiver
    who are advertisers i.e advertise for nearby senders to find them
     */
    abstract class Receiver{

        protected ReceiverCallbacks receiverCallbacks;

        protected Authenticator authenticator;
        protected Connection connection;

        public Receiver(ReceiverCallbacks receiverCallbacks, Authenticator authenticator,
                        Connection connection) {

            this.receiverCallbacks = receiverCallbacks;
            this.authenticator = authenticator;
            this.connection = connection;
        }

        public abstract void advertiseToSenders();

        public void setReceiverCallbacks(ReceiverCallbacks receiverCallbacks) {
            this.receiverCallbacks = receiverCallbacks;
        }

        public Authenticator getAuthenticator() {
            return authenticator;
        }

        public void setAuthenticator(Authenticator authenticator) {
            this.authenticator = authenticator;
        }

        public Connection getConnection() {
            return connection;
        }

        public void setConnection(Connection connection) {
            this.connection = connection;
        }
    }
    /*
    Interface for NearbyConnection.Receiver callbacks
     */
    interface ReceiverCallbacks{

        void onAdvertisementSuccess();
        void onAdvertisementFailed(String message);

        void onDataReceived(NearbyConnectionData receivedData);
        void onDataReceiveFailed(String message);
    }



    /*
    Abstract class to handle connection for one peer
     */
    abstract class Connection{

        protected NearbyConnectionPeer me, peer;
        protected ConnectionCallbacks connectionCallbacks;

        public Connection(NearbyConnectionPeer me, ConnectionCallbacks connectionCallbacks) {
            this.me = me;
            this.connectionCallbacks = connectionCallbacks;
        }

        public NearbyConnectionPeer getMe() {
            return me;
        }

        public void setMe(NearbyConnectionPeer me) {
            this.me = me;
        }

        public NearbyConnectionPeer getPeer() {
            return peer;
        }

        public void setPeer(NearbyConnectionPeer peer) {
            this.peer = peer;
        }

        public void setConnectionCallbacks(ConnectionCallbacks connectionCallbacks) {
            this.connectionCallbacks = connectionCallbacks;
        }

        public abstract void requestConnection(NearbyConnectionPeer peer); // request for new connection
        public abstract void rejectConnection(NearbyConnectionPeer peer); // reject incoming connection request

        public abstract void connect(NearbyConnectionPeer peer); // establish a connection
        public abstract void disconnect(NearbyConnectionPeer peer); // disconnect an active connection
    }
    /*
    Interface for connection status callbacks
     */
    interface ConnectionCallbacks{

        void onConnectionInitiated(); // new connection initiated
        void onConnectionEstablished(NearbyConnectionPeer receiver);
        void onConnectionRejected(NearbyConnectionPeer receiver);
        void onConnectionSetupError(NearbyConnectionPeer receiver);
    }



    /*
    Abstract class for authenticating nearby connections

    'AuthToken' is the data that will be used for authentication
    should be a string most of the time
    */
    abstract class Authenticator<AuthTokenType>{

        private AuthTokenType mAuthToken;
        private AuthenticationCallbacks mAuthenticationCallbacks;

        public Authenticator(AuthTokenType mAuthToken, AuthenticationCallbacks mAuthenticationCallbacks) {
            this.mAuthToken = mAuthToken;
            this.mAuthenticationCallbacks = mAuthenticationCallbacks;
        }

        // implementation of this method will be different for sender and receiver
        public abstract void authenticate();
    }
    /*
    Interface for NearbyConnection.Authenticator
     */
    interface AuthenticationCallbacks{

        void onAuthenticationSuccess();
        void onAuthenticationFailed(String message);
    }

}
