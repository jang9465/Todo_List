package com.LEGEND.todolist.service;

import com.LEGEND.todolist.domain.Todo;
import com.LEGEND.todolist.persistence.TodoStorage;
import com.LEGEND.todolist.persistence.FileTodoStorage;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

public class TodoService {
    private List<Todo> todos;
    private TodoStorage todoStorage;
    private String userId;

    public TodoService(String userId) {
        this.userId = userId;
        this.todoStorage = new FileTodoStorage(); // 파일 저장 방식 적용
        this.todos = todoStorage.loadTodos(userId);
    }

    public void addTodo(String title, String description) {
        Todo newTodo = new Todo(userId, title, description);
        todos.add(newTodo);
        todoStorage.saveTodos(userId, todos);
    }

    public void removeTodo(int index) {
        if (index >= 0 && index < todos.size()) {
            todos.remove(index);
            todoStorage.saveTodos(userId, todos);
        }
    }

    public void markTodoAsCompleted(int index) {
        if (index >= 0 && index < todos.size()) {
            todos.get(index).markAsCompleted();
            todoStorage.saveTodos(userId, todos);
        }
    }

    public void editTodo(int index, String newTitle, String newDescription) {
        if (index >= 0 && index < todos.size()) {
            Todo todo = todos.get(index);
            if (!newTitle.isEmpty()) {
                todo.setTitle(newTitle);
            }
            if (!newDescription.isEmpty()) {
                todo.setDescription(newDescription);
            }
            todoStorage.saveTodos(userId, todos);
            System.out.println("할 일이 수정되었습니다.");
        } else {
            System.out.println("잘못된 번호입니다.");
        }
    }

    public void displayTodos() {
        List<Todo> allTodos = todoStorage.loadTodos(userId);

        if (allTodos.isEmpty()) {
            System.out.println("모든 할 일이 없습니다.");
            return;
        }



        // 날짜별 그룹화
        Map<String, List<Todo>> groupedTodos = allTodos.stream()
                .collect(Collectors.groupingBy(Todo::getCreatedDate, TreeMap::new, Collectors.toList()));

        for (String date : groupedTodos.keySet()) {
            System.out.println("\n==== " + date + " ====\n");
            for (int i = 0; i < groupedTodos.get(date).size(); i++) {
                System.out.printf("%d. %s\n", (i + 1), groupedTodos.get(date).get(i));
            }
        }

        // 전체 할 일 수와 완료된 할 일 수 계산
        int total = allTodos.size();
        int completedCount = (int) allTodos.stream().filter(Todo::isCompleted).count();
        double percentage = (total > 0) ? (completedCount * 100.0 / total) : 0;

        // 목표 달성률 출력
        System.out.printf("\n[   목표 달성률 : %d/%d  ]  약 %.0f%% 달성했네! 더욱 화이팅하자\n",
                completedCount, total, percentage);
    }

    public List<Todo> getTodos() {
        return todoStorage.loadTodos(userId);
    }

    public List<Todo> getTodayTodos() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String today = sdf.format(new Date());
        return todos.stream()
                .filter(todo -> todo.getCreatedDate().equals(today))
                .collect(Collectors.toList());
    }
}