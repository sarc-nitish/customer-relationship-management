package com.crm.project.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class LeadDto {

    private Long id;

    @NotNull(message = "Customer id is required")
    private Long customerId;

    private String source;
    private String stage;
    private Double value;
}
