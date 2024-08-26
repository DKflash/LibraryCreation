package dev.adamag.librarycreation;


import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Query;

import dev.adamag.roomlibrary.DaoInterface;

import java.util.List;

@Dao
public interface DemoDao extends DaoInterface<DemoEntity> {

    @Query("SELECT * FROM demo_table")
    LiveData<List<DemoEntity>> getAll();
}
