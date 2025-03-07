package com.LEGEND.todolist.persistence;

import com.LEGEND.todolist.domain.Todo;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import java.io.*;
import java.util.List;
import java.util.ArrayList;
import java.util.stream.Collectors;

public class FileTodoStorage implements TodoStorage {
    private static final String FILE_NAME = "src/main/java/com/LEGEND/todolist/db/todos.json";
    private Gson gson = new GsonBuilder().setPrettyPrinting().create();

    @Override
    public void saveTodos(String userId, List<Todo> todos) {
        List<Todo> allTodos = loadAllTodos();
        allTodos.removeIf(todo -> todo.getUserId().equals(userId));
        allTodos.addAll(todos);

        try (Writer writer = new FileWriter(FILE_NAME)) {
            gson.toJson(allTodos, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Todo> loadTodos(String userId) {
        List<Todo> allTodos = loadAllTodos();
        return allTodos.stream()
                .filter(todo -> todo.getUserId() != null && todo.getUserId().equals(userId))
                .collect(Collectors.toList());
    }



    private List<Todo> loadAllTodos() {
        try (Reader reader = new FileReader(FILE_NAME)) {
            return gson.fromJson(reader, new TypeToken<List<Todo>>() {}.getType());
        } catch (IOException e) {
            return new ArrayList<>();
        }
    }
}


