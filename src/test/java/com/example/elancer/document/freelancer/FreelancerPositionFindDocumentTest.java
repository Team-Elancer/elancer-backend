package com.example.elancer.document.freelancer;

import com.example.elancer.common.FreelancerHelper;
import com.example.elancer.document.common.DocumentBaseTest;
import com.example.elancer.freelancer.model.Freelancer;
import com.example.elancer.freelancerprofile.controller.position.FreelancerPositionFindControllerPath;
import com.example.elancer.freelancerprofile.model.FreelancerProfile;
import com.example.elancer.freelancerprofile.model.position.PositionType;
import com.example.elancer.freelancerprofile.model.position.designer.DesignDetailRole;
import com.example.elancer.freelancerprofile.model.position.designer.DesignDetailSkill;
import com.example.elancer.freelancerprofile.model.position.designer.DesignRole;
import com.example.elancer.freelancerprofile.model.position.designer.DesignSkill;
import com.example.elancer.freelancerprofile.model.position.designer.Designer;
import com.example.elancer.freelancerprofile.model.position.developer.Developer;
import com.example.elancer.freelancerprofile.model.position.developer.cskill.CDetailSkill;
import com.example.elancer.freelancerprofile.model.position.developer.cskill.ClangSkill;
import com.example.elancer.freelancerprofile.model.position.developer.dbskill.DBDetailSkill;
import com.example.elancer.freelancerprofile.model.position.developer.dbskill.DBSkill;
import com.example.elancer.freelancerprofile.model.position.developer.dotnet.DotNetDetailSkill;
import com.example.elancer.freelancerprofile.model.position.developer.dotnet.DotNetSkill;
import com.example.elancer.freelancerprofile.model.position.developer.javascript.JavaScriptDetailSkill;
import com.example.elancer.freelancerprofile.model.position.developer.javascript.JavaScriptSkill;
import com.example.elancer.freelancerprofile.model.position.developer.javaskill.JavaDetailSkill;
import com.example.elancer.freelancerprofile.model.position.developer.javaskill.JavaSkill;
import com.example.elancer.freelancerprofile.model.position.developer.mobileskill.MobileAppDetailSkill;
import com.example.elancer.freelancerprofile.model.position.developer.mobileskill.MobileAppSkill;
import com.example.elancer.freelancerprofile.model.position.developer.phpaspskill.PhpOrAspDetailSkill;
import com.example.elancer.freelancerprofile.model.position.developer.phpaspskill.PhpOrAspSkill;
import com.example.elancer.freelancerprofile.model.position.etc.EtcDetailRole;
import com.example.elancer.freelancerprofile.model.position.etc.EtcRole;
import com.example.elancer.freelancerprofile.model.position.etc.PositionEtc;
import com.example.elancer.freelancerprofile.model.position.planner.Planner;
import com.example.elancer.freelancerprofile.model.position.planner.PlannerDetailField;
import com.example.elancer.freelancerprofile.model.position.planner.PlannerField;
import com.example.elancer.freelancerprofile.model.position.publisher.Publisher;
import com.example.elancer.freelancerprofile.model.position.publisher.PublishingDetailSkill;
import com.example.elancer.freelancerprofile.model.position.publisher.PublishingSkill;
import com.example.elancer.common.LoginHelper;
import com.example.elancer.member.dto.MemberLoginResponse;
import com.example.elancer.token.jwt.JwtTokenProvider;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.restdocs.headers.HeaderDocumentation.headerWithName;
import static org.springframework.restdocs.headers.HeaderDocumentation.requestHeaders;
import static org.springframework.restdocs.headers.HeaderDocumentation.responseHeaders;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class FreelancerPositionFindDocumentTest extends DocumentBaseTest {

    @DisplayName("???????????? ????????? ????????? ????????? ?????? ?????????")
    @Test
    public void ????????????_?????????_?????????_?????????_??????_?????????() throws Exception {
        //given
        Freelancer freelancer = freelancerRepository.save(FreelancerHelper.????????????_??????(freelancerRepository, passwordEncoder));
        MemberLoginResponse memberLoginResponse = LoginHelper.?????????(freelancer.getUserId(), jwtTokenService);

        FreelancerProfile freelancerProfile = freelancerProfileRepository.save(new FreelancerProfile("greeting", freelancer, PositionType.DEVELOPER));

        Developer developer = Developer.createBasicDeveloper(PositionType.DEVELOPER, freelancerProfile, "java,spring", "backend");
        List<JavaSkill> javaSkills = Arrays.asList(JavaSkill.createJavaSkill(JavaDetailSkill.SPRING, developer), JavaSkill.createJavaSkill(JavaDetailSkill.BACK_END, developer));
        List<MobileAppSkill> mobileAppSkills = Arrays.asList(MobileAppSkill.createMobileAppSkill(MobileAppDetailSkill.ANDROID, developer));
        List<PhpOrAspSkill> phpOrAspSkills = Arrays.asList(PhpOrAspSkill.createPhpOrAspSkill(PhpOrAspDetailSkill.PHP, developer));
        List<DotNetSkill> dotNetSkills = Arrays.asList(DotNetSkill.createDotNetSkill(DotNetDetailSkill.C, developer));
        List<JavaScriptSkill> javaScriptSkills = Arrays.asList(JavaScriptSkill.createJavaScriptSkill(JavaScriptDetailSkill.ANGULAR_JS, developer));
        List<ClangSkill> clangSkills = Arrays.asList(ClangSkill.createCSkill(CDetailSkill.EMBEDDED, developer));
        List<DBSkill> dbSkills = Arrays.asList(DBSkill.createDBSkill(DBDetailSkill.MARIADB, developer), DBSkill.createDBSkill(DBDetailSkill.MYSQL, developer));
        String etc = "etc";

        developer.coverDeveloperSkills(
                javaSkills,
                mobileAppSkills,
                phpOrAspSkills,
                dotNetSkills,
                javaScriptSkills,
                clangSkills,
                dbSkills,
                etc
        );

        freelancerProfile.coverPosition(developer);

        freelancerProfileRepository.save(freelancerProfile);

        //when & then
        mockMvc.perform(RestDocumentationRequestBuilders.get(FreelancerPositionFindControllerPath.FREELANCER_PROFILE_POSITION_DEVELOPER_FIND)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .header(JwtTokenProvider.AUTHORITIES_KEY, memberLoginResponse.getAccessToken()))
                .andExpect(status().isOk())
                .andDo(print())
                .andDo(document("freelancer-profile-developer-find",
                        requestHeaders(
                                headerWithName(JwtTokenProvider.AUTHORITIES_KEY).description("jwt ?????? ?????? ?????? ??????.")
                        ),
                        responseHeaders(
                                headerWithName(HttpHeaders.CONTENT_TYPE).description("?????? ???????????? ????????????, ?????? ????????? JSON ????????? ??????")
                        ),
                        responseFields(
                                fieldWithPath("focusSkills").type("List<String>").description("???????????? ????????? ????????? ?????? ??????."),
                                fieldWithPath("roles").type("List<String>").description("???????????? ????????? ?????? ?????? ??????."),
                                fieldWithPath("javaDetailSkills").type("List<JavaDetailSkill>").description("???????????? ????????? JAVA ?????? ?????? ??????."),
                                fieldWithPath("mobileAppDetailSkills").type("List<MobileAppDetailSkill>").description("???????????? ????????? Mobile App ?????? ?????? ??????."),
                                fieldWithPath("phpOrAspDetailSkills").type("List<PhpOrAspDetailSkill>").description("???????????? ????????? PHP/ASP ?????? ?????? ??????."),
                                fieldWithPath("dotNetDetailSkills").type("List<DotNetDetailSkill>").description("???????????? ????????? .NET ?????? ?????? ??????."),
                                fieldWithPath("javaScriptDetailSkills").type("List<JavaScriptDetailSkill>").description("???????????? ????????? JavaScript ?????? ?????? ??????."),
                                fieldWithPath("cdetailSkills").type("List<CDetailSkill>").description("???????????? ????????? C/C++ ?????? ?????? ??????."),
                                fieldWithPath("dbDetailSkills").type("List<DBDetailSkill>").description("???????????? ????????? DB ?????? ?????? ??????."),
                                fieldWithPath("etcSkill").type("String").description("???????????? ????????? ???????????? ?????? ??????.")
                        )
                ));
    }

    @DisplayName("???????????? ????????? ???????????? ????????? ?????? ?????????")
    @Test
    public void ????????????_?????????_????????????_?????????_??????_?????????() throws Exception {
        //given
        Freelancer freelancer = freelancerRepository.save(FreelancerHelper.????????????_??????(freelancerRepository, passwordEncoder));
        MemberLoginResponse memberLoginResponse = LoginHelper.?????????(freelancer.getUserId(), jwtTokenService);

        FreelancerProfile freelancerProfile = freelancerProfileRepository.save(new FreelancerProfile("greeting", freelancer, PositionType.DEVELOPER));

        Publisher publisher = Publisher.createBasicPublisher(PositionType.PUBLISHER, freelancerProfile, "etcPubSkill");
        List<PublishingSkill> publishingSkillList = Arrays.asList(PublishingSkill.createPublishingSkill(PublishingDetailSkill.HTML5, publisher), PublishingSkill.createPublishingSkill(PublishingDetailSkill.CSS, publisher));
        publisher.coverPublishingSkill(publishingSkillList);
        freelancerProfile.coverPosition(publisher);

        freelancerProfileRepository.save(freelancerProfile);

        //when & then
        mockMvc.perform(RestDocumentationRequestBuilders.get(FreelancerPositionFindControllerPath.FREELANCER_PROFILE_POSITION_PUBLISHER_FIND)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .header(JwtTokenProvider.AUTHORITIES_KEY, memberLoginResponse.getAccessToken()))
                .andExpect(status().isOk())
                .andDo(print())
                .andDo(document("freelancer-profile-publisher-find",
                        requestHeaders(
                                headerWithName(JwtTokenProvider.AUTHORITIES_KEY).description("jwt ?????? ?????? ?????? ??????.")
                        ),
                        responseHeaders(
                                headerWithName(HttpHeaders.CONTENT_TYPE).description("?????? ???????????? ????????????, ?????? ????????? JSON ????????? ??????")
                        ),
                        responseFields(
                                fieldWithPath("publishingDetailSkills").type("List<PublishingDetailSkill>").description("???????????? ???????????? ??????,?????? ?????? ??????."),
                                fieldWithPath("etcSkill").type("String").description("???????????? ???????????? ???????????? ?????? ??????.")
                        )
                ));
    }

    @DisplayName("???????????? ????????? ???????????? ????????? ?????? ?????????")
    @Test
    public void ????????????_?????????_????????????_?????????_??????_?????????() throws Exception {
        //given
        Freelancer freelancer = freelancerRepository.save(FreelancerHelper.????????????_??????(freelancerRepository, passwordEncoder));
        MemberLoginResponse memberLoginResponse = LoginHelper.?????????(freelancer.getUserId(), jwtTokenService);

        FreelancerProfile freelancerProfile = freelancerProfileRepository.save(new FreelancerProfile("greeting", freelancer, PositionType.DEVELOPER));

        Designer designer = Designer.createBasicDesigner(PositionType.DESIGNER, freelancerProfile);
        List<DesignRole> designRoles = Arrays.asList(DesignRole.createDesignRole(DesignDetailRole.APP_DESIGN, designer), DesignRole.createDesignRole(DesignDetailRole.GAME_DESIGN, designer));
        List<DesignSkill> designSkills = Arrays.asList(DesignSkill.createDesignSkill(DesignDetailSkill.FLASH, designer));
        String etcRole = "etcRole";
        String etcSkill = "etcSkill";
        designer.coverDesignRoleAndSkill(
                designRoles,
                designSkills,
                etcRole,
                etcSkill
        );
        freelancerProfile.coverPosition(designer);

        freelancerProfileRepository.save(freelancerProfile);

        //when & then
        mockMvc.perform(RestDocumentationRequestBuilders.get(FreelancerPositionFindControllerPath.FREELANCER_PROFILE_POSITION_DESIGNER_FIND)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .header(JwtTokenProvider.AUTHORITIES_KEY, memberLoginResponse.getAccessToken()))
                .andExpect(status().isOk())
                .andDo(print())
                .andDo(document("freelancer-profile-designer-find",
                        requestHeaders(
                                headerWithName(JwtTokenProvider.AUTHORITIES_KEY).description("jwt ?????? ?????? ?????? ??????.")
                        ),
                        responseHeaders(
                                headerWithName(HttpHeaders.CONTENT_TYPE).description("?????? ???????????? ????????????, ?????? ????????? JSON ????????? ??????")
                        ),
                        responseFields(
                                fieldWithPath("designDetailRoles").type("List<DesignDetailRole>").description("???????????? ???????????? ?????? ?????? ??????."),
                                fieldWithPath("etcRole").type("String").description("???????????? ???????????? ???????????? ?????? ??????."),
                                fieldWithPath("designDetailSkills").type("List<DesignDetailSkill>").description("???????????? ???????????? ?????? ?????? ??????."),
                                fieldWithPath("etcSkill").type("String").description("???????????? ???????????? ???????????? ?????? ??????.")
                        )
                ));
    }

    @DisplayName("???????????? ????????? ????????? ????????? ?????? ?????????")
    @Test
    public void ????????????_?????????_?????????_?????????_??????_?????????() throws Exception {
        //given
        Freelancer freelancer = freelancerRepository.save(FreelancerHelper.????????????_??????(freelancerRepository, passwordEncoder));
        MemberLoginResponse memberLoginResponse = LoginHelper.?????????(freelancer.getUserId(), jwtTokenService);

        FreelancerProfile freelancerProfile = freelancerProfileRepository.save(new FreelancerProfile("greeting", freelancer, PositionType.DEVELOPER));

        Planner planner = Planner.createBasicPlanner(PositionType.PLANNER, freelancerProfile);
        List<PlannerField> plannerFields = Arrays.asList(PlannerField.createPlannerField(PlannerDetailField.APP_PLAN, planner), PlannerField.createPlannerField(PlannerDetailField.WEB_PLAN, planner));
        String etcField = "etcField";
        planner.coverAllField(plannerFields, etcField);
        freelancerProfile.coverPosition(planner);

        freelancerProfileRepository.save(freelancerProfile);

        //when & then
        mockMvc.perform(RestDocumentationRequestBuilders.get(FreelancerPositionFindControllerPath.FREELANCER_PROFILE_POSITION_PLANNER_FIND)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .header(JwtTokenProvider.AUTHORITIES_KEY, memberLoginResponse.getAccessToken()))

                .andExpect(status().isOk())
                .andDo(print())
                .andDo(document("freelancer-profile-planner-find",
                        requestHeaders(
                                headerWithName(JwtTokenProvider.AUTHORITIES_KEY).description("jwt ?????? ?????? ?????? ??????.")
                        ),
                        responseHeaders(
                                headerWithName(HttpHeaders.CONTENT_TYPE).description("?????? ???????????? ????????????, ?????? ????????? JSON ????????? ??????")
                        ),
                        responseFields(
                                fieldWithPath("plannerDetailFields").type("List<PlannerDetailField>").description("???????????? ????????? ???????????? ?????? ??????."),
                                fieldWithPath("etcField").type("String").description("???????????? ????????? ???????????? ?????? ??????.")
                        )
                ));
    }

    @DisplayName("???????????? ????????? ?????? ????????? ?????? ?????????")
    @Test
    public void ????????????_?????????_??????_?????????_??????_?????????() throws Exception {
        //given
        Freelancer freelancer = freelancerRepository.save(FreelancerHelper.????????????_??????(freelancerRepository, passwordEncoder));
        MemberLoginResponse memberLoginResponse = LoginHelper.?????????(freelancer.getUserId(), jwtTokenService);

        FreelancerProfile freelancerProfile = freelancerProfileRepository.save(new FreelancerProfile("greeting", freelancer, PositionType.DEVELOPER));

        PositionEtc positionEtc = PositionEtc.createBasicPositionEtc(PositionType.ETC, freelancerProfile);
        List<EtcRole> etcRoles = Arrays.asList(EtcRole.createEtcRole(EtcDetailRole.DBA, positionEtc));
        String positionEtcField = "positionEtcField";
        freelancerProfile.coverPosition(positionEtc);

        freelancerProfileRepository.save(freelancerProfile);

        //when & then
        mockMvc.perform(RestDocumentationRequestBuilders.get(FreelancerPositionFindControllerPath.FREELANCER_PROFILE_POSITION_ETC_FIND)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .header(JwtTokenProvider.AUTHORITIES_KEY, memberLoginResponse.getAccessToken()))
                .andExpect(status().isOk())
                .andDo(print())
                .andDo(document("freelancer-profile-etc-find",
                        requestHeaders(
                                headerWithName(JwtTokenProvider.AUTHORITIES_KEY).description("jwt ?????? ?????? ?????? ??????.")
                        ),
                        responseHeaders(
                                headerWithName(HttpHeaders.CONTENT_TYPE).description("?????? ???????????? ????????????, ?????? ????????? JSON ????????? ??????")
                        ),
                        responseFields(
                                fieldWithPath("etcDetailRoles").type("List<EtcDetailRole>").description("???????????? ???????????? ?????? ?????? ??????."),
                                fieldWithPath("positionEtcRole").type("String").description("???????????? ???????????? ???????????? ????????? ?????? ??????.")
                        )
                ));
    }

    @AfterEach
    void tearDown() {
        databaseCleaner.clean();
    }
}
