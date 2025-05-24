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
    private String createdDate;
    private String updatedDate;

    public Schedule(Long id, String name, String todo, String createdDate, String updatedDate) {
        this.id = id;
        this.name = name;
        this.todo = todo;
        this.createdDate = createdDate;
        this.updatedDate = updatedDate;
    }

    public Schedule(String name, String password, String todo, String createdDate, String updatedDate) {
        this.name = name;
        this.password = password;
        this.todo = todo;
        this.createdDate = createdDate;
        this.updatedDate = updatedDate;
    }
}
