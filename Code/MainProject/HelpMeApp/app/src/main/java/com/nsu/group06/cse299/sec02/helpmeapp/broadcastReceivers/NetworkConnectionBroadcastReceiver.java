package com.nsu.group06.cse299.sec02.helpmeapp.broadcastReceivers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class NetworkConnectionBroadcastReceiver extends BroadcastReceiver {

    private InternetStatusCallback mInternetStatusCallback;

    public NetworkConnectionBroadcastReceiver(){
        // required default constructor
    }

    public NetworkConnectionBroadcastReceiver(InternetStatusCallback mInternetStatusCallback) {
        this.mInternetStatusCallback = mInternetStatusCallback;
    }

    @Override
    public void onReceive(Context context, Intent intent) {

        if(isInternetAvailable(context)){

            mInternetStatusCallback.onInternetAvailable();
        }

        else mInternetStatusCallback.onInternetNotAvailable();
    }

    private boolean isInternetAvailable(Context context){

        ConnectivityManager connectivityManager
                = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();

        return networkInfo!=null && networkInfo.isConnected();
    }

    public interface InternetStatusCallback{

        void onInternetAvailable();
        void onInternetNotAvailable();
    }
}