package com.example.schedule.dto;

import lombok.Getter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

@Getter
public class ScheduleRequestDto {
    private String name;
    private String password;
    private String todo;
    private LocalDateTime updatedDate;
}
