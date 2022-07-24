package com.example.elancer.enterprise.repository;

import com.example.elancer.enterprise.model.enterpriseintro.EnterpriseMainBiz;
import com.example.elancer.enterprise.model.enterpriseintro.EnterpriseSubBiz;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EnterpriseSubBizRepository extends JpaRepository<EnterpriseSubBiz, Long> {

    void deleteByEnterpriseIntroNum(Long enterpriseIntroNum);
}
