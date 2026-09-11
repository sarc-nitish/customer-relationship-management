package com.crm.project.service;

import com.crm.project.dto.TaskDto;
import com.crm.project.entity.Customer;
import com.crm.project.entity.Task;
import com.crm.project.entity.User;
import com.crm.project.exception.ResourceNotFoundException;
import com.crm.project.repository.CustomerRepository;
import com.crm.project.repository.TaskRepository;
import com.crm.project.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TaskService {

    private final TaskRepository taskRepository;
    private final UserRepository userRepository;
    private final CustomerRepository customerRepository;

    public List<TaskDto> getAll() {
        return taskRepository.findAll().stream().map(this::toDto).collect(Collectors.toList());
    }

    public TaskDto getById(Long id) {
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Task not found with id: " + id));
        return toDto(task);
    }

    public TaskDto create(TaskDto dto) {
        Task task = Task.builder()
                .title(dto.getTitle())
                .description(dto.getDescription())
                .dueDate(dto.getDueDate())
                .status(dto.getStatus() != null ? dto.getStatus() : "PENDING")
                .build();

        if (dto.getAssignedToId() != null) {
            User user = userRepository.findById(dto.getAssignedToId())
                    .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + dto.getAssignedToId()));
            task.setAssignedTo(user);
        }

        if (dto.getCustomerId() != null) {
            Customer customer = customerRepository.findById(dto.getCustomerId())
                    .orElseThrow(() -> new ResourceNotFoundException("Customer not found with id: " + dto.getCustomerId()));
            task.setCustomer(customer);
        }

        return toDto(taskRepository.save(task));
    }

    public TaskDto update(Long id, TaskDto dto) {
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Task not found with id: " + id));

        task.setTitle(dto.getTitle());
        task.setDescription(dto.getDescription());
        task.setDueDate(dto.getDueDate());
        task.setStatus(dto.getStatus());

        return toDto(taskRepository.save(task));
    }

    public void delete(Long id) {
        if (!taskRepository.existsById(id)) {
            throw new ResourceNotFoundException("Task not found with id: " + id);
        }
        taskRepository.deleteById(id);
    }

    private TaskDto toDto(Task task) {
        TaskDto dto = new TaskDto();
        dto.setId(task.getId());
        dto.setTitle(task.getTitle());
        dto.setDescription(task.getDescription());
        dto.setDueDate(task.getDueDate());
        dto.setStatus(task.getStatus());
        if (task.getAssignedTo() != null) dto.setAssignedToId(task.getAssignedTo().getId());
        if (task.getCustomer() != null) dto.setCustomerId(task.getCustomer().getId());
        return dto;
    }
}
