package dev.adamag.librarycreation;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "demo_table")
public class DemoEntity {

    @PrimaryKey(autoGenerate = true)
    private int id;
    private String name;
    private int age;

    // Constructor
    public DemoEntity(String name, int age) {
        this.name = name;
        this.age = age;
    }

    // Getters and setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }
}
