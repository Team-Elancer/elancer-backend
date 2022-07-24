package com.example.elancer.enterprise.repository;

import com.example.elancer.enterprise.model.enterpriseintro.EnterpriseMainBiz;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EnterpriseMainBizRepository extends JpaRepository<EnterpriseMainBiz, Long> {

    void deleteByEnterpriseIntroNum(Long enterpriseIntroNum);
}
