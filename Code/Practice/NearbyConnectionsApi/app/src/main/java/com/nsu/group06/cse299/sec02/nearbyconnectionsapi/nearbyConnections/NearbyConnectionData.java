package com.nsu.group06.cse299.sec02.nearbyconnectionsapi.nearbyConnections;

import java.io.IOException;
import java.io.Serializable;

/*
Abstraction for data to be transferred model class for nearby connection
transferring data MUST be a byte array
 */
                                           // because we need to convert the model to byte array
public interface NearbyConnectionData extends Serializable {

    /*
    convert model class's attributes to byte array
     */
    byte[] toByteArray() throws IOException;
}
