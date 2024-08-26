package dev.adamag.librarycreation;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;

import dev.adamag.roomlibrary.EntityUtils;
import dev.adamag.roomlibrary.QueryBuilder;
import dev.adamag.roomlibrary.RoomHelper;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private DemoDao demoDao;
    private TextView resultTextView;
    private long lastInsertedId = -1; // Store the generated ID


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize the UI components
        resultTextView = findViewById(R.id.resultTextView);
        Button insertButton = findViewById(R.id.insertButton);
        Button queryButton = findViewById(R.id.queryButton);
        Button updateButton = findViewById(R.id.updateButton);
        Button deleteButton = findViewById(R.id.deleteButton);

        // Initialize the Room database using the library's RoomHelper
        RoomHelper roomHelper = RoomHelper.getInstance();
        roomHelper.initDatabase(this, DemoDatabase.class, "demo_db");
        DemoDatabase demoDatabase = (DemoDatabase) roomHelper.getDatabase(); // Cast to DemoDatabase
        demoDao = demoDatabase.demoDao();

        // Insert sample data and get the generated ID
        insertButton.setOnClickListener(v -> {
            DemoEntity demoEntity = new DemoEntity("Adam Agbaria", 22);
            lastInsertedId = EntityUtils.insertAndReturnId(demoDao, demoEntity);
            showResult("Inserted: Adam Agbaria, Age: 22, ID: " + lastInsertedId);
        });

        // Query data using QueryBuilder from the library
        queryButton.setOnClickListener(v -> {
            QueryBuilder queryBuilder = new QueryBuilder("demo_table");
            queryBuilder.where("age > ?", 18).orderBy("age", true);

            // Execute the query using the DAO method
            demoDao.getAll().observe(this, demoEntities -> {
                StringBuilder result = new StringBuilder("Query Result:\n");
                for (DemoEntity entity : demoEntities) {
                    result.append(entity.getName()).append(", Age: ").append(entity.getAge()).append(", ID: ").append(entity.getId()).append("\n");
                }
                showResult(result.toString());
            });
        });

        // Update data using EntityUtils from the library with the last inserted ID
        updateButton.setOnClickListener(v -> {
            if (lastInsertedId != -1) {
                DemoEntity updatedEntity = new DemoEntity("Adam Agbaria", 24);
                updatedEntity.setId((int) lastInsertedId); // Use the stored ID
                EntityUtils.updateAsync(demoDao, updatedEntity);
                showResult("Updated: Adam Agbaria, Age: 24, ID: " + lastInsertedId);
            } else {
                showResult("No entity to update. Please insert an entity first.");
            }
        });

        // Delete data using EntityUtils from the library with the last inserted ID
        deleteButton.setOnClickListener(v -> {
            if (lastInsertedId != -1) {
                DemoEntity deleteEntity = new DemoEntity("Adam Agbaria", 24);
                deleteEntity.setId((int) lastInsertedId); // Use the stored ID
                EntityUtils.deleteAsync(demoDao, deleteEntity);
                showResult("Deleted: Adam Agbaria, Age: 24, ID: " + lastInsertedId);
            } else {
                showResult("No entity to delete. Please insert an entity first.");
            }
        });
    }

    private void showResult(String result) {
        resultTextView.setText(result);
    }
}
