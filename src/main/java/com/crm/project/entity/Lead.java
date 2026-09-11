package com.crm.project.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "leads")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Lead {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "customer_id", nullable = false)
    private Customer customer;

    private String source; // WEBSITE, REFERRAL, ADS, COLD_CALL

    @Column(nullable = false)
    private String stage; // NEW, CONTACTED, QUALIFIED, LOST

    private Double value;

    @Builder.Default
    private LocalDateTime createdAt = LocalDateTime.now();
}
