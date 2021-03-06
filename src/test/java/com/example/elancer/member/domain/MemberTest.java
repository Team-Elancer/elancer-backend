package com.example.elancer.member.domain;

import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

@DataJpaTest
@ActiveProfiles("h2")
class MemberTest {
//    @Autowired
//    private MemberRepository memberRepository;
//
//    @Autowired
//    private FreelancerRepository freelancerRepository;
//
//    @Autowired
//    private EnterpriseRepository enterpriseRepository;
//
//    @DisplayName("프리랜서, 기업 생성및 조회 확인.")
//    @Test
//    public void name() {
//        //given
//        Freelancer freelancer1 = new Freelancer(
//                "memberId1",
//                "pwd",
//                "name",
//                "phone",
//                "email",
//                MemberType.FREELANCER,
//                MailReceptionState.RECEPTION,
//                WorkPossibleState.POSSIBLE,
//                LocalDate.of(2021, 02, 01),
//                null
//        );
//
//        Freelancer freelancer2 = new Freelancer(
//                "memberId1",
//                "pwd",
//                "name",
//                "phone",
//                "email",
//                MemberType.FREELANCER,
//                MailReceptionState.RECEPTION,
//                WorkPossibleState.POSSIBLE,
//                LocalDate.of(2021, 02, 01),
//                null
//        );
//
//        Freelancer freelancer3 = new Freelancer(
//                "memberId1",
//                "pwd",
//                "name",
//                "phone",
//                "email",
//                MemberType.FREELANCER,
//                MailReceptionState.RECEPTION,
//                WorkPossibleState.POSSIBLE,
//                LocalDate.of(2021, 02, 01),
//                null
//        );
//
////        Enterprise enterprise1 = new Enterprise("T-test1");
////        Enterprise enterprise2 = new Enterprise("T-test2");
////        Enterprise enterprise3 = new Enterprise("T-test3");
//
//        memberRepository.save(freelancer1);
////        memberRepository.save(enterprise1);
//
//        freelancerRepository.save(freelancer2);
//        freelancerRepository.save(freelancer3);
//
////        enterpriseRepository.save(enterprise2);
////        enterpriseRepository.save(enterprise3);
//        //when
//        List<Member> allMember = memberRepository.findAll();
//        List<Freelancer> allFree = freelancerRepository.findAll();
////        List<Enterprise> allEnter = enterpriseRepository.findAll();
//
//        //then
//        Assertions.assertThat(allMember).hasSize(6);
//        Assertions.assertThat(allFree).hasSize(3);
////        Assertions.assertThat(allEnter).hasSize(3);
//    }
}