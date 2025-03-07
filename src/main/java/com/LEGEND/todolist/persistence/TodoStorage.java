package com.LEGEND.todolist.persistence;

import com.LEGEND.todolist.domain.Todo;
import java.util.List;

public interface TodoStorage {
    void saveTodos(String userId, List<Todo> todos);
    List<Todo> loadTodos(String userId);
}
