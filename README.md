# RoomSQL Query Simplification Library

<p align="center">
  <img src="https://img.shields.io/badge/License-MIT-blue.svg" alt="License: MIT"/>
</p>

## <strong>Overview</strong>

<p>
The <strong>RoomSQL Query Simplification Library</strong> is designed to simplify working with Room in Android applications. It provides utility classes for performing common database operations such as CRUD (Create, Read, Update, Delete), dynamic query construction, and database initialization. This library reduces boilerplate code and streamlines the integration of Room in your Android projects.
</p>

<p>
<strong>Key Features:</strong>
<ul>
  <li><strong>EntityUtils</strong>: Simplifies asynchronous CRUD operations for any Room entity.</li>
  <li><strong>RoomHelper</strong>: Manages Room database initialization and provides a singleton instance of the database.</li>
  <li><strong>QueryBuilder</strong>: Allows developers to create dynamic SQL queries for more advanced use cases.</li>
  <li><strong>Generic Implementation</strong>: The library works generically with any Room entity and DAO.</li>
</ul>
</p>

## <strong>Table of Contents</strong>

<ul>
  <li><a href="#installation">Installation</a></li>
  <li><a href="#usage">Usage</a></li>
  <ul>
    <li><a href="#database-setup">Database Setup</a></li>
    <li><a href="#crud-operations">CRUD Operations</a></li>
    <li><a href="#dynamic-queries">Dynamic Queries</a></li>
  </ul>
  <li><a href="#demo-application">Demo Application</a></li>
  <li><a href="#contributing">Contributing</a></li>
  <li><a href="#license">License</a></li>
</ul>

## <a name="installation"></a><strong>Installation</strong>

### 1. Add Room and Lifecycle Dependencies

Ensure that the required Room and Lifecycle dependencies are added to your `app/build.gradle` file:

```gradle
dependencies {
    // Room components
    implementation 'androidx.room:room-runtime:2.5.2'
    annotationProcessor 'androidx.room:room-compiler:2.5.2' // For Java projects

    // Lifecycle components for LiveData and ViewModel
    implementation 'androidx.lifecycle:lifecycle-livedata:2.5.1'
    implementation 'androidx.lifecycle:lifecycle-viewmodel:2.5.1'
}

2. Include the Library
To use the library in your project, you can either include it as a local module or publish it to a repository (e.g., JitPack or Maven) and add it as a dependency.

Usage
Database Setup
First, create your Room entity, DAO, and database classes.

1. Create an Entity Class
java
Copy code
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
2. Create a DAO Interface
java
Copy code
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
3. Create the Room Database
java
Copy code
import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {DemoEntity.class}, version = 1, exportSchema = false)
public abstract class DemoDatabase extends RoomDatabase {
    public abstract DemoDao demoDao();
}
CRUD Operations
Use EntityUtils to perform asynchronous CRUD operations with any DAO that implements DaoInterface<T>.

Insert an Entity and Retrieve the Generated ID
java
Copy code
// Insert data and retrieve the generated ID
DemoEntity demoEntity = new DemoEntity("John Doe", 25);
long generatedId = EntityUtils.insertAndReturnId(demoDao, demoEntity);
Update an Entity
java
Copy code
// Update the entity using the previously generated ID
DemoEntity updatedEntity = new DemoEntity("John Doe", 30);
updatedEntity.setId(generatedId); // Use the generated ID
EntityUtils.updateAsync(demoDao, updatedEntity);
Delete an Entity
java
Copy code
// Delete the entity using the generated ID
DemoEntity deleteEntity = new DemoEntity("John Doe", 30);
deleteEntity.setId(generatedId); // Use the generated ID
EntityUtils.deleteAsync(demoDao, deleteEntity);
Retrieve All Entities
java
Copy code
// Retrieve all entities using LiveData
EntityUtils.getAllEntities(demoDao).observe(this, demoEntities -> {
    for (DemoEntity entity : demoEntities) {
        System.out.println(entity.getName() + " - " + entity.getAge());
    }
});
Dynamic Queries
Use QueryBuilder to build custom SQL queries dynamically.

Example: Build and Execute a Query
java
Copy code
QueryBuilder queryBuilder = new QueryBuilder("demo_table");
queryBuilder.where("age > ?", 18).orderBy("age", true);

List<DemoEntity> results = demoDao.executeQuery(queryBuilder.build());
for (DemoEntity entity : results) {
    System.out.println(entity.getName() + " - " + entity.getAge());
}
Demo Application
This repository includes a demo Android application that demonstrates how to use the library in a real-world scenario. The demo app allows you to perform CRUD operations on DemoEntity instances and shows the results in a TextView.

Demo App Structure
MainActivity: Demonstrates how to use the library for inserting, querying, updating, and deleting data.
DemoEntity: A Room entity representing a table in the database.
DemoDao: The DAO interface for interacting with DemoEntity.
DemoDatabase: The Room database class that provides access to the DAO.
Demo App UI
The app provides buttons to perform the following actions:

Insert a record
Query records
Update a record
Delete a record
Results are displayed in a TextView.

Running the Demo App
Clone the repository.
Open the project in Android Studio.
Build and run the app on an emulator or physical device.
Use the provided buttons to perform operations and observe the results.
Contributing
Contributions are welcome! To contribute:

Fork the repository.
Create a new branch for your feature (git checkout -b feature/your-feature).
Commit your changes (git commit -m 'Add your feature').
Push to the branch (git push origin feature/your-feature).
Open a pull request.
Contribution Guidelines
Ensure your code is well-documented.
Write tests where applicable.
Follow the existing coding style and conventions.
