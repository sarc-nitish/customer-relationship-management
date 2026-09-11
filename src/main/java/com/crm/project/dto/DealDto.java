package com.crm.project.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDate;

@Data
public class DealDto {

    private Long id;

    @NotNull(message = "Customer id is required")
    private Long customerId;

    @NotNull(message = "Amount is required")
    private Double amount;

    private String stage;
    private LocalDate closeDate;
}
