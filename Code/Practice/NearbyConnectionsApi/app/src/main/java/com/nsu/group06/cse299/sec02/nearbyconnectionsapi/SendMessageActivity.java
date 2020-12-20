package com.nsu.group06.cse299.sec02.nearbyconnectionsapi;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.nsu.group06.cse299.sec02.nearbyconnectionsapi.nearbyConnections.NearbyConnection;
import com.nsu.group06.cse299.sec02.nearbyconnectionsapi.nearbyConnections.NearbyConnectionPeer;
import com.nsu.group06.cse299.sec02.nearbyconnectionsapi.nearbyConnections.NearbyHelpPost;
import com.nsu.group06.cse299.sec02.nearbyconnectionsapi.nearbyConnections.nearbyConnectionsApiAdapters.NearbyConnectionsApiAdapterSender;

import java.io.IOException;

public class SendMessageActivity extends AppCompatActivity {

    private static final String TAG = "SMA-debug";

    // ui
    private EditText mMessageEditText;
    private Button mSendButton;

    // model
    private NearbyHelpPost mHelpPost;
    private NearbyConnectionPeer mMe;

    /*
    Nearby connection Sender variables START
     */

    private NearbyConnection.Sender mNearbySender;

    private NearbyConnection.SenderCallbacks mSenderCallbacks = new NearbyConnection.SenderCallbacks() {
        @Override
        public void onReceiverDiscovered(NearbyConnectionPeer receiver) {

            if(mNearbySender!=null) {
                
                if (!mNearbySender.isAlreadySentReceiver(receiver)){

                    mNearbySender.requestConnection(receiver);
                    Log.d(TAG, "onReceiverDiscovered: sending to new user -> "+receiver.getmPeerId());
                }
            }
            
            else Log.d(TAG, "onReceiverDiscovered: null pointer exception");
        }

        @Override
        public void onDiscoveryError(String message) {

            Log.d(TAG, "onDiscoveryError: error -> "+message);
        }

        @Override
        public void onDataSendSuccess(NearbyConnectionPeer receiver) {

            showToast("data sent to user, "+receiver.getUsername());

            if(mNearbySender!=null) {
             
                mNearbySender.addToAlreadySentReceivers(receiver);

                Log.d(TAG, "onDataSendSuccess: sent to user -> "+receiver.getmPeerId());

                // MUST disconnect from both ends
                mNearbySender.disconnect(receiver);
            }
            
            else Log.d(TAG, "onDataSendSuccess: null pointer exception");
        }

        @Override
        public void onDataSendFailed(NearbyConnectionPeer receiver, String message) {

            mNearbySender.disconnect(receiver);
            Log.d(TAG, "onDataSendFailed: error -> "+message);
        }
    };

    private NearbyConnection.AuthenticationCallbacks mSenderAuthenticationCallbacks = new NearbyConnection.AuthenticationCallbacks() {
        @Override
        public void onAuthenticationSuccess(NearbyConnectionPeer peer) {

            if(mNearbySender!=null) mNearbySender.connect(peer);
            
            else Log.d(TAG, "onAuthenticationSuccess: null pointer exception");
        }

        @Override
        public void onAuthenticationFailed(String message, NearbyConnectionPeer peer) {

            showToast("authentication failed!");
            Log.d(TAG, "onAuthenticationFailed: -> "+message);
        }
    };
    
    private NearbyConnection.ConnectionCallbacks mSenderConnectionCallbacks = new NearbyConnection.ConnectionCallbacks() {
        @Override
        public void onConnectionInitiated(NearbyConnectionPeer peer) {
            
           if(mNearbySender!=null) mNearbySender.authenticate("dummy-auth-token", peer);
           
           else Log.d(TAG, "onConnectionInitiated: null pointer exception");
        }

        @Override
        public void onConnectionEstablished(NearbyConnectionPeer peer) {

            try {

                byte[] data = NearbyHelpPost.toByteArray(mHelpPost);

                mNearbySender.sendDataToConnectedReceiver(peer, data);

            } catch (IOException e) {

                e.printStackTrace();

            } catch (NullPointerException e){

                Log.d(TAG, "onConnectionEstablished: null pointer exception");
            }
        }

        @Override
        public void onConnectionRejected(NearbyConnectionPeer peer) {

            Log.d(TAG, "onConnectionRejected: connection rejected by -> "+peer.getmPeerId());
        }

        @Override
        public void onConnectionDisconnected(NearbyConnectionPeer peer) {

            Log.d(TAG, "onConnectionDisconnected: connection disconnected -> "+peer.getmPeerId());
        }

        @Override
        public void onConnectionSetupError(String message) {

            Log.d(TAG, "onConnectionSetupError: error -> "+message);
            showToast("error setting up connection");
        }
    };
    /*
    Nearby connection Sender variables END
     */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_message);

        init();
    }

    @Override
    protected void onStop() {
        super.onStop();

        if(mNearbySender!=null) mNearbySender.stopReceiversDiscovery();
    }

    private void init() {

        mMessageEditText = findViewById(R.id.message_EditText);
        mSendButton = findViewById(R.id.send_Button);

        mMe = new NearbyConnectionPeer();
        mMe.setUsername("shelock221b");

        mNearbySender = new NearbyConnectionsApiAdapterSender(this, mMe,
                mSenderCallbacks, mSenderConnectionCallbacks, mSenderAuthenticationCallbacks);
    }

    /*
    User clicked send message button
     */
    public void sendClicked(View view) {

        String message = mMessageEditText.getText().toString();

        mHelpPost = new NearbyHelpPost(mMe.getUsername(), message, 23.798856, 90.358793);
        mHelpPost.setmWebPageUrl("www.helpme.bd.org/posts/2394");

        if(mHelpPost.isValid()){
            startReceiversDiscovery();

            mMessageEditText.setEnabled(false);

            mSendButton.setEnabled(false);
            mSendButton.setText("sending message...");
        }

        else showToast("empty field!");
    }

    /*
    Start searching for nearby users to send mNearbyHelpPost
     */
    private void startReceiversDiscovery() {


        mNearbySender.discoverReceivers();
    }

    private void showToast(String message){

        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}