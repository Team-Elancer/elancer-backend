package com.example.elancer.document.freelancer;

import com.example.elancer.common.EnterpriseHelper;
import com.example.elancer.common.FreelancerHelper;
import com.example.elancer.document.common.DocumentBaseTest;
import com.example.elancer.enterprise.model.enterprise.Enterprise;
import com.example.elancer.freelancer.model.Freelancer;
import com.example.elancer.freelancerprofile.model.FreelancerProfile;
import com.example.elancer.freelancerprofile.model.position.PositionType;
import com.example.elancer.common.LoginHelper;
import com.example.elancer.member.domain.Address;
import com.example.elancer.member.domain.CountryType;
import com.example.elancer.member.dto.MemberLoginResponse;
import com.example.elancer.project.model.*;
import com.example.elancer.project.repository.ProjectRepository;
import com.example.elancer.token.jwt.JwtTokenProvider;
import com.example.elancer.wishprojects.controller.WishProjectControllerPath;
import com.example.elancer.wishprojects.dto.WishProjectDeleteRequest;
import com.example.elancer.wishprojects.dto.WishProjectSaveRequest;
import com.example.elancer.wishprojects.model.WishProject;
import com.example.elancer.wishprojects.repository.WishProjectRepository;
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

public class WishProjectDocumentTest extends DocumentBaseTest {
    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private WishProjectRepository wishProjectRepository;

    @DisplayName("???????????? ??? ?????? ????????? ?????????")
    @Test
    public void ???????????????_??????_?????????() throws Exception {
        //given
        Freelancer freelancer = FreelancerHelper.????????????_??????(freelancerRepository, passwordEncoder);
        Enterprise enterprise = EnterpriseHelper.??????_??????(enterpriseRepository, passwordEncoder);
        MemberLoginResponse memberLoginResponse = LoginHelper.?????????(freelancer.getUserId(), jwtTokenService);

        FreelancerProfile freelancerProfile = freelancerProfileRepository.save(new FreelancerProfile("greeting", freelancer, PositionType.ETC));

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

        WishProjectSaveRequest wishProjectSaveRequest = new WishProjectSaveRequest(project.getNum());

        //when & then
        mockMvc.perform(post(WishProjectControllerPath.WISH_PROJECT_SAVE)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .header(JwtTokenProvider.AUTHORITIES_KEY, memberLoginResponse.getAccessToken())
                        .content(objectMapper.writeValueAsString(wishProjectSaveRequest)))
                .andExpect(status().isCreated())
                .andDo(print())
                .andDo(document("wish-project-save",
                        requestHeaders(
                                headerWithName(HttpHeaders.CONTENT_TYPE).description("?????? ???????????? ????????????, ?????? ????????? JSON ????????? ??????")
                        ),
                        requestFields(
                                fieldWithPath("projectNum").type("Long").description("?????? ???????????? ????????? ?????? ??????.")
                        )
                ));
    }

    @DisplayName("???????????? ??? ?????? ????????? ?????????")
    @Test
    public void ???????????????_??????_?????????() throws Exception {
        //given
        Freelancer freelancer = FreelancerHelper.????????????_??????(freelancerRepository, passwordEncoder);
        Enterprise enterprise = EnterpriseHelper.??????_??????(enterpriseRepository, passwordEncoder);
        MemberLoginResponse memberLoginResponse = LoginHelper.?????????(freelancer.getUserId(), jwtTokenService);

        FreelancerProfile freelancerProfile = freelancerProfileRepository.save(new FreelancerProfile("greeting", freelancer, PositionType.ETC));

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
        WishProject wishProject = wishProjectRepository.save(new WishProject(freelancer, project));

        WishProjectDeleteRequest wishProjectDeleteRequest = new WishProjectDeleteRequest(wishProject.getNum());

        //when & then
        mockMvc.perform(delete(WishProjectControllerPath.WISH_PROJECT_DELETE)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .header(JwtTokenProvider.AUTHORITIES_KEY, memberLoginResponse.getAccessToken())
                        .content(objectMapper.writeValueAsString(wishProjectDeleteRequest)))
                .andExpect(status().isOk())
                .andDo(print())
                .andDo(document("wish-project-delete",
                        requestHeaders(
                                headerWithName(HttpHeaders.CONTENT_TYPE).description("?????? ???????????? ????????????, ?????? ????????? JSON ????????? ??????")
                        ),
                        requestFields(
                                fieldWithPath("projectNum").type("Long").description("?????????????????? ?????? ????????? ?????? ??????.")
                        )
                ));
    }

    @AfterEach
    void tearDown() {
        databaseCleaner.clean();
    }
}
