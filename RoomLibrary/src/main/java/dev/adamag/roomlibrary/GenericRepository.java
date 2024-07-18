package dev.adamag.roomlibrary;

import java.util.concurrent.Executors;

public class GenericRepository<T extends BaseEntity> {

    private final BaseDao<T> baseDao;

    public GenericRepository(BaseDao<T> baseDao) {
        this.baseDao = baseDao;
    }

    public void insert(T entity, Callback<Void> callback) {
        Executors.newSingleThreadExecutor().execute(() -> {
            baseDao.insert(entity);
            if (callback != null) {
                callback.onComplete(null);
                System.out.println(callback);

            }
        });
    }

    public void update(T entity, Callback<Void> callback) {
        Executors.newSingleThreadExecutor().execute(() -> {
            baseDao.update(entity);
            if (callback != null) {
                callback.onComplete(null);
            }
        });
    }

    public void delete(T entity, Callback<Void> callback) {
        Executors.newSingleThreadExecutor().execute(() -> {
            baseDao.delete(entity);
            if (callback != null) {
                callback.onComplete(null);
            }
        });
    }

    public interface Callback<T> {
        void onComplete(T result);
    }
}
