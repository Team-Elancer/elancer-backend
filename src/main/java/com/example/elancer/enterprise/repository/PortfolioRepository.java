package com.example.elancer.enterprise.repository;

import com.example.elancer.enterprise.model.enterpriseintro.Portfolio;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PortfolioRepository extends JpaRepository<Portfolio, Long> {
}
