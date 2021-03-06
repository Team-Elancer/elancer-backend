package com.example.elancer.freelancerprofile.service;

import com.example.elancer.freelancer.model.Freelancer;
import com.example.elancer.freelancer.model.IntroBackGround;
import com.example.elancer.freelancer.model.MailReceptionState;
import com.example.elancer.freelancer.model.WorkPossibleState;
import com.example.elancer.freelancer.repository.FreelancerRepository;
import com.example.elancer.freelancerprofile.dto.FreelancerSimpleResponses;
import com.example.elancer.freelancerprofile.dto.response.FreelancerDetailResponse;
import com.example.elancer.freelancerprofile.dto.response.FreelancerProfileSimpleResponse;
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
import com.example.elancer.freelancerprofile.repository.FreelancerProfileFindRepository;
import com.example.elancer.freelancerprofile.repository.FreelancerProfileRepository;
import com.example.elancer.freelancerprofile.service.profile.FreelancerProfileFindService;
import com.example.elancer.login.auth.dto.MemberDetails;
import com.example.elancer.member.domain.Address;
import com.example.elancer.member.domain.CountryType;
import com.example.elancer.member.domain.MemberType;
import com.example.elancer.wishfreelancer.repository.WishFreelancerRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;


@ActiveProfiles("h2")
@ExtendWith(value = MockitoExtension.class)
class FreelancerProfileFindServiceTest {

    private FreelancerProfileFindService freelancerProfileFindService;

    @Mock
    private FreelancerProfileFindRepository freelancerProfileFindRepository;

    @Mock
    private FreelancerProfileRepository freelancerProfileRepository;

    @Mock
    private WishFreelancerRepository wishFreelancerRepository;

    @Mock
    private FreelancerRepository freelancerRepository;


    @BeforeEach
    void setUp() {
        this.freelancerProfileFindService = new FreelancerProfileFindService(freelancerProfileFindRepository, freelancerProfileRepository, freelancerRepository, wishFreelancerRepository);
    }

