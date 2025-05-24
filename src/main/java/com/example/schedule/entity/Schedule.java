package com.example.schedule.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class Schedule {
    private Long id;
    private String name;
    private String password;
    private String todo;
    private LocalDateTime createdDate;
    private LocalDateTime updatedDate;

    public Schedule(String name, String password, String todo) {
        this.name = name;
        this.password = name;
        this.todo = todo;
    }

    public Schedule(Long id, String name, String todo, LocalDateTime createdDate, LocalDateTime updatedDate) {
        this.id = id;
        this.name = name;
        this.todo = todo;
        this.createdDate = createdDate;
        this.updatedDate = updatedDate;
    }

    public Schedule(String name, String password, String todo, LocalDateTime createdDate, LocalDateTime updatedDate) {
        this.name = name;
        this.password = password;
        this.todo = todo;
        this.createdDate = createdDate;
        this.updatedDate = updatedDate;
    }

    public void update(String name, String todo, LocalDateTime updatedDate){
        this.name = name;
        this.todo = todo;
        this.updatedDate = updatedDate;
    }
}
