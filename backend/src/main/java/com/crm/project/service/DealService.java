package com.crm.project.service;

import com.crm.project.dto.DealDto;
import com.crm.project.entity.Customer;
import com.crm.project.entity.Deal;
import com.crm.project.exception.ResourceNotFoundException;
import com.crm.project.repository.CustomerRepository;
import com.crm.project.repository.DealRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DealService {

    private final DealRepository dealRepository;
    private final CustomerRepository customerRepository;

    public List<DealDto> getAll() {
        return dealRepository.findAll().stream().map(this::toDto).collect(Collectors.toList());
    }

    public DealDto getById(Long id) {
        Deal deal = dealRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Deal not found with id: " + id));
        return toDto(deal);
    }

    public DealDto create(DealDto dto) {
        Customer customer = customerRepository.findById(dto.getCustomerId())
                .orElseThrow(() -> new ResourceNotFoundException("Customer not found with id: " + dto.getCustomerId()));

        Deal deal = Deal.builder()
                .customer(customer)
                .amount(dto.getAmount())
                .stage(dto.getStage() != null ? dto.getStage() : "NEW")
                .closeDate(dto.getCloseDate())
                .build();

        return toDto(dealRepository.save(deal));
    }

    public DealDto update(Long id, DealDto dto) {
        Deal deal = dealRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Deal not found with id: " + id));

        deal.setAmount(dto.getAmount());
        deal.setStage(dto.getStage());
        deal.setCloseDate(dto.getCloseDate());

        return toDto(dealRepository.save(deal));
    }

    public void delete(Long id) {
        if (!dealRepository.existsById(id)) {
            throw new ResourceNotFoundException("Deal not found with id: " + id);
        }
        dealRepository.deleteById(id);
    }

    private DealDto toDto(Deal deal) {
        DealDto dto = new DealDto();
        dto.setId(deal.getId());
        dto.setCustomerId(deal.getCustomer().getId());
        dto.setAmount(deal.getAmount());
        dto.setStage(deal.getStage());
        dto.setCloseDate(deal.getCloseDate());
        return dto;
    }
}
