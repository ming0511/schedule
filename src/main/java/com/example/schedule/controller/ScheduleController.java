package com.example.schedule.controller;

import com.example.schedule.dto.CreateRequestDto;
import com.example.schedule.dto.DeleteRequestDto;
import com.example.schedule.dto.ScheduleResponseDto;
import com.example.schedule.dto.UpdateRequestDto;
import com.example.schedule.service.ScheduleService;
import jakarta.validation.Valid;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;


@RestController
@RequestMapping("/api/schedules")
public class ScheduleController {

    private final ScheduleService scheduleService;

    private ScheduleController(ScheduleService scheduleService) {
        this.scheduleService = scheduleService;
    }

    @PostMapping
    public ResponseEntity<ScheduleResponseDto> createSchedule(@Valid @RequestBody CreateRequestDto createRequestDto) {

        return new ResponseEntity<>(scheduleService.saveSchedule(createRequestDto), HttpStatus.CREATED);
    }

    @GetMapping
    public List<ScheduleResponseDto> findAllSchedules(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate date
    ){
        LocalDateTime dateTime = date != null ? date.atStartOfDay() : null;

        return scheduleService.findAllSchedules(name, dateTime);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ScheduleResponseDto> findScheduleById(@PathVariable Long id){

        return new ResponseEntity<>(scheduleService.findScheduleById(id), HttpStatus.OK);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<ScheduleResponseDto> updateSchedule(
            @PathVariable Long id,
            @Valid @RequestBody UpdateRequestDto updateRequestDto
    ){
        return new ResponseEntity<>(scheduleService.updateSchedule(id, updateRequestDto.getName(), updateRequestDto.getPassword(), updateRequestDto.getTodo()), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSchedule(
            @PathVariable Long id,
            @Valid @RequestBody DeleteRequestDto deleteRequestDto
    ){

        scheduleService.deleteSchedule(id, deleteRequestDto.getPassword());
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
