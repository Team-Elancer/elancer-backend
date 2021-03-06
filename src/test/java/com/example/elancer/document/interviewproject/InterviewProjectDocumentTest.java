package com.example.elancer.document.interviewproject;

import com.example.elancer.applyproject.model.ApplyProject;
import com.example.elancer.applyproject.repository.ApplyProjectRepository;
import com.example.elancer.common.EnterpriseHelper;
import com.example.elancer.common.FreelancerHelper;
import com.example.elancer.common.LoginHelper;
import com.example.elancer.document.common.DocumentBaseTest;
import com.example.elancer.enterprise.model.enterprise.Enterprise;
import com.example.elancer.freelancer.model.Freelancer;
import com.example.elancer.integrate.enterprise.EnterpriseLoginHelper;
import com.example.elancer.interviewproject.dto.AcceptInterviewRequest;
import com.example.elancer.interviewproject.dto.CreateInterviewProjectRequest;
import com.example.elancer.interviewproject.dto.RejectInterviewRequest;
import com.example.elancer.interviewproject.model.InterviewProject;
import com.example.elancer.interviewproject.repository.InterviewProjectRepository;
import com.example.elancer.member.domain.Address;
import com.example.elancer.member.domain.CountryType;
import com.example.elancer.member.dto.MemberLoginResponse;
import com.example.elancer.project.model.*;
import com.example.elancer.project.repository.ProjectRepository;
import com.example.elancer.token.jwt.JwtTokenProvider;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

import java.time.LocalDate;

import static org.springframework.restdocs.headers.HeaderDocumentation.*;
import static org.springframework.restdocs.headers.HeaderDocumentation.headerWithName;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class InterviewProjectDocumentTest extends DocumentBaseTest {

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private ApplyProjectRepository applyProjectRepository;

    @Autowired
    private InterviewProjectRepository interviewProjectRepository;

    @AfterEach
    void tearDown() {
        databaseCleaner.clean();
    }


    @Test
    @DisplayName("?????? ???????????? ????????? ?????? ?????????")
    public void ??????_????????????_?????????_??????_?????????() throws Exception {
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


        CreateInterviewProjectRequest createInterviewProjectRequest = new CreateInterviewProjectRequest(project.getNum(), freelancer.getNum());



        mockMvc.perform(post("/interview-project")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(createInterviewProjectRequest))
                        .header(JwtTokenProvider.AUTHORITIES_KEY, memberLoginResponse.getAccessToken()))
                .andExpect(status().isOk())
                .andExpect(status().isOk())
                .andDo(print())
                .andDo(document("interview-project",
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
    @DisplayName("??????????????? ????????? ?????? ?????? ?????????")
    public void ???????????????_?????????_??????_??????_?????????() throws Exception {
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

        AcceptInterviewRequest acceptInterviewRequest = new AcceptInterviewRequest(project.getNum());



        mockMvc.perform(post("/accept-interview-project")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(acceptInterviewRequest))
                        .header(JwtTokenProvider.AUTHORITIES_KEY, memberLoginResponse.getAccessToken()))
                .andExpect(status().isOk())
                .andDo(print())
                .andDo(document("accept-interview-project",
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
    @DisplayName("????????? ????????? ?????? ?????? ?????????")
    public void ?????????_?????????_??????_??????_?????????() throws Exception {
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

        RejectInterviewRequest rejectInterviewRequest = new RejectInterviewRequest(project.getNum(), freelancer.getNum());


        mockMvc.perform(delete("/reject-interview-project")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(rejectInterviewRequest))
                        .header(JwtTokenProvider.AUTHORITIES_KEY, memberLoginResponse.getAccessToken()))
                .andExpect(status().isOk())
                .andDo(print())
                .andDo(document("reject-interview-project",
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
