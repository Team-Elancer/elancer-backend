package com.example.elancer.integrate.freelancerprofile;

import com.example.elancer.common.FreelancerHelper;
import com.example.elancer.freelancer.model.Freelancer;
import com.example.elancer.freelancer.model.IntroBackGround;
import com.example.elancer.freelancer.model.MailReceptionState;
import com.example.elancer.freelancer.model.WorkPossibleState;
import com.example.elancer.freelancer.repository.FreelancerRepository;
import com.example.elancer.freelancerprofile.controller.position.FreelancerPositionEnumControllerPath;
import com.example.elancer.freelancerprofile.controller.profile.FreelancerProfileAlterControllerPath;
import com.example.elancer.freelancerprofile.controller.profile.FreelancerProfileFindControllerPath;
import com.example.elancer.freelancerprofile.dto.request.AcademicAbilityCoverRequest;
import com.example.elancer.freelancerprofile.dto.request.AcademicAbilityCoverRequests;
import com.example.elancer.freelancerprofile.dto.request.CareerCoverRequest;
import com.example.elancer.freelancerprofile.dto.request.CareerCoverRequests;
import com.example.elancer.freelancerprofile.dto.request.EducationAndLicenseAndLanguageRequests;
import com.example.elancer.freelancerprofile.dto.request.EducationCoverRequest;
import com.example.elancer.freelancerprofile.dto.request.IntroduceCoverRequest;
import com.example.elancer.freelancerprofile.dto.request.LanguageCoverRequest;
import com.example.elancer.freelancerprofile.dto.request.LicenseCoverRequest;
import com.example.elancer.freelancerprofile.dto.request.ProjectHistoryCoverRequests;
import com.example.elancer.freelancerprofile.dto.request.ProjectHistoryRequest;
import com.example.elancer.freelancerprofile.model.FreelancerProfile;
import com.example.elancer.freelancerprofile.model.academic.AcademicAbility;
import com.example.elancer.freelancerprofile.model.academic.state.AcademicState;
import com.example.elancer.freelancerprofile.model.academic.state.SchoolLevel;
import com.example.elancer.freelancerprofile.model.career.Career;
import com.example.elancer.freelancerprofile.model.career.CompanyPosition;
import com.example.elancer.freelancerprofile.model.education.Education;
import com.example.elancer.freelancerprofile.model.language.Language;
import com.example.elancer.freelancerprofile.model.language.LanguageAbility;
import com.example.elancer.freelancerprofile.model.license.License;
import com.example.elancer.freelancerprofile.model.position.PositionType;
import com.example.elancer.freelancerprofile.model.position.developer.Developer;
import com.example.elancer.freelancerprofile.model.position.planner.Planner;
import com.example.elancer.freelancerprofile.model.projecthistory.DevelopEnvironment;
import com.example.elancer.freelancerprofile.model.projecthistory.DevelopField;
import com.example.elancer.freelancerprofile.model.projecthistory.ProjectHistory;
import com.example.elancer.freelancerprofile.repository.FreelancerProfileRepository;
import com.example.elancer.freelancerprofile.repository.profile.academic.AcademicRepository;
import com.example.elancer.freelancerprofile.repository.profile.career.CareerRepository;
import com.example.elancer.freelancerprofile.repository.profile.education.EducationRepository;
import com.example.elancer.freelancerprofile.repository.profile.language.LanguageRepository;
import com.example.elancer.freelancerprofile.repository.profile.license.LicenseRepository;
import com.example.elancer.freelancerprofile.repository.profile.projecthistory.ProjectHistoryRepository;
import com.example.elancer.integrate.common.IntegrateBaseTest;
import com.example.elancer.common.LoginHelper;
import com.example.elancer.login.auth.dto.MemberDetails;
import com.example.elancer.member.domain.Address;
import com.example.elancer.member.domain.CountryType;
import com.example.elancer.member.domain.MemberType;
import com.example.elancer.member.dto.MemberLoginResponse;
import com.example.elancer.token.jwt.JwtTokenProvider;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class FreelancerProfileIntegrateTest extends IntegrateBaseTest {

    @Autowired
    private FreelancerRepository freelancerRepository;

    @Autowired
    private FreelancerProfileRepository freelancerProfileRepository;

    @Autowired
    private AcademicRepository academicRepository;

    @Autowired
    private CareerRepository careerRepository;

    @Autowired
    private ProjectHistoryRepository projectHistoryRepository;

    @Autowired
    private EducationRepository educationRepository;

    @Autowired
    private LicenseRepository licenseRepository;

    @Autowired
    private LanguageRepository languageRepository;

    @DisplayName("???????????? ????????? ???????????? ?????? ???????????????")
    @Test
    public void ????????????_?????????_????????????_??????() throws Exception {
        //given
        Freelancer freelancer = freelancerRepository.save(FreelancerHelper.????????????_??????(freelancerRepository, passwordEncoder));
        MemberLoginResponse memberLoginResponse = LoginHelper.?????????(freelancer.getUserId(), jwtTokenService);

        FreelancerProfile freelancerProfile = freelancerProfileRepository.save(new FreelancerProfile("greeting", freelancer, PositionType.DEVELOPER));

        IntroduceCoverRequest introduceCoverRequest = new IntroduceCoverRequest("greeting", "introName", IntroBackGround.COBALT_BLUE, "introVideoUrl", "introContent");

        //when
        ????????????_?????????_????????????_??????_??????(freelancerProfile, introduceCoverRequest, memberLoginResponse);

        //then
        ????????????_?????????_????????????_??????_????????????_??????(freelancerProfile, introduceCoverRequest);
    }

    @DisplayName("???????????? ????????? ???????????? ?????? ???????????????")
    @Test
    public void ????????????_?????????_????????????_??????() throws Exception {
        //given
        Freelancer freelancer = freelancerRepository.save(FreelancerHelper.????????????_??????(freelancerRepository, passwordEncoder));
        MemberLoginResponse memberLoginResponse = LoginHelper.?????????(freelancer.getUserId(), jwtTokenService);

        FreelancerProfile freelancerProfile = freelancerProfileRepository.save(new FreelancerProfile("greeting", freelancer, PositionType.DEVELOPER));

        AcademicAbilityCoverRequest academicAbilityCoverRequest = new AcademicAbilityCoverRequest(
                "schoolName",
                SchoolLevel.HIGH_SCHOOL,
                LocalDate.of(2015, 02, 01),
                LocalDate.of(2018, 02, 01),
                AcademicState.GRADUATION,
                "??????"
        );

        AcademicAbilityCoverRequests academicAbilityCoverRequests = new AcademicAbilityCoverRequests(Arrays.asList(academicAbilityCoverRequest));

        //when
        ????????????_?????????_????????????_??????_??????(freelancerProfile, academicAbilityCoverRequests, memberLoginResponse);

        //then
        ????????????_?????????_????????????_??????_????????????_??????(academicAbilityCoverRequest);
    }

    @DisplayName("???????????? ????????? ?????? ?????? ???????????????")
    @Test
    public void ????????????_?????????_????????????_??????() throws Exception {
        //given
        Freelancer freelancer = freelancerRepository.save(FreelancerHelper.????????????_??????(freelancerRepository, passwordEncoder));
        MemberLoginResponse memberLoginResponse = LoginHelper.?????????(freelancer.getUserId(), jwtTokenService);

        FreelancerProfile freelancerProfile = freelancerProfileRepository.save(new FreelancerProfile("greeting", freelancer, PositionType.DEVELOPER));

        CareerCoverRequest careerCoverRequest = new CareerCoverRequest(
                "companyName",
                "departmentName",
                CompanyPosition.HEAD_OF_DEPARTMENT,
                LocalDate.of(2020, 9, 01),
                LocalDate.of(2021, 10, 01)
        );

        CareerCoverRequests careerCoverRequests = new CareerCoverRequests(Arrays.asList(careerCoverRequest));

        //when
        ????????????_?????????_????????????_??????_??????(freelancerProfile, careerCoverRequests, memberLoginResponse);

        //then
        ????????????_?????????_????????????_??????_????????????_??????(careerCoverRequests.getCareerCoverRequests().get(0));
    }

    @DisplayName("???????????? ????????? ?????????????????? ?????? ???????????????")
    @Test
    public void ????????????_?????????_??????????????????_??????() throws Exception {
        //given
        Freelancer freelancer = freelancerRepository.save(FreelancerHelper.????????????_??????(freelancerRepository, passwordEncoder));
        MemberLoginResponse memberLoginResponse = LoginHelper.?????????(freelancer.getUserId(), jwtTokenService);

        FreelancerProfile freelancerProfile = freelancerProfileRepository.save(new FreelancerProfile("greeting", freelancer, PositionType.DEVELOPER));

        ProjectHistoryRequest projectHistoryRequest = new ProjectHistoryRequest(
                "projectTitle",
                LocalDate.of(2020, 12, 01),
                LocalDate.of(2021, 9, 01),
                "clientCompany",
                "workCompany",
                DevelopField.APPLICATION,
                "????????? ?????????",
                "model",
                "OS",
                "language",
                "mysql",
                "tool",
                "communication",
                null,
                "????????????"
        );

        ProjectHistoryCoverRequests projectHistoryCoverRequest = new ProjectHistoryCoverRequests(Arrays.asList(projectHistoryRequest));

        //when
        ????????????_?????????_??????????????????_??????_??????(freelancerProfile, projectHistoryCoverRequest, memberLoginResponse);

        //then
        ????????????_?????????_??????????????????_??????_????????????_??????(projectHistoryCoverRequest);
    }

    @DisplayName("???????????? ????????? ?????? ??? ???????????? ?????? ???????????????")
    @Test
    public void ????????????_?????????_?????????????????????_??????() throws Exception {
        //given
        Freelancer freelancer = freelancerRepository.save(FreelancerHelper.????????????_??????(freelancerRepository, passwordEncoder));
        MemberLoginResponse memberLoginResponse = LoginHelper.?????????(freelancer.getUserId(), jwtTokenService);

        FreelancerProfile freelancerProfile = freelancerProfileRepository.save(new FreelancerProfile("greeting", freelancer, PositionType.DEVELOPER));

        EducationCoverRequest educationCoverRequest = new EducationCoverRequest("?????????????????????", "??????????????????", LocalDate.of(2020, 01, 01), LocalDate.of(2021, 01, 01));
        LicenseCoverRequest licenseCoverRequest = new LicenseCoverRequest("??????????????????", "?????????????????????", LocalDate.of(2020, 05, 20));
        LanguageCoverRequest languageCoverRequest = new LanguageCoverRequest("??????", LanguageAbility.MIDDLE);

        EducationAndLicenseAndLanguageRequests educationAndLicenseAndLanguageRequests = new EducationAndLicenseAndLanguageRequests(
                Arrays.asList(educationCoverRequest),
                Arrays.asList(licenseCoverRequest),
                Arrays.asList(languageCoverRequest)
        );

        //when
        ????????????_?????????_?????????????????????_??????_??????(freelancerProfile, educationAndLicenseAndLanguageRequests, memberLoginResponse);

        //then
        ????????????_?????????_?????????????????????_??????_????????????_??????(educationCoverRequest, licenseCoverRequest, languageCoverRequest);
    }

    @DisplayName("???????????? ????????? ???????????? ?????? ???????????????")
    @Test
    public void ????????????_?????????_????????????_??????() throws Exception {
        //given
        Freelancer freelancer = freelancerRepository.save(FreelancerHelper.????????????_??????(freelancerRepository, passwordEncoder));
        MemberLoginResponse memberLoginResponse = LoginHelper.?????????(freelancer.getUserId(), jwtTokenService);

        FreelancerProfile freelancerProfile = new FreelancerProfile("greeting", freelancer, PositionType.DEVELOPER);

        AcademicAbility academicAbility = AcademicAbility.createAcademicAbility(
                "????????????",
                SchoolLevel.HIGH_SCHOOL,
                LocalDate.of(2012, 02, 01),
                LocalDate.of(2015, 02, 01),
                AcademicState.GRADUATION,
                "??????"
        );

        AcademicAbility academicAbility2 = AcademicAbility.createAcademicAbility(
                "?????????",
                SchoolLevel.UNIVERSITY,
                LocalDate.of(2015, 02, 01),
                LocalDate.of(2020, 02, 01),
                AcademicState.GRADUATION,
                "??????????????????"
        );

        Career career = Career.createCareer(
                "??????",
                "?????????",
                CompanyPosition.ASSISTANT_MANAGER,
                LocalDate.of(2020, 02, 01),
                LocalDate.of(2021, 02, 01)
        );

        Education education = Education.createEducation(
                "????????????",
                "????????????",
                LocalDate.of(2020, 02, 01),
                LocalDate.of(2021, 02, 01)
        );

        License license = License.createLicense("?????? ?????????", "?????? ??????", LocalDate.of(2019, 02, 22));

        Language language = Language.createLanguage("??????", LanguageAbility.MIDDLE);

        ProjectHistory projectHistory = ProjectHistory.createProjectHistory(
                "???????????????",
                LocalDate.of(2020, 02, 01),
                LocalDate.of(2021, 02, 01),
                "????????????",
                "????????????",
                DevelopField.APPLICATION,
                "backend",
                DevelopEnvironment.of(
                        "model",
                        "Ms",
                        "language",
                        "DB",
                        "Tool",
                        "??????",
                        "??????"
                ),
                "??????????????? ????????? ??????"
        );

        Developer developer = Developer.createBasicDeveloper(PositionType.DEVELOPER, freelancerProfile, "java", "role");

        String introduceName = "?????????";
        IntroBackGround introBackGround = IntroBackGround.COBALT_BLUE;
        String introduceVideoURL = "?????? ?????? ??????";
        String introduceContent = "?????? ??????";
        freelancerProfile.coverIntroduceInFreelancer(freelancerProfile.getGreeting(), introduceName, introBackGround, introduceVideoURL, introduceContent);

        freelancerProfile.coverAcademicAbilities(Arrays.asList(academicAbility, academicAbility2));
        freelancerProfile.coverCareers(Arrays.asList(career));
        freelancerProfile.coverEducation(Arrays.asList(education));
        freelancerProfile.coverLicense(Arrays.asList(license));
        freelancerProfile.coverLanguage(Arrays.asList(language));
        freelancerProfile.coverProjectHistory(Arrays.asList(projectHistory));
        freelancerProfile.coverPosition(developer);

        freelancerProfileRepository.save(freelancerProfile);

        //when & then
        mockMvc.perform(get(FreelancerProfileFindControllerPath.FREELANCER_PROFILE_FIND_DETAIL)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .header(JwtTokenProvider.AUTHORITIES_KEY, memberLoginResponse.getAccessToken()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("profileNum").value("1"))
                .andDo(print());

    }

    @DisplayName("???????????? ????????? ?????? ?????? ???????????????")
    @Test
    public void ????????????_?????????_??????_??????() throws Exception {
        //given
        Freelancer freelancer = freelancerRepository.save(FreelancerHelper.????????????_??????(freelancerRepository, passwordEncoder));
        MemberLoginResponse memberLoginResponse = LoginHelper.?????????(freelancer.getUserId(), jwtTokenService);

        FreelancerProfile freelancerProfile = new FreelancerProfile("greeting", freelancer, PositionType.DEVELOPER);

        AcademicAbility academicAbility = AcademicAbility.createAcademicAbility(
                "????????????",
                SchoolLevel.HIGH_SCHOOL,
                LocalDate.of(2012, 02, 01),
                LocalDate.of(2015, 02, 01),
                AcademicState.GRADUATION,
                "??????"
        );

        AcademicAbility academicAbility2 = AcademicAbility.createAcademicAbility(
                "?????????",
                SchoolLevel.UNIVERSITY,
                LocalDate.of(2015, 02, 01),
                LocalDate.of(2020, 02, 01),
                AcademicState.GRADUATION,
                "??????????????????"
        );

        Career career = Career.createCareer(
                "??????",
                "?????????",
                CompanyPosition.ASSISTANT_MANAGER,
                LocalDate.of(2020, 02, 01),
                LocalDate.of(2021, 02, 01)
        );

        Education education = Education.createEducation(
                "????????????",
                "????????????",
                LocalDate.of(2020, 02, 01),
                LocalDate.of(2021, 02, 01)
        );

        License license = License.createLicense("?????? ?????????", "?????? ??????", LocalDate.of(2019, 02, 22));

        Language language = Language.createLanguage("??????", LanguageAbility.MIDDLE);

        ProjectHistory projectHistory = ProjectHistory.createProjectHistory(
                "???????????????",
                LocalDate.of(2020, 02, 01),
                LocalDate.of(2021, 02, 01),
                "????????????",
                "????????????",
                DevelopField.APPLICATION,
                "backend",
                DevelopEnvironment.of(
                        "model",
                        "Ms",
                        "language",
                        "DB",
                        "Tool",
                        "??????",
                        "??????"
                ),
                "??????????????? ????????? ??????"
        );

        Developer developer = Developer.createBasicDeveloper(PositionType.DEVELOPER, freelancerProfile, "java", "role");

        String introduceName = "?????????";
        IntroBackGround introBackGround = IntroBackGround.COBALT_BLUE;
        String introduceVideoURL = "?????? ?????? ??????";
        String introduceContent = "?????? ??????";
        freelancerProfile.coverIntroduceInFreelancer(freelancerProfile.getGreeting(), introduceName, introBackGround, introduceVideoURL, introduceContent);

        freelancerProfile.coverAcademicAbilities(Arrays.asList(academicAbility, academicAbility2));
        freelancerProfile.coverCareers(Arrays.asList(career));
        freelancerProfile.coverEducation(Arrays.asList(education));
        freelancerProfile.coverLicense(Arrays.asList(license));
        freelancerProfile.coverLanguage(Arrays.asList(language));
        freelancerProfile.coverProjectHistory(Arrays.asList(projectHistory));
        freelancerProfile.coverPosition(developer);

        freelancerProfileRepository.save(freelancerProfile);

        //when & then
        String path = FreelancerProfileFindControllerPath.FREELANCER_PROFILE_FIND_SIMPLE.replace("{freelancerNum}", String.valueOf(freelancer.getNum()));
        mockMvc.perform(get(path)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .header(JwtTokenProvider.AUTHORITIES_KEY, memberLoginResponse.getAccessToken()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("name").value(freelancer.getName()))
                .andDo(print());

    }

    @DisplayName("???????????? ????????? ????????? ????????? ????????? ?????? ???????????????")
    @Test
    public void ????????????_?????????_?????????_?????????_??????() throws Exception {
        //given
        Freelancer freelancer = freelancerRepository.save(Freelancer.createFreelancer(
                "userId",
                "password",
                "name",
                "phone",
                "email",
                null,
                new Address(CountryType.KR,
                        "??????",
                        "??????",
                        "??????"
                ),
                MemberType.FREELANCER,
                MailReceptionState.RECEPTION,
                WorkPossibleState.POSSIBLE,
                LocalDate.of(2020, 02, 01)
        ));

        FreelancerProfile freelancerProfile = new FreelancerProfile("greeting", freelancer, PositionType.DEVELOPER);

        freelancerProfileRepository.save(freelancerProfile);

        Developer developer = Developer.createBasicDeveloper(PositionType.DEVELOPER, freelancerProfile, "java", "role");

        String introduceName = "?????????";
        IntroBackGround introBackGround = IntroBackGround.COBALT_BLUE;
        String introduceVideoURL = "?????? ?????? ??????";
        String introduceContent = "?????? ??????";
        freelancerProfile.coverIntroduceInFreelancer(freelancerProfile.getGreeting(), introduceName, introBackGround, introduceVideoURL, introduceContent);
        freelancerProfile.coverPosition(developer);
        freelancerProfileRepository.save(freelancerProfile);

        Freelancer freelancer2 = freelancerRepository.save(Freelancer.createFreelancer(
                "userId2",
                "password2",
                "name2",
                "phone2",
                "email2",
                null,
                new Address(CountryType.KR,
                        "??????",
                        "??????",
                        "??????2"
                ),
                MemberType.FREELANCER,
                MailReceptionState.RECEPTION,
                WorkPossibleState.POSSIBLE,
                LocalDate.of(2020, 02, 01)
        ));

        FreelancerProfile freelancerProfile2 = new FreelancerProfile("greeting", freelancer2, PositionType.PLANNER);
        freelancerProfileRepository.save(freelancerProfile2);

        Planner planner = Planner.createBasicPlanner(PositionType.PLANNER, freelancerProfile2);

        String introduceName2 = "?????????";
        IntroBackGround introBackGround2 = IntroBackGround.COBALT_BLUE;
        String introduceVideoURL2 = "?????? ?????? ??????";
        String introduceContent2 = "?????? ??????";
        freelancerProfile2.coverIntroduceInFreelancer(freelancerProfile2.getGreeting(), introduceName2, introBackGround2, introduceVideoURL2, introduceContent2);
        freelancerProfile2.coverPosition(planner);
        freelancerProfileRepository.save(freelancerProfile2);

        //when & then
        mockMvc.perform(get(FreelancerProfileFindControllerPath.FREELANCER_FINDS)
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andExpect(jsonPath("freelancerSimpleResponseList", hasSize(2)))
                .andDo(print());

    }

    @DisplayName("???????????? ????????? ????????? ??????????????? ?????? ???????????????")
    @Test
    public void ????????????_?????????_?????????_???????????????_??????() throws Exception {
        //when & then
        mockMvc.perform(get(FreelancerPositionEnumControllerPath.FREELANCER_POSITION_DEVELOPER_SKILLS_FIND)
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andExpect(jsonPath("javaDetailSkillNames.[0]").value("Front-End"))
                .andExpect(jsonPath("mobileDetailSkillNames.[0]").value("Hybrid"))
                .andExpect(jsonPath("phpOrAspDetailSkillNames.[0]").value("PHP"))
                .andExpect(jsonPath("dotNetDetailSkillNames.[0]").value("ASP.net"))
                .andExpect(jsonPath("javaScriptDetailSkillNames.[0]").value("node.js"))
                .andExpect(jsonPath("cdetailSkillNames.[0]").value("Server"))
                .andExpect(jsonPath("dbDetailSkillNames.[0]").value("Oracle"))
                .andDo(print());

    }

    @DisplayName("???????????? ????????? ??????????????? ?????? ???????????????")
    @Test
    public void ????????????_?????????_???????????????_??????() throws Exception {
        //when & then
        mockMvc.perform(get(FreelancerPositionEnumControllerPath.FREELANCER_PROFILE_ENUMS_FIND)
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andExpect(jsonPath("schoolLevelNames.[0]").value("????????????"))
                .andExpect(jsonPath("academicStateNames.[0]").value("??????"))
                .andExpect(jsonPath("companyPositionNames.[0]").value("??????"))
                .andExpect(jsonPath("languageAbilityNames.[0]").value("???????????? ??????"))
                .andDo(print());

    }

    private void ????????????_?????????_????????????_??????_??????(FreelancerProfile freelancerProfile, IntroduceCoverRequest introduceCoverRequest, MemberLoginResponse memberLoginResponse) throws Exception {
        mockMvc.perform(put(FreelancerProfileAlterControllerPath.FREELANCER_PROFILE_INTRO_COVER)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .header(JwtTokenProvider.AUTHORITIES_KEY, memberLoginResponse.getAccessToken())
                        .content(objectMapper.writeValueAsString(introduceCoverRequest)))
                .andExpect(status().isOk())
                .andDo(print());
    }

    private void ????????????_?????????_????????????_??????_????????????_??????(FreelancerProfile freelancerProfile, IntroduceCoverRequest introduceCoverRequest) {
        FreelancerProfile updatedFreelancerProfile = freelancerProfileRepository.findById(freelancerProfile.getNum()).get();
        Assertions.assertThat(updatedFreelancerProfile.getIntroduceName()).isEqualTo(introduceCoverRequest.getIntroName());
        Assertions.assertThat(updatedFreelancerProfile.getIntroBackGround()).isEqualTo(introduceCoverRequest.getIntroBackGround());
        Assertions.assertThat(updatedFreelancerProfile.getIntroduceContent()).isEqualTo(introduceCoverRequest.getIntroContent());
        Assertions.assertThat(updatedFreelancerProfile.getIntroduceVideoURL()).isEqualTo(introduceCoverRequest.getIntroVideoUrl());
    }

    private void ????????????_?????????_????????????_??????_??????(FreelancerProfile freelancerProfile, AcademicAbilityCoverRequests academicAbilityCoverRequests, MemberLoginResponse memberLoginResponse) throws Exception {
        mockMvc.perform(put(FreelancerProfileAlterControllerPath.FREELANCER_PROFILE_ACADEMIC_COVER)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .header(JwtTokenProvider.AUTHORITIES_KEY, memberLoginResponse.getAccessToken())
                        .content(objectMapper.writeValueAsString(academicAbilityCoverRequests)))
                .andExpect(status().isOk())
                .andDo(print());
    }

    private void ????????????_?????????_????????????_??????_????????????_??????(AcademicAbilityCoverRequest academicAbilityCoverRequest) {
        AcademicAbility academicAbility = academicRepository.findAll().get(0);
        Assertions.assertThat(academicAbility.getSchoolName()).isEqualTo(academicAbilityCoverRequest.getSchoolName());
        Assertions.assertThat(academicAbility.getSchoolLevel()).isEqualTo(academicAbilityCoverRequest.getSchoolLevel());
        Assertions.assertThat(academicAbility.getEnterSchoolDate()).isEqualTo(academicAbilityCoverRequest.getEnterSchoolDate());
        Assertions.assertThat(academicAbility.getGraduationDate()).isEqualTo(academicAbilityCoverRequest.getGraduationDate());
        Assertions.assertThat(academicAbility.getAcademicState()).isEqualTo(academicAbilityCoverRequest.getAcademicState());
        Assertions.assertThat(academicAbility.getMajorName()).isEqualTo(academicAbilityCoverRequest.getMajorName());
    }

    private void ????????????_?????????_????????????_??????_??????(FreelancerProfile freelancerProfile, CareerCoverRequests careerCoverRequests, MemberLoginResponse memberLoginResponse) throws Exception {
        mockMvc.perform(put(FreelancerProfileAlterControllerPath.FREELANCER_PROFILE_CAREER_COVER)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .header(JwtTokenProvider.AUTHORITIES_KEY, memberLoginResponse.getAccessToken())
                        .content(objectMapper.writeValueAsString(careerCoverRequests)))
                .andExpect(status().isOk())
                .andDo(print());
    }

    private void ????????????_?????????_????????????_??????_????????????_??????(CareerCoverRequest careerCoverRequest) {
        Career career = careerRepository.findAll().get(0);
        Assertions.assertThat(career.getCompanyName()).isEqualTo(careerCoverRequest.getCompanyName());
        Assertions.assertThat(career.getDepartmentName()).isEqualTo(careerCoverRequest.getDepartmentName());
        Assertions.assertThat(career.getCompanyPosition()).isEqualTo(careerCoverRequest.getCompanyPosition());
        Assertions.assertThat(career.getCareerStartDate()).isEqualTo(careerCoverRequest.getCareerStartDate());
        Assertions.assertThat(career.getCareerEndDate()).isEqualTo(careerCoverRequest.getCareerEndDate());
    }

    private void ????????????_?????????_??????????????????_??????_??????(FreelancerProfile freelancerProfile, ProjectHistoryCoverRequests projectHistoryCoverRequests, MemberLoginResponse memberLoginResponse) throws Exception {
        mockMvc.perform(put(FreelancerProfileAlterControllerPath.FREELANCER_PROFILE_PROJECT_HISTORY_COVER)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .header(JwtTokenProvider.AUTHORITIES_KEY, memberLoginResponse.getAccessToken())
                        .content(objectMapper.writeValueAsString(projectHistoryCoverRequests)))
                .andExpect(status().isOk())
                .andDo(print());
    }

    private void ????????????_?????????_??????????????????_??????_????????????_??????(ProjectHistoryCoverRequests projectHistoryCoverRequests) {
        List<ProjectHistory> projectHistories = projectHistoryRepository.findAll();
        Assertions.assertThat(projectHistories).hasSize(1);
        Assertions.assertThat(projectHistories.get(0).getProjectTitle()).isEqualTo(projectHistoryCoverRequests.getProjectHistoryRequestList().get(0).getProjectTitle());
        Assertions.assertThat(projectHistories.get(0).getProjectStartDate()).isEqualTo(projectHistoryCoverRequests.getProjectHistoryRequestList().get(0).getProjectStartDate());
        Assertions.assertThat(projectHistories.get(0).getProjectEndDate()).isEqualTo(projectHistoryCoverRequests.getProjectHistoryRequestList().get(0).getProjectEndDate());
        Assertions.assertThat(projectHistories.get(0).getClientCompany()).isEqualTo(projectHistoryCoverRequests.getProjectHistoryRequestList().get(0).getClientCompany());
        Assertions.assertThat(projectHistories.get(0).getWorkCompany()).isEqualTo(projectHistoryCoverRequests.getProjectHistoryRequestList().get(0).getWorkCompany());
        Assertions.assertThat(projectHistories.get(0).getDevelopField()).isEqualTo(projectHistoryCoverRequests.getProjectHistoryRequestList().get(0).getDevelopField());
        Assertions.assertThat(projectHistories.get(0).getDevelopRole()).isEqualTo(projectHistoryCoverRequests.getProjectHistoryRequestList().get(0).getDevelopRole());
        Assertions.assertThat(projectHistories.get(0).getDevelopEnvironment().getDevelopEnvironmentModel()).isEqualTo(projectHistoryCoverRequests.getProjectHistoryRequestList().get(0).getDevelopEnvironmentModel());
        Assertions.assertThat(projectHistories.get(0).getDevelopEnvironment().getDevelopEnvironmentOS()).isEqualTo(projectHistoryCoverRequests.getProjectHistoryRequestList().get(0).getDevelopEnvironmentOS());
        Assertions.assertThat(projectHistories.get(0).getDevelopEnvironment().getDevelopEnvironmentLanguage()).isEqualTo(projectHistoryCoverRequests.getProjectHistoryRequestList().get(0).getDevelopEnvironmentLanguage());
        Assertions.assertThat(projectHistories.get(0).getDevelopEnvironment().getDevelopEnvironmentDBName()).isEqualTo(projectHistoryCoverRequests.getProjectHistoryRequestList().get(0).getDevelopEnvironmentDBName());
        Assertions.assertThat(projectHistories.get(0).getDevelopEnvironment().getDevelopEnvironmentTool()).isEqualTo(projectHistoryCoverRequests.getProjectHistoryRequestList().get(0).getDevelopEnvironmentTool());
        Assertions.assertThat(projectHistories.get(0).getDevelopEnvironment().getDevelopEnvironmentCommunication()).isEqualTo(projectHistoryCoverRequests.getProjectHistoryRequestList().get(0).getDevelopEnvironmentCommunication());
        Assertions.assertThat(projectHistories.get(0).getDevelopEnvironment().getDevelopEnvironmentEtc()).isEqualTo(projectHistoryCoverRequests.getProjectHistoryRequestList().get(0).getDevelopEnvironmentEtc());
        Assertions.assertThat(projectHistories.get(0).getResponsibilityTask()).isEqualTo(projectHistoryCoverRequests.getProjectHistoryRequestList().get(0).getResponsibilityTask());
    }

    private void ????????????_?????????_?????????????????????_??????_??????(FreelancerProfile freelancerProfile, EducationAndLicenseAndLanguageRequests educationAndLicenseAndLanguageRequests, MemberLoginResponse memberLoginResponse) throws Exception {
        mockMvc.perform(put(FreelancerProfileAlterControllerPath.FREELANCER_PROFILE_EDU_AND_LICENSE_AND_LANG_COVER)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .header(JwtTokenProvider.AUTHORITIES_KEY, memberLoginResponse.getAccessToken())
                        .content(objectMapper.writeValueAsString(educationAndLicenseAndLanguageRequests)))
                .andExpect(status().isOk())
                .andDo(print());
    }

    private void ????????????_?????????_?????????????????????_??????_????????????_??????(EducationCoverRequest educationCoverRequest, LicenseCoverRequest licenseCoverRequest, LanguageCoverRequest languageCoverRequest) {
        List<Education> educations = educationRepository.findAll();
        Assertions.assertThat(educations).hasSize(1);
        Assertions.assertThat(educations.get(0).getEducationTitle()).isEqualTo(educationCoverRequest.getEducationTitle());
        Assertions.assertThat(educations.get(0).getEducationOrganization()).isEqualTo(educationCoverRequest.getEducationOrganization());
        Assertions.assertThat(educations.get(0).getEducationStartDate()).isEqualTo(educationCoverRequest.getEducationStartDate());
        Assertions.assertThat(educations.get(0).getEducationEndDate()).isEqualTo(educationCoverRequest.getEducationEndDate());

        List<License> licenses = licenseRepository.findAll();
        Assertions.assertThat(licenses).hasSize(1);
        Assertions.assertThat(licenses.get(0).getLicenseTitle()).isEqualTo(licenseCoverRequest.getLicenseTitle());
        Assertions.assertThat(licenses.get(0).getLicenseIssuer()).isEqualTo(licenseCoverRequest.getLicenseIssuer());
        Assertions.assertThat(licenses.get(0).getAcquisitionDate()).isEqualTo(licenseCoverRequest.getAcquisitionDate());

        List<Language> languages = languageRepository.findAll();
        Assertions.assertThat(languages).hasSize(1);
        Assertions.assertThat(languages.get(0).getLanguageName()).isEqualTo(languageCoverRequest.getLanguageName());
        Assertions.assertThat(languages.get(0).getLanguageAbility()).isEqualTo(languageCoverRequest.getLanguageAbility());
    }

    @AfterEach
    void tearDown() {
        databaseCleaner.clean();
    }
}
