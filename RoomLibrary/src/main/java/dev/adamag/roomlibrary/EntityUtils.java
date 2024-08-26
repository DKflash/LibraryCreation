package dev.adamag.roomlibrary;

import java.util.concurrent.Callable;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import androidx.lifecycle.LiveData;

import java.util.List;
import java.util.concurrent.Future;

public class EntityUtils {

    // Executor to run database operations asynchronously
    private static final Executor executor = Executors.newSingleThreadExecutor();

    /**
     * Inserts an entity asynchronously.
     *
     * @param dao      The DAO interface to access the database.
     * @param entity   The entity object to insert.
     * @param <T>      The entity type.
     */
    public static <T> long insertAndReturnId(final DaoInterface<T> dao, final T entity) {
        Callable<Long> insertTask = () -> dao.insert(entity);
        Future<Long> future = Executors.newSingleThreadExecutor().submit(insertTask);
        try {
            return future.get(); // Returns the generated ID
        } catch (Exception e) {
            e.printStackTrace();
            return -1; // Return -1 if an error occurs
        }
    }

    /**
     * Updates an entity asynchronously.
     *
     * @param dao      The DAO interface to access the database.
     * @param entity   The entity object to update.
     * @param <T>      The entity type.
     */
    public static <T> void updateAsync(final DaoInterface<T> dao, final T entity) {
        executor.execute(() -> dao.update(entity));
    }

    /**
     * Deletes an entity asynchronously.
     *
     * @param dao      The DAO interface to access the database.
     * @param entity   The entity object to delete.
     * @param <T>      The entity type.
     */
    public static <T> void deleteAsync(final DaoInterface<T> dao, final T entity) {
        executor.execute(() -> dao.delete(entity));
    }

    /**
     * Retrieves all entities from the table.
     *
     * @param dao      The DAO interface to access the database.
     * @param <T>      The entity type.
     * @return         A LiveData list of entities.
     */
    public static <T> LiveData<List<T>> getAllEntities(final DaoInterface<T> dao) {
        return dao.getAll();
    }
}
