package com.example.schedule.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class DeleteRequestDto {
    @NotBlank(message = "비밀번호는 필수입니다.")
    private String password;
}
