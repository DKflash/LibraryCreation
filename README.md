'''
# RoomSQL Query Simplification Library

## Overview
### Key Features
- **EntityUtils**: Simplifies asynchronous CRUD operations for any Room entity.
- **RoomHelper**: Manages Room database initialization and provides a singleton instance of the database.
- **QueryBuilder**: Allows developers to create dynamic SQL queries.
- **Generic Implementation**: Works with any Room entity and DAO.

[Video](#video)

## Installation
### 1. Add the Library to Your Project
#### Add JitPack Repository
allprojects {
    repositories {
        google()
        mavenCentral()
        maven { url 'https://jitpack.io' }
    }
}
#### Add the Library Dependency

dependencies {
    implementation ':RoomLibrary'
}

### 2. Add Room and Lifecycle Dependencies

dependencies {
    implementation 'androidx.room:room-runtime:2.5.2'
    annotationProcessor 'androidx.room:room-compiler:2.5.2'

    implementation 'androidx.lifecycle:lifecycle-livedata:2.5.1'
    implementation 'androidx.lifecycle:lifecycle-viewmodel:2.5.1'
}

## Usage

### Database Setup

#### 1. Create an Entity Class
```java
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "demo_table")
public class DemoEntity {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private String name;
    private int age;

    public DemoEntity(String name, int age) {
        this.name = name;
        this.age = age;
    }

    // Getters and Setters...
}
```

---

#### 2. Create a DAO Interface
```java
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
```

---

#### 3. Create the Room Database
```java
import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {DemoEntity.class}, version = 1, exportSchema = false)
public abstract class DemoDatabase extends RoomDatabase {
    public abstract DemoDao demoDao();
}
```

---

### CRUD Operations

#### Insert an Entity and Retrieve the Generated ID
```java
DemoEntity demoEntity = new DemoEntity("Adam Agbaria", 22);
long generatedId = EntityUtils.insertAndReturnId(demoDao, demoEntity);
Update an Entity
```java

DemoEntity updatedEntity = new DemoEntity("Adam Agbaria", 24);
updatedEntity.setId(generatedId);
EntityUtils.updateAsync(demoDao, updatedEntity);
```

---
### Delete an Entity
```java
DemoEntity deleteEntity = new DemoEntity("Adam Agbaria", 24);
deleteEntity.setId(generatedId);
EntityUtils.deleteAsync(demoDao, deleteEntity);
Retrieve All Entities
```java
EntityUtils.getAllEntities(demoDao).observe(this, demoEntities -> {
    for (DemoEntity entity : demoEntities) {
        System.out.println(entity.getName() + " - " + entity.getAge());
    }
});
```

---
### Dynamic Queries

#### Build and Execute a Query
```java
QueryBuilder queryBuilder = new QueryBuilder("demo_table");
queryBuilder.where("age > ?", 18).orderBy("age", true);

List<DemoEntity> results = demoDao.executeQuery(queryBuilder.build());
for (DemoEntity entity : results) {
    System.out.println(entity.getName() + " - " + entity.getAge());
}
```

---

## Demo Application
This repository includes a demo Android application that demonstrates how to use the library in a real-world scenario. The demo app allows you to perform CRUD operations on DemoEntity instances and shows the results in a TextView.

### Demo App Structure
# MainActivity: Demonstrates how to use the library for inserting, querying, updating, and deleting data.
# DemoEntity: A Room entity representing a table in the database.
# DemoDao: The DAO interface for interacting with DemoEntity.
# DemoDatabase: The Room database class that provides access to the DAO.

## Running the Demo App
Clone the repository.
Open the project in Android Studio.
Build and run the app on an emulator or physical device.
Use the provided buttons to perform operations and observe the results.

# video

https://github.com/user-attachments/assets/2ec3c726-deaf-4e80-9e5c-f79ccadded49


