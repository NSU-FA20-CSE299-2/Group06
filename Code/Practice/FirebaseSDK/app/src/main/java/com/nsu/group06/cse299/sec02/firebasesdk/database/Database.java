package com.nsu.group06.cse299.sec02.firebasesdk.database;

/*
    Abstraction for database CRUD operations and Realtime data change trigger classes
 */
public abstract class Database<T> {

    protected DatabaseCallbacks<T> databaseCallbacks;

    public Database(DatabaseCallbacks<T> databaseCallbacks) {

        this.databaseCallbacks = databaseCallbacks;
    }

    abstract public void create(T data);
    abstract public void read(); // read single data
    abstract public void update(T data);
    abstract public void delete(T data);

    abstract public void listenForDataChange();
    abstract public void stopListeningForDataChange();
    
    public void setDatabaseCallbacks(DatabaseCallbacks<T> databaseCallbacks) {
        this.databaseCallbacks = databaseCallbacks;
    }

    /*
            callbacks for any database operation events
         */
    public interface DatabaseCallbacks<T>{

        // any of the operation success or failure
        void onDatabaseOperationSuccess(String message);
        void onDatabaseOperationFailed(String message);

        void onDataRead(T data); // single data read

        void onDataAddition(T data);
        void onDataUpdate(T data);
        void onDataDeletion(T data);
    }

}
