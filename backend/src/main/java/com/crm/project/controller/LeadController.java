package com.crm.project.controller;

import com.crm.project.dto.LeadDto;
import com.crm.project.service.LeadService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Lead APIs")
@RestController
@RequestMapping("/api/leads")
@RequiredArgsConstructor
public class LeadController {

    private final LeadService leadService;

    @Operation(summary = "Get all leads")
    @GetMapping
    public ResponseEntity<List<LeadDto>> getAll() {
        return ResponseEntity.ok(leadService.getAll());
    }

    @Operation(summary = "Get lead by id")
    @GetMapping("/{id}")
    public ResponseEntity<LeadDto> getById(@PathVariable Long id) {
        return ResponseEntity.ok(leadService.getById(id));
    }

    @Operation(summary = "Create a new lead")
    @PostMapping
    public ResponseEntity<LeadDto> create(@Valid @RequestBody LeadDto dto) {
        return ResponseEntity.ok(leadService.create(dto));
    }

    @Operation(summary = "Update an existing lead")
    @PutMapping("/{id}")
    public ResponseEntity<LeadDto> update(@PathVariable Long id, @Valid @RequestBody LeadDto dto) {
        return ResponseEntity.ok(leadService.update(id, dto));
    }

    @Operation(summary = "Delete a lead")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        leadService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
