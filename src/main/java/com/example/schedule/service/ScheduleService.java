package com.example.schedule.service;

import com.example.schedule.dto.CreateRequestDto;
import com.example.schedule.dto.ScheduleResponseDto;

import java.time.LocalDateTime;
import java.util.List;

public interface ScheduleService {

    ScheduleResponseDto saveSchedule(CreateRequestDto createRequestDto);

    List<ScheduleResponseDto> findAllSchedules(String name, LocalDateTime updatedDate);

    ScheduleResponseDto findScheduleById(Long id);

    ScheduleResponseDto updateSchedule(Long id, String name, String password, String todo);

    void deleteSchedule(Long id, String password);
}
