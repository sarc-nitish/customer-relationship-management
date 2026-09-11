package com.crm.project.service;

import com.crm.project.dto.LeadDto;
import com.crm.project.entity.Customer;
import com.crm.project.entity.Lead;
import com.crm.project.exception.ResourceNotFoundException;
import com.crm.project.repository.CustomerRepository;
import com.crm.project.repository.LeadRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class LeadService {

    private final LeadRepository leadRepository;
    private final CustomerRepository customerRepository;

    public List<LeadDto> getAll() {
        return leadRepository.findAll().stream().map(this::toDto).collect(Collectors.toList());
    }

    public LeadDto getById(Long id) {
        Lead lead = leadRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Lead not found with id: " + id));
        return toDto(lead);
    }

    public LeadDto create(LeadDto dto) {
        Customer customer = customerRepository.findById(dto.getCustomerId())
                .orElseThrow(() -> new ResourceNotFoundException("Customer not found with id: " + dto.getCustomerId()));

        Lead lead = Lead.builder()
                .customer(customer)
                .source(dto.getSource())
                .stage(dto.getStage() != null ? dto.getStage() : "NEW")
                .value(dto.getValue())
                .build();

        return toDto(leadRepository.save(lead));
    }

    public LeadDto update(Long id, LeadDto dto) {
        Lead lead = leadRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Lead not found with id: " + id));

        lead.setSource(dto.getSource());
        lead.setStage(dto.getStage());
        lead.setValue(dto.getValue());

        return toDto(leadRepository.save(lead));
    }

    public void delete(Long id) {
        if (!leadRepository.existsById(id)) {
            throw new ResourceNotFoundException("Lead not found with id: " + id);
        }
        leadRepository.deleteById(id);
    }

    private LeadDto toDto(Lead lead) {
        LeadDto dto = new LeadDto();
        dto.setId(lead.getId());
        dto.setCustomerId(lead.getCustomer().getId());
        dto.setSource(lead.getSource());
        dto.setStage(lead.getStage());
        dto.setValue(lead.getValue());
        return dto;
    }
}
