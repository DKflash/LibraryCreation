package dev.adamag.roomlibrary;

import androidx.lifecycle.LiveData;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Update;

import java.util.List;

public interface DaoInterface<T> {

    @Insert
    long insert(T entity);

    @Update
    void update(T entity);

    @Delete
    void delete(T entity);

    LiveData<List<T>> getAll();
}
