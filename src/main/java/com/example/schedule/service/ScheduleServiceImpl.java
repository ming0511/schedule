package com.example.schedule.service;

import com.example.schedule.dto.ScheduleRequestDto;
import com.example.schedule.dto.ScheduleResponseDto;
import com.example.schedule.entity.Schedule;
import com.example.schedule.repository.ScheduleRepository;
import org.springframework.stereotype.Service;

@Service
public class ScheduleServiceImpl implements ScheduleService{

    private final ScheduleRepository scheduleRepository;

    public ScheduleServiceImpl(ScheduleRepository scheduleRepository) {
        this.scheduleRepository = scheduleRepository;
    }

    @Override
    public ScheduleResponseDto saveSchedule(ScheduleRequestDto scheduleRequestDto) {
        Schedule schedule = new Schedule(scheduleRequestDto.getName(), scheduleRequestDto.getPassword(), scheduleRequestDto.getTodo(), scheduleRequestDto.getCreatedDate(), scheduleRequestDto.getUpdatedDate());

        Long scheduleId = scheduleRepository.saveSchedule(schedule);

        return new ScheduleResponseDto(scheduleId, schedule.getName(), schedule.getTodo(), schedule.getCreatedDate(), schedule.getUpdatedDate());
    }
}
