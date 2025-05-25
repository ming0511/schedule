package com.example.schedule.service;

import com.example.schedule.dto.CreateRequestDto;
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
    public ScheduleResponseDto saveSchedule(CreateRequestDto createRequestDto) {
        Schedule schedule = new Schedule(createRequestDto.getName(), createRequestDto.getPassword(), createRequestDto.getTodo());

        return scheduleRepository.saveSchedule(schedule);
    }

    @Override
    public List<ScheduleResponseDto> findAllSchedules(String name, LocalDateTime updatedDate) {

        return scheduleRepository.findAllSchedules(name, updatedDate);
    }

    @Override
    public ScheduleResponseDto findScheduleById(Long id) {

        // 기존 일정 조회
        Schedule schedule = scheduleRepository.findScheduleById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "일정이 존재하지 않습니다."));


        return new ScheduleResponseDto(schedule);
    }

    @Transactional
    @Override
    public ScheduleResponseDto updateSchedule(Long id, String name, String password, String todo) {
        // 기존 일정 조회
        Schedule schedule = scheduleRepository.findScheduleWithPasswordById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "일정이 존재하지 않습니다."));

        // 비밀번호 확인
        if (schedule.getPassword() == null || password == null || !schedule.getPassword().equals(password)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "비밀번호가 일치하지 않습니다.");
        }

        // Validate that at least one field is being updated
        if (name == null && todo == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "최소한 하나의 필드는 업데이트되어야 합니다.");
        }

        // 현재 값을 유지할 경우 기존 값을 사용
        String updateName = (name != null) ? name : schedule.getName();
        String updateTodo = (todo != null) ? todo : schedule.getTodo();

        int updatedRows = scheduleRepository.updateSchedule(id, updateName, updateTodo);
        if (updatedRows == 0) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "업데이트에 실패했습니다.");
        }

        // 엔티티 업데이트
        schedule.setName(updateName);
        schedule.setTodo(updateTodo);

        return new ScheduleResponseDto(schedule);
    }

    @Override
    public void deleteSchedule(Long id, String password) {
        // 기존 일정 조회
        Schedule schedule = scheduleRepository.findScheduleWithPasswordById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "일정이 존재하지 않습니다."));

        // 비밀번호 확인
        if (schedule.getPassword() == null || password == null || !schedule.getPassword().equals(password)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "비밀번호가 일치하지 않습니다.");
        }

        scheduleRepository.deleteSchedule(id);
    }
}
