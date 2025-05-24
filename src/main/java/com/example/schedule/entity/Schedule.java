package com.example.schedule.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class Schedule {
    private Long id;
    private String name;
    private String password;
    private String todo;
    private String createdDate;
    private String updatedDate;
}
