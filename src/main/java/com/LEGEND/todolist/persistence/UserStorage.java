package com.LEGEND.todolist.persistence;

import com.LEGEND.todolist.domain.User;
import java.util.Map;

public interface UserStorage {
    void saveUsers(Map<String, User> users);
    Map<String, User> loadUsers();
}
