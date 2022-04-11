package com.example.elancer.member.domain;

import com.example.elancer.member.repository.EnterpriseRepository;
import com.example.elancer.member.repository.FreelancerRepository;
import com.example.elancer.member.repository.MemberRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@ActiveProfiles("h2")
class MemberTest {
    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private FreelancerRepository freelancerRepository;

    @Autowired
    private EnterpriseRepository enterpriseRepository;

    @DisplayName("프리랜서, 기업 생성및 조회 확인.")
    @Test
    public void name() {
        //given
        Freelancer freelancer1 = new Freelancer("F-test1");
        Freelancer freelancer2 = new Freelancer("F-test2");
        Freelancer freelancer3 = new Freelancer("F-test3");

        Enterprise enterprise1 = new Enterprise("T-test1");
        Enterprise enterprise2 = new Enterprise("T-test2");
        Enterprise enterprise3 = new Enterprise("T-test3");

        memberRepository.save(freelancer1);
        memberRepository.save(enterprise1);

        freelancerRepository.save(freelancer2);
        freelancerRepository.save(freelancer3);

        enterpriseRepository.save(enterprise2);
        enterpriseRepository.save(enterprise3);
        //when
        List<Member> allMember = memberRepository.findAll();
        List<Freelancer> allFree = freelancerRepository.findAll();
        List<Enterprise> allEnter = enterpriseRepository.findAll();

        //then
        Assertions.assertThat(allMember).hasSize(6);
        Assertions.assertThat(allFree).hasSize(3);
        Assertions.assertThat(allEnter).hasSize(3);
    }
}