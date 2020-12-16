package com.nsu.group06.cse299.sec02.nearbyconnectionsapi.nearbyConnections.nearbyConnectionsApiAdapters;

import com.nsu.group06.cse299.sec02.nearbyconnectionsapi.nearbyConnections.NearbyConnection;

public class NearbyConnectionCustomAuthenticator extends NearbyConnection.Authenticator<String> {

    public NearbyConnectionCustomAuthenticator(String mAuthToken, NearbyConnection.AuthenticationCallbacks mAuthenticationCallbacks) {
        super(mAuthToken, mAuthenticationCallbacks);
    }

    @Override
    public void authenticate() {

        // TODO: do authentication

        // no authentication for now
        authenticationCallbacks.onAuthenticationSuccess();
    }
}
