package com.sodarasou.spring_boot_jwt.dto;

import jakarta.validation.constraints.NotBlank;

public record RegisterDto (
        @NotBlank
        String username,
        @NotBlank
        String password
) {
}
