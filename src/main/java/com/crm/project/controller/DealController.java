package com.crm.project.controller;

import com.crm.project.dto.DealDto;
import com.crm.project.service.DealService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Deal APIs")
@RestController
@RequestMapping("/api/deals")
@RequiredArgsConstructor
public class DealController {

    private final DealService dealService;

    @Operation(summary = "Get all deals")
    @GetMapping
    public ResponseEntity<List<DealDto>> getAll() {
        return ResponseEntity.ok(dealService.getAll());
    }

    @Operation(summary = "Get deal by id")
    @GetMapping("/{id}")
    public ResponseEntity<DealDto> getById(@PathVariable Long id) {
        return ResponseEntity.ok(dealService.getById(id));
    }

    @Operation(summary = "Create a new deal")
    @PostMapping
    public ResponseEntity<DealDto> create(@Valid @RequestBody DealDto dto) {
        return ResponseEntity.ok(dealService.create(dto));
    }

    @Operation(summary = "Update an existing deal")
    @PutMapping("/{id}")
    public ResponseEntity<DealDto> update(@PathVariable Long id, @Valid @RequestBody DealDto dto) {
        return ResponseEntity.ok(dealService.update(id, dto));
    }

    @Operation(summary = "Delete a deal")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        dealService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
