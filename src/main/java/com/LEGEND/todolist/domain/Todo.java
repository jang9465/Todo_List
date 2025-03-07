package com.LEGEND.todolist.domain;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Todo implements Serializable {
    private static final long serialVersionUID = 1L; // 기존 버전과의 충돌 방지

    private String userId;  // 사용자 ID 추가
    private String title;
    private String description;
    private boolean isCompleted;
    private String createdDate; // 생성 날짜 (오늘 날짜)



    // 기본 생성자 추가 (Gson이 역직렬화할 때 필요)
    public Todo() {}


    public Todo(String userId, String title, String description) {
        this.userId = userId;
        this.title = title;
        this.description = description;
        this.isCompleted = false;
        this.createdDate = new SimpleDateFormat("yyyy-MM-dd").format(new Date()); // 현재 날짜 저장
    }



    public void markAsCompleted() {
        this.isCompleted = true;
    }

    public String getUserId() { return userId; }
    public String getTitle() { return title; }
    public String getDescription() { return description; }
    public boolean isCompleted() { return isCompleted; }
    public String getCreatedDate() { return createdDate; } // 생성일 반환
    public void setTitle(String title){this.title = title;}
    public void setDescription(String description) { this.description = description; }


    @Override
    public String toString() {
        return (isCompleted ? "[완료] " : "[미완료] ") + title + " - " + description;
    }



}
