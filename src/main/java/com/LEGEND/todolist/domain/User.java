package com.LEGEND.todolist.domain;

import java.io.Serializable;

public class User implements Serializable {
    private String id;
    private String password;

    public User(String id, String password) {
        this.id = id;
        this.password = password;
    }

    public String getId() { return id; }
    public String getPassword() { return password; }
}