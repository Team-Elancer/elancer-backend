package com.example.elancer.document.waitproject;

import com.example.elancer.applyproject.model.ApplyProject;
import com.example.elancer.applyproject.repository.ApplyProjectRepository;
import com.example.elancer.common.EnterpriseHelper;
import com.example.elancer.common.FreelancerHelper;
import com.example.elancer.document.common.DocumentBaseTest;
import com.example.elancer.enterprise.model.enterprise.Enterprise;
import com.example.elancer.freelancer.model.Freelancer;
import com.example.elancer.freelancerprofile.model.FreelancerProfile;
import com.example.elancer.freelancerprofile.model.position.PositionType;
import com.example.elancer.integrate.enterprise.EnterpriseLoginHelper;
import com.example.elancer.interviewproject.model.InterviewProject;
import com.example.elancer.interviewproject.model.InterviewStatus;
import com.example.elancer.interviewproject.repository.InterviewProjectRepository;
import com.example.elancer.member.domain.Address;
import com.example.elancer.member.domain.CountryType;
import com.example.elancer.member.dto.MemberLoginResponse;
import com.example.elancer.project.model.*;
import com.example.elancer.project.repository.ProjectRepository;
import com.example.elancer.token.jwt.JwtTokenProvider;
import com.example.elancer.waitproject.dto.LeaveProjectRequest;
import com.example.elancer.waitproject.dto.WaitProjectRequest;
import com.example.elancer.waitproject.model.WaitProject;
import com.example.elancer.waitproject.repsitory.WaitProjectRepository;
import com.example.elancer.waitproject.service.WithdrawFreelancerRequest;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

import java.time.LocalDate;

