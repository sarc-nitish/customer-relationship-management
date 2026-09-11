package com.crm.project.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class CustomerDto {

    private Long id;

    @NotBlank(message = "Name is required")
    private String name;

    private String company;
    private String phone;
    private String email;
    private String status;
    private Long assignedToId;
}
