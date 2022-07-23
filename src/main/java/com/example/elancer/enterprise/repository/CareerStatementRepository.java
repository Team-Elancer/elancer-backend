package com.example.elancer.enterprise.repository;

import com.example.elancer.enterprise.model.enterpriseintro.CareerStatement;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CareerStatementRepository extends JpaRepository<CareerStatement, Long> {
}
