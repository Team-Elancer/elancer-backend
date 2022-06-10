package com.example.elancer.applyproject.service;

import com.example.elancer.applyproject.dto.ApplyProjectCreateRequest;
import com.example.elancer.applyproject.model.ApplyProject;
import com.example.elancer.applyproject.repository.ApplyProjectRepository;
import com.example.elancer.common.FreelancerHelper;
import com.example.elancer.common.basetest.ServiceBaseTest;
import com.example.elancer.freelancer.model.Freelancer;
import com.example.elancer.login.auth.dto.MemberDetails;
import com.example.elancer.member.domain.Address;
import com.example.elancer.member.domain.CountryType;
import com.example.elancer.project.model.EnterpriseLogo;
import com.example.elancer.project.model.PositionKind;
import com.example.elancer.project.model.Project;
import com.example.elancer.project.model.ProjectBackGround;
import com.example.elancer.project.model.ProjectStep;
import com.example.elancer.project.model.ProjectType;
import com.example.elancer.project.repository.ProjectRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ApplyProjectServiceTest extends ServiceBaseTest {

    @Autowired
    private ApplyProjectService applyProjectService;

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private ApplyProjectRepository applyProjectRepository;

    @DisplayName("프로젝트 지원이 완료 된다.")
    @Test
    public void 프로젝트_지원_생성() {
        //given
        Freelancer freelancer = FreelancerHelper.프리랜서_생성(freelancerRepository, passwordEncoder);
        Project project = projectRepository.save(new Project(
                ProjectType.TELEWORKING,
                ProjectBackGround.BLACK,
                EnterpriseLogo.COUPANG,
                ProjectStep.ANALYSIS,
                "쇼핑몰",
                PositionKind.DEVELOPER,
                "Java",
                "쇼핑몰 프로젝트",
                5,
                5,
                "1.프로젝트 명 .....",
                LocalDate.now(),
                LocalDate.now().plusMonths(1L),
                LocalDate.now().plusDays(10L),
                new Address(CountryType.KR, "123-123", "메인 주소", "상세 주소"),
                6000000,
                10000000,
                5,
                3,
                30,
                35
        ));

        ApplyProjectCreateRequest applyProjectCreateRequest = new ApplyProjectCreateRequest(project.getNum());
        MemberDetails memberDetails = MemberDetails.userDetailsFrom(freelancer);

        //when
        applyProjectService.createApplyProject(applyProjectCreateRequest, memberDetails);

        //then
        List<ApplyProject> applyProjects = applyProjectRepository.findAll();
        Assertions.assertThat(applyProjects).hasSize(1);
    }
}