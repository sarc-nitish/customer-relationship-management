package com.crm.project.service;

import com.crm.project.dto.CustomerDto;
import com.crm.project.entity.Customer;
import com.crm.project.entity.User;
import com.crm.project.exception.ResourceNotFoundException;
import com.crm.project.repository.CustomerRepository;
import com.crm.project.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CustomerService {

    private final CustomerRepository customerRepository;
    private final UserRepository userRepository;

    public List<CustomerDto> getAll() {
        return customerRepository.findAll().stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    public CustomerDto getById(Long id) {
        Customer customer = customerRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Customer not found with id: " + id));
        return toDto(customer);
    }

    public CustomerDto create(CustomerDto dto) {
        Customer customer = toEntity(dto);
        customer.setId(null);
        Customer saved = customerRepository.save(customer);
        return toDto(saved);
    }

    public CustomerDto update(Long id, CustomerDto dto) {
        Customer existing = customerRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Customer not found with id: " + id));

        existing.setName(dto.getName());
        existing.setCompany(dto.getCompany());
        existing.setPhone(dto.getPhone());
        existing.setEmail(dto.getEmail());
        existing.setStatus(dto.getStatus());

        if (dto.getAssignedToId() != null) {
            User user = userRepository.findById(dto.getAssignedToId())
                    .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + dto.getAssignedToId()));
            existing.setAssignedTo(user);
        }

        Customer updated = customerRepository.save(existing);
        return toDto(updated);
    }

    public void delete(Long id) {
        if (!customerRepository.existsById(id)) {
            throw new ResourceNotFoundException("Customer not found with id: " + id);
        }
        customerRepository.deleteById(id);
    }

    private CustomerDto toDto(Customer customer) {
        CustomerDto dto = new CustomerDto();
        dto.setId(customer.getId());
        dto.setName(customer.getName());
        dto.setCompany(customer.getCompany());
        dto.setPhone(customer.getPhone());
        dto.setEmail(customer.getEmail());
        dto.setStatus(customer.getStatus());
        if (customer.getAssignedTo() != null) {
            dto.setAssignedToId(customer.getAssignedTo().getId());
        }
        return dto;
    }

    private Customer toEntity(CustomerDto dto) {
        Customer customer = Customer.builder()
                .id(dto.getId())
                .name(dto.getName())
                .company(dto.getCompany())
                .phone(dto.getPhone())
                .email(dto.getEmail())
                .status(dto.getStatus() != null ? dto.getStatus() : "NEW")
                .build();

        if (dto.getAssignedToId() != null) {
            User user = userRepository.findById(dto.getAssignedToId())
                    .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + dto.getAssignedToId()));
            customer.setAssignedTo(user);
        }

        return customer;
    }
}