    @DisplayName("???????????? ????????? ????????? ???????????? ??????.")
    @Test
    public void ????????????_?????????_??????() {
        //given
        Freelancer freelancer = Freelancer.createFreelancer("userId", "password", "name", null, null, null, null, MemberType.FREELANCER,
                MailReceptionState.RECEPTION, WorkPossibleState.POSSIBLE, null);
        FreelancerProfile freelancerProfile = new FreelancerProfile("greeting", freelancer, PositionType.DEVELOPER);

        MemberDetails memberDetails = MemberDetails.userDetailsFrom(freelancer);

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
        freelancerProfile.coverIntroduceInFreelancer(freelancerProfile.getGreeting(),introduceName, introBackGround, introduceVideoURL, introduceContent);

        freelancerProfile.coverAcademicAbilities(Arrays.asList(academicAbility, academicAbility2));
        freelancerProfile.coverCareers(Arrays.asList(career));
        freelancerProfile.coverEducation(Arrays.asList(education));
        freelancerProfile.coverLicense(Arrays.asList(license));
        freelancerProfile.coverLanguage(Arrays.asList(language));
        freelancerProfile.coverProjectHistory(Arrays.asList(projectHistory));
        freelancerProfile.coverPosition(developer);

        when(freelancerProfileFindRepository.findFreelancerProfileByFetch(any())).thenReturn(Optional.of(freelancerProfile));

        //when
        FreelancerDetailResponse detailFreelancerProfile = freelancerProfileFindService.findDetailFreelancerProfile(memberDetails);

        //then
        Assertions.assertThat(detailFreelancerProfile.getProfileNum()).isEqualTo(freelancerProfile.getNum());
        Assertions.assertThat(detailFreelancerProfile.getGreeting()).isEqualTo(freelancerProfile.getGreeting());
        Assertions.assertThat(detailFreelancerProfile.getPositionType()).isEqualTo(freelancerProfile.getPosition().getPositionType());

        Assertions.assertThat(detailFreelancerProfile.getAcademicAbilityResponses().get(0).getSchoolName())
                .isEqualTo(academicAbility.getSchoolName());
        Assertions.assertThat(detailFreelancerProfile.getAcademicAbilityResponses().get(0).getSchoolLevel())
                .isEqualTo(academicAbility.getSchoolLevel());
        Assertions.assertThat(detailFreelancerProfile.getAcademicAbilityResponses().get(0).getEnterSchoolDate())
                .isEqualTo(academicAbility.getEnterSchoolDate());
        Assertions.assertThat(detailFreelancerProfile.getAcademicAbilityResponses().get(0).getGraduationDate())
                .isEqualTo(academicAbility.getGraduationDate());
        Assertions.assertThat(detailFreelancerProfile.getAcademicAbilityResponses().get(0).getAcademicState())
                .isEqualTo(academicAbility.getAcademicState());
        Assertions.assertThat(detailFreelancerProfile.getAcademicAbilityResponses().get(0).getMajorName())
                .isEqualTo(academicAbility.getMajorName());

        Assertions.assertThat(detailFreelancerProfile.getAcademicAbilityResponses().get(1).getSchoolName())
                .isEqualTo(academicAbility2.getSchoolName());
        Assertions.assertThat(detailFreelancerProfile.getAcademicAbilityResponses().get(1).getSchoolLevel())
                .isEqualTo(academicAbility2.getSchoolLevel());
        Assertions.assertThat(detailFreelancerProfile.getAcademicAbilityResponses().get(1).getEnterSchoolDate())
                .isEqualTo(academicAbility2.getEnterSchoolDate());
        Assertions.assertThat(detailFreelancerProfile.getAcademicAbilityResponses().get(1).getGraduationDate())
                .isEqualTo(academicAbility2.getGraduationDate());
        Assertions.assertThat(detailFreelancerProfile.getAcademicAbilityResponses().get(1).getAcademicState())
                .isEqualTo(academicAbility2.getAcademicState());
        Assertions.assertThat(detailFreelancerProfile.getAcademicAbilityResponses().get(1).getMajorName())
                .isEqualTo(academicAbility2.getMajorName());

        Assertions.assertThat(detailFreelancerProfile.getCareerResponses().get(0).getCompanyName()).isEqualTo(career.getCompanyName());
        Assertions.assertThat(detailFreelancerProfile.getCareerResponses().get(0).getDepartmentName()).isEqualTo(career.getDepartmentName());
        Assertions.assertThat(detailFreelancerProfile.getCareerResponses().get(0).getCompanyPosition()).isEqualTo(career.getCompanyPosition());
        Assertions.assertThat(detailFreelancerProfile.getCareerResponses().get(0).getCareerStartDate()).isEqualTo(career.getCareerStartDate());
        Assertions.assertThat(detailFreelancerProfile.getCareerResponses().get(0).getCareerEndDate()).isEqualTo(career.getCareerEndDate());

        Assertions.assertThat(detailFreelancerProfile.getEducationResponses().get(0).getEducationTitle()).isEqualTo(education.getEducationTitle());
        Assertions.assertThat(detailFreelancerProfile.getEducationResponses().get(0).getEducationOrganization()).isEqualTo(education.getEducationOrganization());
        Assertions.assertThat(detailFreelancerProfile.getEducationResponses().get(0).getEducationStartDate()).isEqualTo(education.getEducationStartDate());
        Assertions.assertThat(detailFreelancerProfile.getEducationResponses().get(0).getEducationEndDate()).isEqualTo(education.getEducationEndDate());

        Assertions.assertThat(detailFreelancerProfile.getLicenseResponses().get(0).getLicenseTitle()).isEqualTo(license.getLicenseTitle());
        Assertions.assertThat(detailFreelancerProfile.getLicenseResponses().get(0).getLicenseIssuer()).isEqualTo(license.getLicenseIssuer());
        Assertions.assertThat(detailFreelancerProfile.getLicenseResponses().get(0).getAcquisitionDate()).isEqualTo(license.getAcquisitionDate());

        Assertions.assertThat(detailFreelancerProfile.getLanguageResponses().get(0).getLanguageName()).isEqualTo(language.getLanguageName());
        Assertions.assertThat(detailFreelancerProfile.getLanguageResponses().get(0).getLanguageAbility()).isEqualTo(language.getLanguageAbility());

        Assertions.assertThat(detailFreelancerProfile.getProjectHistoryResponses().get(0).getProjectTitle()).isEqualTo(projectHistory.getProjectTitle());
        Assertions.assertThat(detailFreelancerProfile.getProjectHistoryResponses().get(0).getProjectStartDate()).isEqualTo(projectHistory.getProjectStartDate());
        Assertions.assertThat(detailFreelancerProfile.getProjectHistoryResponses().get(0).getProjectEndDate()).isEqualTo(projectHistory.getProjectEndDate());
        Assertions.assertThat(detailFreelancerProfile.getProjectHistoryResponses().get(0).getClientCompany()).isEqualTo(projectHistory.getClientCompany());
        Assertions.assertThat(detailFreelancerProfile.getProjectHistoryResponses().get(0).getWorkCompany()).isEqualTo(projectHistory.getWorkCompany());
        Assertions.assertThat(detailFreelancerProfile.getProjectHistoryResponses().get(0).getDevelopField()).isEqualTo(projectHistory.getDevelopField());
        Assertions.assertThat(detailFreelancerProfile.getProjectHistoryResponses().get(0).getDevelopRole()).isEqualTo(projectHistory.getDevelopRole());
        Assertions.assertThat(detailFreelancerProfile.getProjectHistoryResponses().get(0).getDevelopEnvironment().getDevelopEnvironmentModel())
                .isEqualTo(projectHistory.getDevelopEnvironment().getDevelopEnvironmentModel());
        Assertions.assertThat(detailFreelancerProfile.getProjectHistoryResponses().get(0).getDevelopEnvironment().getDevelopEnvironmentOS())
                .isEqualTo(projectHistory.getDevelopEnvironment().getDevelopEnvironmentOS());
        Assertions.assertThat(detailFreelancerProfile.getProjectHistoryResponses().get(0).getDevelopEnvironment().getDevelopEnvironmentLanguage())
                .isEqualTo(projectHistory.getDevelopEnvironment().getDevelopEnvironmentLanguage());
        Assertions.assertThat(detailFreelancerProfile.getProjectHistoryResponses().get(0).getDevelopEnvironment().getDevelopEnvironmentDBName())
                .isEqualTo(projectHistory.getDevelopEnvironment().getDevelopEnvironmentDBName());
        Assertions.assertThat(detailFreelancerProfile.getProjectHistoryResponses().get(0).getDevelopEnvironment().getDevelopEnvironmentTool())
                .isEqualTo(projectHistory.getDevelopEnvironment().getDevelopEnvironmentTool());
        Assertions.assertThat(detailFreelancerProfile.getProjectHistoryResponses().get(0).getDevelopEnvironment().getDevelopEnvironmentCommunication())
                .isEqualTo(projectHistory.getDevelopEnvironment().getDevelopEnvironmentCommunication());
        Assertions.assertThat(detailFreelancerProfile.getProjectHistoryResponses().get(0).getDevelopEnvironment().getDevelopEnvironmentEtc())
                .isEqualTo(projectHistory.getDevelopEnvironment().getDevelopEnvironmentEtc());
        Assertions.assertThat(detailFreelancerProfile.getProjectHistoryResponses().get(0).getResponsibilityTask()).isEqualTo(projectHistory.getResponsibilityTask());
    }

