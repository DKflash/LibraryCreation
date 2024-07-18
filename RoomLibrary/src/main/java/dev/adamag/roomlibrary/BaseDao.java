package dev.adamag.roomlibrary;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;


@Dao
public interface BaseDao<T extends BaseEntity> {
    @Insert
    void insert(T entity);

    @Update
    void update(T entity);

    @Delete
    void delete(T entity);


}