import static org.springframework.restdocs.headers.HeaderDocumentation.headerWithName;
import static org.springframework.restdocs.headers.HeaderDocumentation.requestHeaders;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class WaitProjectDocumentTest extends DocumentBaseTest {

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private ApplyProjectRepository applyProjectRepository;

    @Autowired
    private InterviewProjectRepository interviewProjectRepository;

    @Autowired
    private WaitProjectRepository waitProjectRepository;

    @AfterEach
    void tearDown() {
        databaseCleaner.clean();
    }

    @Test
    @DisplayName("???????????? ???????????? ?????? ?????? ????????? ?????????")
    public void ????????????_????????????_??????_??????() throws Exception {
        Enterprise enterprise = EnterpriseHelper.??????_??????(enterpriseRepository, passwordEncoder);
        Freelancer freelancer = FreelancerHelper.????????????_??????(freelancerRepository, passwordEncoder);
        MemberLoginResponse memberLoginResponse = EnterpriseLoginHelper.?????????(enterprise.getUserId(), jwtTokenService);

        freelancerProfileRepository.save(new FreelancerProfile(null, freelancer, PositionType.DEVELOPER));
        Project project = projectRepository.save(new Project(
                ProjectType.TELEWORKING,
                ProjectBackGround.BLACK,
                ProjectStep.ANALYSIS,
                "?????????",
                PositionKind.DEVELOPER,
                "Java",
                "????????? ????????????",
                5,
                5,
                "1.???????????? ??? .....",
                LocalDate.now(),
                LocalDate.now().plusMonths(1L),
                LocalDate.now().plusDays(10L),
                new Address(CountryType.KR, "123-123", "?????? ??????", "?????? ??????"),
                6000000,
                10000000,
                5,
                3,
                30,
                35,
                ProjectStatus.PROGRESS,
                enterprise
        ));
        applyProjectRepository.save(ApplyProject.createApplyProject(freelancer, project));
        InterviewProject interviewProject = InterviewProject.createInterviewProject(freelancer, project);
        interviewProject.changeInterviewStatus(InterviewStatus.ACCEPT);
        interviewProjectRepository.save(interviewProject);

        WaitProjectRequest waitProjectRequest = new WaitProjectRequest(project.getNum(), freelancer.getNum());

        mockMvc.perform(post("/wait-project")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(waitProjectRequest))
                        .header(JwtTokenProvider.AUTHORITIES_KEY, memberLoginResponse.getAccessToken()))
                .andExpect(status().isOk())
                .andExpect(status().isOk())
                .andDo(print())
                .andDo(document("wait-project-save",
                        requestHeaders(
                                headerWithName(HttpHeaders.CONTENT_TYPE).description("?????? ???????????? ????????????, ?????? ????????? JSON ????????? ??????"),
                                headerWithName(JwtTokenProvider.AUTHORITIES_KEY).description("jwt ?????? ?????? ?????? ??????.")
                        ),
                        requestFields(
                                fieldWithPath("projectNum").type("Long").description("???????????? ?????????"),
                                fieldWithPath("freelancerNum").type("Long").description("???????????? ?????????")
                        )
                ));
    }

    @Test
    @DisplayName("??????????????? ???????????? ?????? ?????? ?????? ?????????")
    public void ???????????????_????????????_??????_??????_??????_?????????() throws Exception {
        Enterprise enterprise = EnterpriseHelper.??????_??????(enterpriseRepository, passwordEncoder);
        Freelancer freelancer = FreelancerHelper.????????????_??????(freelancerRepository, passwordEncoder);
        MemberLoginResponse memberLoginResponse = EnterpriseLoginHelper.?????????(freelancer.getUserId(), jwtTokenService);


        Project project = projectRepository.save(new Project(
                ProjectType.TELEWORKING,
                ProjectBackGround.BLACK,
                ProjectStep.ANALYSIS,
                "?????????",
                PositionKind.DEVELOPER,
                "Java",
                "????????? ????????????",
                5,
                5,
                "1.???????????? ??? .....",
                LocalDate.now(),
                LocalDate.now().plusMonths(1L),
                LocalDate.now().plusDays(10L),
                new Address(CountryType.KR, "123-123", "?????? ??????", "?????? ??????"),
                6000000,
                10000000,
                5,
                3,
                30,
                35,
                ProjectStatus.PROGRESS,
                enterprise
        ));
        applyProjectRepository.save(ApplyProject.createApplyProject(freelancer, project));
        interviewProjectRepository.save(InterviewProject.createInterviewProject(freelancer, project));
        waitProjectRepository.save(WaitProject.createWaitProject(freelancer, project));

        LeaveProjectRequest leaveProjectRequest = new LeaveProjectRequest(project.getNum());

        mockMvc.perform(delete("/leave-wait-project")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(leaveProjectRequest))
                        .header(JwtTokenProvider.AUTHORITIES_KEY, memberLoginResponse.getAccessToken()))
                .andExpect(status().isOk())
                .andDo(print())
                .andDo(document("leave-wait-project",
                        requestHeaders(
                                headerWithName(HttpHeaders.CONTENT_TYPE).description("?????? ???????????? ????????????, ?????? ????????? JSON ????????? ??????"),
                                headerWithName(JwtTokenProvider.AUTHORITIES_KEY).description("jwt ?????? ?????? ?????? ??????.")
                        ),
                        requestFields(
                                fieldWithPath("projectNum").type("Long").description("???????????? ?????????")
                        )
                ));
    }

    @Test
    @DisplayName("????????? ???????????? ?????? ?????? ?????? ?????????")
    public void ?????????_????????????_??????_??????_??????_?????????() throws Exception {
        Enterprise enterprise = EnterpriseHelper.??????_??????(enterpriseRepository, passwordEncoder);
        Freelancer freelancer = FreelancerHelper.????????????_??????(freelancerRepository, passwordEncoder);
        MemberLoginResponse memberLoginResponse = EnterpriseLoginHelper.?????????(enterprise.getUserId(), jwtTokenService);


        Project project = projectRepository.save(new Project(
                ProjectType.TELEWORKING,
                ProjectBackGround.BLACK,
                ProjectStep.ANALYSIS,
                "?????????",
                PositionKind.DEVELOPER,
                "Java",
                "????????? ????????????",
                5,
                5,
                "1.???????????? ??? .....",
                LocalDate.now(),
                LocalDate.now().plusMonths(1L),
                LocalDate.now().plusDays(10L),
                new Address(CountryType.KR, "123-123", "?????? ??????", "?????? ??????"),
                6000000,
                10000000,
                5,
                3,
                30,
                35,
                ProjectStatus.PROGRESS,
                enterprise
        ));
        applyProjectRepository.save(ApplyProject.createApplyProject(freelancer, project));
        interviewProjectRepository.save(InterviewProject.createInterviewProject(freelancer, project));
        waitProjectRepository.save(WaitProject.createWaitProject(freelancer, project));

        WithdrawFreelancerRequest withdrawFreelancerRequest = new WithdrawFreelancerRequest(project.getNum(), freelancer.getNum());

        mockMvc.perform(delete("/exclude-wait-project")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(withdrawFreelancerRequest))
                        .header(JwtTokenProvider.AUTHORITIES_KEY, memberLoginResponse.getAccessToken()))
                .andExpect(status().isOk())
                .andDo(print())
                .andDo(document("exclude-wait-project",
                        requestHeaders(
                                headerWithName(HttpHeaders.CONTENT_TYPE).description("?????? ???????????? ????????????, ?????? ????????? JSON ????????? ??????"),
                                headerWithName(JwtTokenProvider.AUTHORITIES_KEY).description("jwt ?????? ?????? ?????? ??????.")
                        ),
                        requestFields(
                                fieldWithPath("projectNum").type("Long").description("???????????? ?????????"),
                                fieldWithPath("freelancerNum").type("Long").description("???????????? ?????????")
                        )
                ));
    }
}
