package com.example.schedule.dto;

import com.example.schedule.entity.Schedule;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class ScheduleResponseDto {
    private Long id;
    private String name;
    private String todo;
    private LocalDateTime createdDate;
    private LocalDateTime updatedDate;

    public ScheduleResponseDto(Schedule schedule) {
        this.id = schedule.getId();
        this.name = schedule.getName();
        this.todo = schedule.getTodo();
        this.createdDate = schedule.getCreatedDate();
        this.updatedDate = schedule.getUpdatedDate();
    }
}
