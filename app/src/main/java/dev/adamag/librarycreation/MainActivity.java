package dev.adamag.librarycreation;

import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import java.util.List;
import java.util.concurrent.Executors;
import dev.adamag.roomlibrary.GenericRepository;
//import dev.adamag.roomlibrary.GenericRepository.Callback;

public class MainActivity extends AppCompatActivity {

    private GenericRepository<User> userRepository;
    private UserDao userDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize the UserDao and GenericRepository
        userDao = new UserDaoImpl();
        userRepository = new GenericRepository<>(userDao);

        Button button = findViewById(R.id.button);
        button.setOnClickListener(v -> {
            User user = new User("Adam Agbaria", 22);
            userRepository.insert(user, result -> {
                runOnUiThread(() -> Toast.makeText(this, "User added", Toast.LENGTH_SHORT).show());
                Executors.newSingleThreadExecutor().execute(() -> {
                    List<User> users = userDao.getAll();
                    runOnUiThread(() -> {
                        for (User u : users) {
                            System.out.println("User: " + u.getName() + ", Age: " + u.getAge());
                        }
                    });
                });
            });
        });
    }
}
