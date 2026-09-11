package com.crm.project.repository;

import com.crm.project.entity.Lead;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LeadRepository extends JpaRepository<Lead, Long> {
    List<Lead> findByCustomerId(Long customerId);
}
