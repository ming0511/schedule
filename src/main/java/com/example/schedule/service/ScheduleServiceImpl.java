package com.example.schedule.service;

import com.example.schedule.dto.ScheduleRequestDto;
import com.example.schedule.dto.ScheduleResponseDto;
import com.example.schedule.entity.Schedule;
import com.example.schedule.repository.ScheduleRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class ScheduleServiceImpl implements ScheduleService{

    private final ScheduleRepository scheduleRepository;

    public ScheduleServiceImpl(ScheduleRepository scheduleRepository) {
        this.scheduleRepository = scheduleRepository;
    }

    @Override
    public ScheduleResponseDto saveSchedule(ScheduleRequestDto scheduleRequestDto) {
        Schedule schedule = new Schedule(scheduleRequestDto.getName(), scheduleRequestDto.getPassword(), scheduleRequestDto.getTodo());

        return scheduleRepository.saveSchedule(schedule);
    }

    @Override
    public List<ScheduleResponseDto> findAllSchedules() {

        return scheduleRepository.findAllSchedules();
    }

    @Override
    public ScheduleResponseDto findScheduleById(Long id) {

        Optional<Schedule> optionalSchedule = scheduleRepository.findScheduleById(id);

        if (optionalSchedule == null){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Does not exist id = " + id);
        }

        return new ScheduleResponseDto(optionalSchedule.get());
    }

    @Transactional
    @Override
    public ScheduleResponseDto updateSchedule(Long id, String name, String password, String todo, LocalDateTime updatedDate) {

        if (name == null || todo == null){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "The name and todo are required values.");
        }

        int updatedRow = scheduleRepository.updateSchedule(id, name, password, todo, updatedDate);

        if (updatedRow == 0){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Does not exist id = " + id);
        }

        return new ScheduleResponseDto(scheduleRepository.findScheduleById(id).get());
    }

    @Override
    public void deleteSchedule(Long id) {
        int deletedRow = scheduleRepository.deleteSchedule(id);
        if (deletedRow == 0){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Does not exist id = " + id);
        }
    }
}
