package dev.adamag.librarycreation;

import java.util.List;
import dev.adamag.roomlibrary.BaseDao;

public interface UserDao extends BaseDao<User> {
    List<User> getAll();
}
