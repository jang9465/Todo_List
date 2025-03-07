package com.LEGEND.todolist.persistence;

import com.LEGEND.todolist.domain.User;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class FileUserStorage implements UserStorage {
    private static final String FILE_NAME = "src/main/java/com/LEGEND/todolist/db/users.json";
    private Gson gson = new Gson();

    @Override
    public void saveUsers(Map<String, User> users) {
        try (Writer writer = new FileWriter(FILE_NAME)) {
            gson.toJson(users, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Map<String, User> loadUsers() {
        try (Reader reader = new FileReader(FILE_NAME)) {
            return gson.fromJson(reader, new TypeToken<Map<String, User>>() {}.getType());
        } catch (IOException e) {
            return new HashMap<>(); // 파일이 없으면 빈 리스트 반환
        }
    }
}

