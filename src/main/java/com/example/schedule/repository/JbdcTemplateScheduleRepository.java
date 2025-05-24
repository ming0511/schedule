package com.example.schedule.repository;

import com.example.schedule.dto.ScheduleResponseDto;
import com.example.schedule.entity.Schedule;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
public class JbdcTemplateScheduleRepository implements ScheduleRepository{

    private final JdbcTemplate jdbcTemplate;

    public JbdcTemplateScheduleRepository(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public ScheduleResponseDto saveSchedule(Schedule schedule) {
        SimpleJdbcInsert jdbcInsert = new SimpleJdbcInsert(jdbcTemplate);
        jdbcInsert.withTableName("schedules")
                .usingGeneratedKeyColumns("id")
                .usingColumns("name", "password", "todo");

        Map<String, Object> parameters = new HashMap<>();
        parameters.put("name", schedule.getName());
        parameters.put("password", schedule.getPassword());
        parameters.put("todo", schedule.getTodo());

        Number key = jdbcInsert.executeAndReturnKey(new MapSqlParameterSource(parameters));

        Long id = key.longValue();

        // id로 생성된 일정의 created_date와 updated_date를 조회
        Map<String, Object> result = jdbcTemplate.queryForMap("SELECT id, name, todo, createdDate, updatedDate FROM schedules WHERE id = ?", id);

        // 반환하는 DTO에 날짜 정보 포함
        LocalDateTime createdDate = result.get("createdDate") != null ?
                ((java.sql.Timestamp) result.get("createdDate")).toLocalDateTime() : null;
        LocalDateTime updatedDate = result.get("updatedDate") != null ?
                ((java.sql.Timestamp) result.get("updatedDate")).toLocalDateTime() : null;

        // 디버깅
        System.out.println("updatedDate = " + updatedDate);

        return new ScheduleResponseDto(id, schedule.getName(), schedule.getTodo(), createdDate, updatedDate);
    }

    @Override
    public List<ScheduleResponseDto> findAllSchedules(String name, LocalDateTime updatedDate) {
        String sql = "SELECT id, name, todo, createdDate, updatedDate " +
                "FROM schedules " +
                "WHERE (updatedDate >= ? OR ? IS NULL) " +
                "AND (name = ? OR ? IS NULL) " +
                "ORDER BY updatedDate DESC";

        // 파라미터 설정
        Object[] params = new Object[] {
                updatedDate,
                updatedDate == null ? null : updatedDate,
                name,
                name == null ? null : name
        };

        return jdbcTemplate.query(sql, scheduleRowMapper(), params);
    }

    @Override
    public Optional<Schedule> findScheduleById(Long id){
        List<Schedule> result = jdbcTemplate.query("select * from schedules where id = ?", scheduleRowMapperV2(), id);

        return result.stream().findAny();
    }

    @Override
    public Optional<Schedule> findScheduleWithPasswordById(Long id) {
        List<Schedule> result = jdbcTemplate.query("select * from schedules where id = ?", scheduleRowMapperV3(), id);

        return result.stream().findAny();
    }

    @Override
    public int updateSchedule(Long id, String name, String todo) {
        return jdbcTemplate.update("update schedules set name = ?, todo = ? where id = ?", name, todo, id);
    }

    @Override
    public int deleteSchedule(Long id) {
        return jdbcTemplate.update("delete from schedules where id = ?", id);
    }

    private RowMapper<ScheduleResponseDto> scheduleRowMapper() {
        return new RowMapper<ScheduleResponseDto>() {
            @Override
            public ScheduleResponseDto mapRow(ResultSet rs, int rowNum) throws SQLException{
                return new ScheduleResponseDto(
                        rs.getLong("id"),
                        rs.getString("name"),
                        rs.getString("todo"),
                        rs.getObject("createdDate", LocalDateTime.class),
                        rs.getObject("updatedDate", LocalDateTime.class)
                );
            }
        };
    }

    private RowMapper<Schedule> scheduleRowMapperV2() {
        return new RowMapper<Schedule>() {
            @Override
            public Schedule mapRow(ResultSet rs, int rowNum) throws SQLException {
                return new Schedule(
                        rs.getLong("id"),
                        rs.getString("name"),
                        rs.getString("todo"),
                        rs.getObject("createdDate", LocalDateTime.class),
                        rs.getObject("updatedDate", LocalDateTime.class)
                );
            }
        };
    }

    private RowMapper<Schedule> scheduleRowMapperV3() {
        return new RowMapper<Schedule>() {
            @Override
            public Schedule mapRow(ResultSet rs, int rowNum) throws SQLException {
                return new Schedule(
                        rs.getLong("id"),
                        rs.getString("name"),
                        rs.getString("password"),
                        rs.getString("todo"),
                        rs.getObject("createdDate", LocalDateTime.class),
                        rs.getObject("updatedDate", LocalDateTime.class)
                );
            }
        };
    }

}
