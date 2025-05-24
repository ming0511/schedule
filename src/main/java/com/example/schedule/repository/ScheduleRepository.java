package com.example.schedule.repository;

import com.example.schedule.dto.ScheduleResponseDto;
import com.example.schedule.entity.Schedule;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface ScheduleRepository {

    ScheduleResponseDto saveSchedule(Schedule schedule);

    List<ScheduleResponseDto> findAllSchedules(String name, LocalDateTime updatedDate);

    Optional<Schedule> findScheduleById(Long id);

    Optional<Schedule> findScheduleWithPasswordById(Long id);

//    int updateSchedule(Long id, String name, String password, String todo);

    int deleteSchedule(Long id);
}
