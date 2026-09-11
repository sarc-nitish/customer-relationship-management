package com.crm.project.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "customers")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    private String company;

    private String phone;

    private String email;

    @Column(nullable = false)
    private String status; // NEW, ACTIVE, INACTIVE

    @ManyToOne
    @JoinColumn(name = "assigned_to")
    private User assignedTo;

    @Builder.Default
    private LocalDateTime createdAt = LocalDateTime.now();
}
