package dev.adamag.librarycreation;

import java.util.ArrayList;
import java.util.List;

public class UserDaoImpl implements UserDao {
    private final List<User> userList = new ArrayList<>();

    @Override
    public void insert(User entity) {
        userList.add(entity);
    }

    @Override
    public void update(User entity) {
        for (int i = 0; i < userList.size(); i++) {
            if (userList.get(i).getId() == entity.getId()) {
                userList.set(i, entity);
                return;
            }
        }
    }

    @Override
    public void delete(User entity) {
        userList.removeIf(user -> user.getId() == entity.getId());
    }

    @Override
    public List<User> getAll() {
        return new ArrayList<>(userList);
    }
}
