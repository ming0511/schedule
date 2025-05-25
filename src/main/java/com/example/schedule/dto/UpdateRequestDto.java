package com.example.schedule.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class UpdateRequestDto {
    private String name;

    @NotBlank(message = "비밀번호는 필수입니다.")
    private String password;
    private String todo;
}
