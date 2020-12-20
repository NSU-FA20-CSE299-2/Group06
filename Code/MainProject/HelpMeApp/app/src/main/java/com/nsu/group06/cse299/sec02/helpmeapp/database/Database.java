package com.nsu.group06.cse299.sec02.helpmeapp.database;

/*
    Wrapper interface for two types of database operation classes which are
    abstraction for database CRUD operations and Realtime data change trigger
 */
public interface Database {

    /*
    CRUD single operations
     */
    abstract class SingleOperationDatabase<T>{

        protected SingleOperationDatabaseCallback singleOperationDatabaseCallback;

        public SingleOperationDatabase() {
        }

        public SingleOperationDatabase(SingleOperationDatabaseCallback singleOperationDatabaseCallback) {
            this.singleOperationDatabaseCallback = singleOperationDatabaseCallback;
        }

        abstract public void create(T data);
        abstract public void read(); // read single data
        abstract public void update(T data);
        abstract public void delete(T data);

        /*
        callback for a single database operation event (read once)
         */
        public interface SingleOperationDatabaseCallback<T> extends FailureStatus{

            void onDataRead(T data);
        }

        public SingleOperationDatabaseCallback getSingleOperationDatabaseCallback() {
            return singleOperationDatabaseCallback;
        }

        public void setSingleOperationDatabaseCallback(SingleOperationDatabaseCallback singleOperationDatabaseCallback) {
            this.singleOperationDatabaseCallback = singleOperationDatabaseCallback;
        }
    }

    /*
    Realtime operations
     */
    abstract class RealtimeDatabase<T>{

        protected RealtimeChangesDatabaseCallback realtimeChangesDatabaseCallback;

        public RealtimeDatabase() {
        }

        public RealtimeDatabase(RealtimeChangesDatabaseCallback realtimeChangesDatabaseCallback) {
            this.realtimeChangesDatabaseCallback = realtimeChangesDatabaseCallback;
        }

        abstract public void listenForSingleDataChange();
        abstract public void listenForListDataChange();

        abstract public void stopListeningForDataChange();

        /*
        callbacks for realtime changes in database
         */
        public interface RealtimeChangesDatabaseCallback<T> extends FailureStatus{

            void onDataAddition(T data);
            void onDataUpdate(T data);
            void onDataDeletion(T data);
        }

        public RealtimeChangesDatabaseCallback getRealtimeChangesDatabaseCallback() {
            return realtimeChangesDatabaseCallback;
        }

        public void setRealtimeChangesDatabaseCallback(RealtimeChangesDatabaseCallback realtimeChangesDatabaseCallback) {
            this.realtimeChangesDatabaseCallback = realtimeChangesDatabaseCallback;
        }
    }


    /*
    callback for database operation fail
     */
    interface FailureStatus{

        void onDatabaseOperationFailed(String message);
    }

}
