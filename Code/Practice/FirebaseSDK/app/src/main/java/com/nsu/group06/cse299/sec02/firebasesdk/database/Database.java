package com.nsu.group06.cse299.sec02.firebasesdk.database;

/*
    Abstraction for database CRUD operations and Realtime data change trigger classes
 */
public abstract class Database<T> {

    abstract void create(T data);
    abstract void read(QuerySpecification querySpecification); // read single data
    abstract void update(T data);
    abstract void delete(T data);

    abstract void listenForDataChange(QuerySpecification querySpecification);

    /*
        callbacks for any database operation events
     */
    public interface DatabaseCallbacks<T>{

        // any of the operation success or failure
        void onDatabaseOperationSuccess(String message);
        void onDatabaseOperationFailed(String message);

        void onDataRead(T data); // single data read

        void onDataAddition(T data);
        void onDataUpdat(T data);
        void onDataDeletion(T data);
    }

}
