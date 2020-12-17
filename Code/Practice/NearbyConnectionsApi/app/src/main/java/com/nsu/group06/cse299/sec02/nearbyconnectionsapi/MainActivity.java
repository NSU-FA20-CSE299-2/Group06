package com.nsu.group06.cse299.sec02.nearbyconnectionsapi;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;
import com.nsu.group06.cse299.sec02.nearbyconnectionsapi.nearbyConnections.NearbyConnection;
import com.nsu.group06.cse299.sec02.nearbyconnectionsapi.nearbyConnections.NearbyConnectionPeer;
import com.nsu.group06.cse299.sec02.nearbyconnectionsapi.nearbyConnections.NearbyHelpPost;
import com.nsu.group06.cse299.sec02.nearbyconnectionsapi.nearbyConnections.nearbyConnectionsApiAdapters.NearbyConnectionsApiAdapterReceiver;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MA-debug";

    // ui
    private TextView receivedDataTextView;

    // model
    private NearbyHelpPost mReceivedNearbyHelpPost;

    /*
     Nearby connection variables START
     */
    private NearbyConnection.Receiver mNearbyReceiver;

    private NearbyConnection.ReceiverCallbacks mReceiverCallbacks = new NearbyConnection.ReceiverCallbacks() {
        @Override
        public void onAdvertisementError(String message) {

            Log.d(TAG, "onAdvertisementError: error -> "+message);
            showToast("error starting advertisement!");
        }

        @Override
        public void onDataReceived(NearbyHelpPost receivedData) {

            Log.d(TAG, "onDataReceived: data received from -> "+receivedData.getmAuthor());
            mReceivedNearbyHelpPost = receivedData;
            showReceivedData(receivedData.toString());
        }

        @Override
        public void onDataReceiveFailed(String message) {

            Log.d(TAG, "onDataReceiveFailed: error -> "+message);
            showToast("failed to receive data!");
        }
    };

    private NearbyConnection.AuthenticationCallbacks mReceiverAuthenticationCallbacks = new NearbyConnection.AuthenticationCallbacks() {
        @Override
        public void onAuthenticationSuccess(NearbyConnectionPeer peer) {

            if(mNearbyReceiver!=null) mNearbyReceiver.connect(peer);
            else {

                Log.d(TAG, "onAuthenticationSuccess: null pointer exception");
            }
        }

        @Override
        public void onAuthenticationFailed(String message, NearbyConnectionPeer peer) {

            if(mNearbyReceiver!=null) mNearbyReceiver.rejectConnection(peer);

            Log.d(TAG, "onAuthenticationFailed: error -> "+message);
            showToast("authentication failed!");
        }
    };

    private NearbyConnection.ConnectionCallbacks mReceiverConnectionCallbacks = new NearbyConnection.ConnectionCallbacks() {
        @Override
        public void onConnectionInitiated(NearbyConnectionPeer peer) {

            if(mNearbyReceiver!=null) mNearbyReceiver.authenticate("dummy-auth-token", peer);

            else Log.d(TAG, "onConnectionInitiated: null pointer exception");
        }

        @Override
        public void onConnectionEstablished(NearbyConnectionPeer peer) {
        // sit back and wait for data

            showToast("nearby user found!");
            Log.d(TAG, "onConnectionEstablished: connection established!");
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
     Nearby connection variables END
     */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();
    }

    @Override
    protected void onStop() {
        super.onStop();

        if(mNearbyReceiver!=null) mNearbyReceiver.stopAdvertising();
    }

    private void init() {

        receivedDataTextView = findViewById(R.id.receivedMessage_TextView);

        getNearbyPermissions();
    }

    /*
    Ask user for permissions required to user nearby api
    using the open source library- <https://github.com/Karumi/Dexter>
     */
    private void getNearbyPermissions() {

        Dexter.withContext(this)
                .withPermissions(
                        Manifest.permission.BLUETOOTH,
                        Manifest.permission.BLUETOOTH_ADMIN,
                        Manifest.permission.ACCESS_WIFI_STATE,
                        Manifest.permission.CHANGE_WIFI_STATE,
                        Manifest.permission.ACCESS_COARSE_LOCATION,
                        Manifest.permission.ACCESS_FINE_LOCATION
                )
                .withListener(new MultiplePermissionsListener() {
                    @Override
                    public void onPermissionsChecked(MultiplePermissionsReport multiplePermissionsReport) {

                        if(multiplePermissionsReport.areAllPermissionsGranted()){

                            startNearbyConnections();
                        }

                        else{

                            showNearbyPermissionExplanationDialog(multiplePermissionsReport.isAnyPermissionPermanentlyDenied());
                        }
                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(List<PermissionRequest> list, PermissionToken permissionToken) {
                    // ignore for now
                        permissionToken.continuePermissionRequest();
                    }

                }).check();
    }

    /*
    start advertising device for nearby connections
     */
    private void startNearbyConnections() {

        mNearbyReceiver = new NearbyConnectionsApiAdapterReceiver(this, new NearbyConnectionPeer("demichi", ""),
                mReceiverCallbacks, mReceiverAuthenticationCallbacks, mReceiverConnectionCallbacks);

        mNearbyReceiver.advertiseToSenders();
    }

    /*
    show alert dialog explaining why location permission is a MUST
    with a simple dialog, quit activity if permission is permanently denied
    courtesy - <https://stackoverflow.com/questions/26097513/android-simple-alert-dialog
     */
    private void showNearbyPermissionExplanationDialog(boolean isPermissionPermanentlyDenied) {

        AlertDialog alertDialog = new AlertDialog.Builder(MainActivity.this).create();

        // TODO: bring in the strings from res/values/strings.xml
        String title = "Nearby Connections Permission";
        String explanation;

        if(isPermissionPermanentlyDenied)
            explanation = "Please allow required permissions from for the app from your device Settings.";
        else
            explanation = "Location, WiFi and BLE access permission is required to connection with nearby devices, permission MUST be provided!";

        alertDialog.setTitle(title);
        alertDialog.setMessage(explanation);
        alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                (dialog, which) -> {
                    if(!isPermissionPermanentlyDenied)
                        getNearbyPermissions();
                    else
                        finish();
                });

        alertDialog.show();
    }

    private void showToast(String message){

        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    private void showReceivedData(String data){

        if(receivedDataTextView!=null){

            receivedDataTextView.setVisibility(View.VISIBLE);
            receivedDataTextView.setText(mReceivedNearbyHelpPost.toString());
        }
    }


    public void proceedToSendDataClick(View view) {

        startActivity(new Intent(this, SendMessageActivity.class));
    }
}