package com.crm.project.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class TaskDto {

    private Long id;

    @NotBlank(message = "Title is required")
    private String title;

    private String description;
    private LocalDateTime dueDate;
    private String status;
    private Long assignedToId;
    private Long customerId;
}
