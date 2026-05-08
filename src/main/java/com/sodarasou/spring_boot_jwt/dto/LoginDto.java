package com.sodarasou.spring_boot_jwt.dto;

import jakarta.validation.constraints.NotBlank;

public record LoginDto(
        @NotBlank
        String username,
        @NotBlank
        String password
) {
}
