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

        public abstract void createWithId(String id, T data);
        public abstract void create(T data);
        public abstract void readSingle(); // read single data
        public abstract void readList(); // read list matching query
        public abstract void update(T data);
        public abstract void delete(T data);

        /*
        callback for a single database operation event (read once)
         */
        public interface SingleOperationDatabaseCallback<T> extends StatusUpdate{

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
    abstract class RealtimeDatabase{

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
        public interface RealtimeChangesDatabaseCallback<T> extends StatusUpdate{

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
    callback for database operation status
     */
    interface StatusUpdate{

        void onDatabaseOperationSuccess();
        void onDatabaseOperationFailed(String message);
    }

}
