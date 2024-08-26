package dev.adamag.roomlibrary;

import android.content.Context;

import androidx.room.Room;
import androidx.room.RoomDatabase;

public class RoomHelper {

    private static RoomHelper instance;
    private RoomDatabase database;

    private RoomHelper() {
        // Private constructor to enforce Singleton pattern
    }

    public static synchronized RoomHelper getInstance() {
        if (instance == null) {
            instance = new RoomHelper();
        }
        return instance;
    }

    /**
     * Initializes the Room database.
     *
     * @param context The application context.
     * @param dbClass The RoomDatabase class.
     * @param dbName  The name of the database.
     * @param <T>     The type of the RoomDatabase.
     */
    public <T extends RoomDatabase> void initDatabase(Context context, Class<T> dbClass, String dbName) {
        if (database == null) {
            database = Room.databaseBuilder(context.getApplicationContext(), dbClass, dbName)
                    .fallbackToDestructiveMigration() // Handle migrations (use with caution)
                    .build();
        }
    }

    /**
     * Gets the initialized Room database.
     *
     * @param <T> The type of the RoomDatabase.
     * @return The RoomDatabase instance.
     */
    @SuppressWarnings("unchecked")
    public <T extends RoomDatabase> T getDatabase() {
        if (database == null) {
            throw new IllegalStateException("Database is not initialized. Call initDatabase() first.");
        }
        return (T) database;
    }

    /**
     * Closes the database connection.
     */
    public void closeDatabase() {
        if (database != null && database.isOpen()) {
            database.close();
            database = null;
        }
    }
}
