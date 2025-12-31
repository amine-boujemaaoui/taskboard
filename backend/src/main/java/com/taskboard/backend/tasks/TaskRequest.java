package com.taskboard.backend.tasks;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record TaskRequest(
        @NotBlank @Size(max = 120) String title,
        String description,
        @NotBlank @Size(max = 20) String status,
        @NotBlank @Size(max = 20) String priority
) {}
