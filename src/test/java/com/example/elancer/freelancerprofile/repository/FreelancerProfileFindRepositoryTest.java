package com.example.elancer.freelancerprofile.repository;

import com.example.elancer.common.FreelancerHelper;
import com.example.elancer.freelancer.model.Freelancer;
import com.example.elancer.freelancer.model.IntroBackGround;
import com.example.elancer.freelancer.repository.FreelancerRepository;
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
import com.example.elancer.freelancerprofile.model.projecthistory.DevelopEnvironment;
import com.example.elancer.freelancerprofile.model.projecthistory.DevelopField;
import com.example.elancer.freelancerprofile.model.projecthistory.ProjectHistory;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.Optional;

@SpringBootTest
@ActiveProfiles("h2")
/**
 * 1. @DataJpaTest??? ????????? ?????? ???????????? ????????? ???????????????. (yml ????????? ????????? ??????????????? ???????????? ????????? ????????????)
 * 2. ????????? @AutoConfigureTestDatabase ?????????????????? ?????? ???????????? ????????? ?????????????????? ??????????????? ????????? ????????? ???????????? ??????.
 * */
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class FreelancerProfileFindRepositoryTest {

    @Autowired
    private FreelancerRepository freelancerRepository;

    @Autowired
    private FreelancerProfileRepository freelancerProfileRepository;

    @Autowired
    private FreelancerProfileFindRepository freelancerProfileFindRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @DisplayName("ddd")
//    @Test
    public void name() {
        //given
        Freelancer freelancer = FreelancerHelper.????????????_??????(freelancerRepository, passwordEncoder);
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

        FreelancerProfile savedProfile = freelancerProfileRepository.save(freelancerProfile);

        //when
        FreelancerProfile freelancerProfileByFetch = freelancerProfileFindRepository.findFreelancerProfileByFetch(freelancer.getNum()).get();

        //then
        Assertions.assertThat(freelancerProfileByFetch.getIntroduceName()).isEqualTo(introduceName);
        Assertions.assertThat(freelancerProfileByFetch.getIntroBackGround()).isEqualTo(introBackGround);
        Assertions.assertThat(freelancerProfileByFetch.getIntroduceVideoURL()).isEqualTo(introduceVideoURL);
        Assertions.assertThat(freelancerProfileByFetch.getIntroduceContent()).isEqualTo(introduceContent);
        Assertions.assertThat(freelancerProfileByFetch.getGreeting()).isEqualTo(freelancerProfile.getGreeting());
    }
}