    @DisplayName("???????????? ????????? ?????? ????????? ???????????? ??????.")
    @Test
    public void ????????????_?????????_??????_??????() {
        //given
        Freelancer freelancer = Freelancer.createFreelancer(
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
        );

        FreelancerProfile freelancerProfile = new FreelancerProfile("greeting", freelancer, PositionType.DEVELOPER);

        MemberDetails memberDetails = MemberDetails.userDetailsFrom(freelancer);

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


        when(freelancerProfileRepository.findByFreelancerNum(any())).thenReturn(Optional.of(freelancerProfile));

        //when
        FreelancerProfileSimpleResponse freelancerProfileSimpleResponse = freelancerProfileFindService.findSimpleMyAccountByFreelancer(memberDetails);

        //then
        Assertions.assertThat(freelancerProfileSimpleResponse.getName()).isEqualTo(freelancer.getName());
        Assertions.assertThat(freelancerProfileSimpleResponse.getExpertise()).isEqualTo(0);
        Assertions.assertThat(freelancerProfileSimpleResponse.getScheduleAdherence()).isEqualTo(0);
        Assertions.assertThat(freelancerProfileSimpleResponse.getInitiative()).isEqualTo(0);
        Assertions.assertThat(freelancerProfileSimpleResponse.getCommunication()).isEqualTo(0);
        Assertions.assertThat(freelancerProfileSimpleResponse.getReemploymentIntention()).isEqualTo(0);
        Assertions.assertThat(freelancerProfileSimpleResponse.getTotalActiveScore()).isEqualTo(0);
        Assertions.assertThat(freelancerProfileSimpleResponse.getIntroduceName()).isEqualTo(freelancerProfile.getIntroduceName());
        Assertions.assertThat(freelancerProfileSimpleResponse.getIntroBackGround()).isEqualTo(freelancerProfile.getIntroBackGround());
        Assertions.assertThat(freelancerProfileSimpleResponse.getGreeting()).isEqualTo(freelancerProfile.getGreeting());
        Assertions.assertThat(freelancerProfileSimpleResponse.getCareerYear()).isEqualTo(freelancer.getFreelancerAccountInfo().getCareerYear());
        Assertions.assertThat(freelancerProfileSimpleResponse.getPositionType()).isEqualTo(developer.getPositionType());
        Assertions.assertThat(freelancerProfileSimpleResponse.getPositionTypeDescription()).isEqualTo(developer.getPositionType().getDesc());
        Assertions.assertThat(freelancerProfileSimpleResponse.getIntroduceVideoUrl()).isEqualTo(freelancerProfile.getIntroduceVideoURL());
        Assertions.assertThat(freelancerProfileSimpleResponse.getIntroduceContent()).isEqualTo(freelancerProfile.getIntroduceContent());
        Assertions.assertThat(freelancerProfileSimpleResponse.getProjectHistoryResponses()).hasSize(1);

        //???????????? ???????????? ??????
        Assertions.assertThat(freelancerProfileSimpleResponse.getProjectHistoryResponses().get(0).getProjectTitle()).isEqualTo(projectHistory.getProjectTitle());
        Assertions.assertThat(freelancerProfileSimpleResponse.getProjectHistoryResponses().get(0).getProjectStartDate()).isEqualTo(projectHistory.getProjectStartDate());
        Assertions.assertThat(freelancerProfileSimpleResponse.getProjectHistoryResponses().get(0).getProjectEndDate()).isEqualTo(projectHistory.getProjectEndDate());
        Assertions.assertThat(freelancerProfileSimpleResponse.getProjectHistoryResponses().get(0).getClientCompany()).isEqualTo(projectHistory.getClientCompany());
        Assertions.assertThat(freelancerProfileSimpleResponse.getProjectHistoryResponses().get(0).getWorkCompany()).isEqualTo(projectHistory.getWorkCompany());
        Assertions.assertThat(freelancerProfileSimpleResponse.getProjectHistoryResponses().get(0).getDevelopField()).isEqualTo(projectHistory.getDevelopField());
        Assertions.assertThat(freelancerProfileSimpleResponse.getProjectHistoryResponses().get(0).getDevelopRole()).isEqualTo(projectHistory.getDevelopRole());
        Assertions.assertThat(freelancerProfileSimpleResponse.getProjectHistoryResponses().get(0).getDevelopEnvironment().getDevelopEnvironmentModel())
                .isEqualTo(projectHistory.getDevelopEnvironment().getDevelopEnvironmentModel());
        Assertions.assertThat(freelancerProfileSimpleResponse.getProjectHistoryResponses().get(0).getDevelopEnvironment().getDevelopEnvironmentOS())
                .isEqualTo(projectHistory.getDevelopEnvironment().getDevelopEnvironmentOS());
        Assertions.assertThat(freelancerProfileSimpleResponse.getProjectHistoryResponses().get(0).getDevelopEnvironment().getDevelopEnvironmentLanguage())
                .isEqualTo(projectHistory.getDevelopEnvironment().getDevelopEnvironmentLanguage());
        Assertions.assertThat(freelancerProfileSimpleResponse.getProjectHistoryResponses().get(0).getDevelopEnvironment().getDevelopEnvironmentDBName())
                .isEqualTo(projectHistory.getDevelopEnvironment().getDevelopEnvironmentDBName());
        Assertions.assertThat(freelancerProfileSimpleResponse.getProjectHistoryResponses().get(0).getDevelopEnvironment().getDevelopEnvironmentTool())
                .isEqualTo(projectHistory.getDevelopEnvironment().getDevelopEnvironmentTool());
        Assertions.assertThat(freelancerProfileSimpleResponse.getProjectHistoryResponses().get(0).getDevelopEnvironment().getDevelopEnvironmentCommunication())
                .isEqualTo(projectHistory.getDevelopEnvironment().getDevelopEnvironmentCommunication());
        Assertions.assertThat(freelancerProfileSimpleResponse.getProjectHistoryResponses().get(0).getDevelopEnvironment().getDevelopEnvironmentEtc())
                .isEqualTo(projectHistory.getDevelopEnvironment().getDevelopEnvironmentEtc());
        Assertions.assertThat(freelancerProfileSimpleResponse.getProjectHistoryResponses().get(0).getResponsibilityTask()).isEqualTo(projectHistory.getResponsibilityTask());

        //???????????? ??????
        Assertions.assertThat(freelancerProfileSimpleResponse.getAcademicAbilityResponses()).hasSize(2);
        Assertions.assertThat(freelancerProfileSimpleResponse.getAcademicAbilityResponses().get(0).getSchoolName()).isEqualTo(academicAbility.getSchoolName());
        Assertions.assertThat(freelancerProfileSimpleResponse.getAcademicAbilityResponses().get(0).getSchoolLevel()).isEqualTo(academicAbility.getSchoolLevel());
        Assertions.assertThat(freelancerProfileSimpleResponse.getAcademicAbilityResponses().get(0).getSchoolLevelDescription()).isEqualTo(academicAbility.getSchoolLevel().getDesc());
        Assertions.assertThat(freelancerProfileSimpleResponse.getAcademicAbilityResponses().get(0).getEnterSchoolDate()).isEqualTo(academicAbility.getEnterSchoolDate());
        Assertions.assertThat(freelancerProfileSimpleResponse.getAcademicAbilityResponses().get(0).getGraduationDate()).isEqualTo(academicAbility.getGraduationDate());
        Assertions.assertThat(freelancerProfileSimpleResponse.getAcademicAbilityResponses().get(0).getAcademicState()).isEqualTo(academicAbility.getAcademicState());
        Assertions.assertThat(freelancerProfileSimpleResponse.getAcademicAbilityResponses().get(0).getMajorName()).isEqualTo(academicAbility.getMajorName());

        //???????????? ??????
        Assertions.assertThat(freelancerProfileSimpleResponse.getCareerResponses().get(0).getCompanyName()).isEqualTo(career.getCompanyName());
        Assertions.assertThat(freelancerProfileSimpleResponse.getCareerResponses().get(0).getDepartmentName()).isEqualTo(career.getDepartmentName());
        Assertions.assertThat(freelancerProfileSimpleResponse.getCareerResponses().get(0).getCompanyPosition()).isEqualTo(career.getCompanyPosition());
        Assertions.assertThat(freelancerProfileSimpleResponse.getCareerResponses().get(0).getCompanyPositionDescription()).isEqualTo(career.getCompanyPosition().getDesc());
        Assertions.assertThat(freelancerProfileSimpleResponse.getCareerResponses().get(0).getCareerStartDate()).isEqualTo(career.getCareerStartDate());
        Assertions.assertThat(freelancerProfileSimpleResponse.getCareerResponses().get(0).getCareerEndDate()).isEqualTo(career.getCareerEndDate());

        //???????????? ??????
        Assertions.assertThat(freelancerProfileSimpleResponse.getEducationResponses().get(0).getEducationTitle()).isEqualTo(education.getEducationTitle());
        Assertions.assertThat(freelancerProfileSimpleResponse.getEducationResponses().get(0).getEducationOrganization()).isEqualTo(education.getEducationOrganization());
        Assertions.assertThat(freelancerProfileSimpleResponse.getEducationResponses().get(0).getEducationStartDate()).isEqualTo(education.getEducationStartDate());
        Assertions.assertThat(freelancerProfileSimpleResponse.getEducationResponses().get(0).getEducationEndDate()).isEqualTo(education.getEducationEndDate());

        //??????????????? ??????
        Assertions.assertThat(freelancerProfileSimpleResponse.getLicenseResponses().get(0).getLicenseTitle()).isEqualTo(license.getLicenseTitle());
        Assertions.assertThat(freelancerProfileSimpleResponse.getLicenseResponses().get(0).getLicenseIssuer()).isEqualTo(license.getLicenseIssuer());
        Assertions.assertThat(freelancerProfileSimpleResponse.getLicenseResponses().get(0).getAcquisitionDate()).isEqualTo(license.getAcquisitionDate());

        //???????????? ??????
        Assertions.assertThat(freelancerProfileSimpleResponse.getLanguageResponses().get(0).getLanguageName()).isEqualTo(language.getLanguageName());
        Assertions.assertThat(freelancerProfileSimpleResponse.getLanguageResponses().get(0).getLanguageAbility()).isEqualTo(language.getLanguageAbility());
        Assertions.assertThat(freelancerProfileSimpleResponse.getLanguageResponses().get(0).getLanguageAbilityDescription()).isEqualTo(language.getLanguageAbility().getDesc());
    }

