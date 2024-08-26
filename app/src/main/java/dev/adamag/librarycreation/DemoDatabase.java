package dev.adamag.librarycreation;

import androidx.room.Database;
import androidx.room.RoomDatabase;


@Database(entities = {DemoEntity.class}, version = 1, exportSchema = false)
public abstract class DemoDatabase extends RoomDatabase {
    public abstract DemoDao demoDao();
}
