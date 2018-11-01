package com.dbs.replsdk.persistence;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

/**
 * All Messages will be store in this In-Memory DB
 */
@Database(entities = Message.class, version = 1, exportSchema = false)
public abstract class MessageDatabase extends RoomDatabase {

    private static volatile MessageDatabase INSTANCE;

    /**
     * Create the singleton Database
     *
     * @param context context needed to build the databse
     * @return instance of the database
     */
    public static MessageDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (MessageDatabase.class) {
                if (INSTANCE == null) {
                    // Create database here
                    INSTANCE = Room.
                            inMemoryDatabaseBuilder(context.getApplicationContext(), MessageDatabase.class)
                            .build();
                }
            }
        }
        return INSTANCE;
    }

    public abstract MessageDao getMessageDao();

}
