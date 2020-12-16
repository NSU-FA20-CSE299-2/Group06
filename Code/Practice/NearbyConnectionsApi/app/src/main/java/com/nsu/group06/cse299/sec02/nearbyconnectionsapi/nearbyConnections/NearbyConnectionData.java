package com.nsu.group06.cse299.sec02.nearbyconnectionsapi.nearbyConnections;

import java.io.Serializable;

/*
Abstraction for data to be transferred model class for nearby connection
transferring data MUST be a byte array
 */
                                           // because we need to convert the model to byte array
public abstract class NearbyConnectionData implements Serializable {

    public abstract byte[] toByteArray();
}
