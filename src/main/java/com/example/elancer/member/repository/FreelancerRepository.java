package com.example.elancer.member.repository;

import com.example.elancer.member.domain.Freelancer;
import com.example.elancer.member.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FreelancerRepository extends JpaRepository<Freelancer, Long> {

}
