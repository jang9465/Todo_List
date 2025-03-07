package com.LEGEND.todolist.service;

import com.LEGEND.todolist.domain.User;
import com.LEGEND.todolist.persistence.UserStorage;
import com.LEGEND.todolist.persistence.FileUserStorage;
import java.util.Map;

public class UserService {
    private UserStorage userStorage;
    private Map<String, User> users;

    public UserService() {
        this.userStorage = new FileUserStorage(); // 파일 저장 방식 적용
        this.users = userStorage.loadUsers();
    }

    public boolean register(String id, String password) {
        if (users.containsKey(id)) return false;
        users.put(id, new User(id, password));
        userStorage.saveUsers(users);
        return true;
    }

    public boolean login(String id, String password) {
        User user = users.get(id);
        return user != null && user.getPassword().equals(password);
    }
}