    @DisplayName("???????????? ????????? ?????? ?????? ???????????? ???????????? ??????.")
    @Test
    public void ????????????_?????????_??????_??????_?????????_??????() {
        //given
        Freelancer freelancer = Freelancer.createFreelancer(
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
        );

        FreelancerProfile freelancerProfile = new FreelancerProfile("greeting", freelancer, PositionType.DEVELOPER);

        MemberDetails memberDetails = MemberDetails.userDetailsFrom(freelancer);

        Developer developer = Developer.createBasicDeveloper(PositionType.DEVELOPER, freelancerProfile, "java", "role");

        String introduceName = "?????????";
        IntroBackGround introBackGround = IntroBackGround.COBALT_BLUE;
        String introduceVideoURL = "?????? ?????? ??????";
        String introduceContent = "?????? ??????";
        freelancerProfile.coverIntroduceInFreelancer(freelancerProfile.getGreeting(), introduceName, introBackGround, introduceVideoURL, introduceContent);
        freelancerProfile.coverPosition(developer);

        Freelancer freelancer2 = Freelancer.createFreelancer(
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
        );

        FreelancerProfile freelancerProfile2 = new FreelancerProfile("greeting", freelancer2, PositionType.PLANNER);

        Planner planner = Planner.createBasicPlanner(PositionType.PLANNER, freelancerProfile2);

        String introduceName2 = "?????????";
        IntroBackGround introBackGround2 = IntroBackGround.COBALT_BLUE;
        String introduceVideoURL2 = "?????? ?????? ??????";
        String introduceContent2 = "?????? ??????";
        freelancerProfile2.coverIntroduceInFreelancer(freelancerProfile2.getGreeting(), introduceName2, introBackGround2, introduceVideoURL2, introduceContent2);
        freelancerProfile2.coverPosition(planner);


        when(freelancerProfileFindRepository.findFreelancersByCreateDate()).thenReturn(Arrays.asList(freelancerProfile2, freelancerProfile));

        //when
        FreelancerSimpleResponses responses = freelancerProfileFindService.findSimpleFreelancers(memberDetails);

        //then
        Assertions.assertThat(responses.getFreelancerSimpleResponseList()).hasSize(2);
        Assertions.assertThat(responses.getFreelancerSimpleResponseList().get(0).getFreelancerName()).isEqualTo(freelancer2.getName());
        Assertions.assertThat(responses.getFreelancerSimpleResponseList().get(0).getGreeting()).isEqualTo(freelancerProfile2.getGreeting());
        Assertions.assertThat(responses.getFreelancerSimpleResponseList().get(0).getCareerYear()).isEqualTo(freelancer2.getFreelancerAccountInfo().getCareerYear());
        Assertions.assertThat(responses.getFreelancerSimpleResponseList().get(0).getPositionName()).isEqualTo(freelancerProfile2.getPosition().getPositionType().getDesc());
        Assertions.assertThat(responses.getFreelancerSimpleResponseList().get(1).getFreelancerName()).isEqualTo(freelancer.getName());
        Assertions.assertThat(responses.getFreelancerSimpleResponseList().get(1).getGreeting()).isEqualTo(freelancerProfile.getGreeting());
        Assertions.assertThat(responses.getFreelancerSimpleResponseList().get(1).getCareerYear()).isEqualTo(freelancer.getFreelancerAccountInfo().getCareerYear());
        Assertions.assertThat(responses.getFreelancerSimpleResponseList().get(1).getPositionName()).isEqualTo(freelancerProfile.getPosition().getPositionType().getDesc());

    }
}