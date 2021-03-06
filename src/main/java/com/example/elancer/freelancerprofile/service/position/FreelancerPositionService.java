package com.example.elancer.freelancerprofile.service.position;

import com.example.elancer.common.checker.RightRequestChecker;
import com.example.elancer.common.utils.StringEditor;
import com.example.elancer.freelancerprofile.dto.request.position.DesignerCoverRequest;
import com.example.elancer.freelancerprofile.dto.request.position.DeveloperCoverRequest;
import com.example.elancer.freelancerprofile.dto.request.position.PlannerCoverRequest;
import com.example.elancer.freelancerprofile.dto.request.position.PositionEtcCoverRequest;
import com.example.elancer.freelancerprofile.dto.request.position.PublisherCoverRequest;
import com.example.elancer.freelancerprofile.exception.NotExistFreelancerProfileException;
import com.example.elancer.freelancerprofile.model.FreelancerProfile;
import com.example.elancer.freelancerprofile.model.position.CrowdWorker;
import com.example.elancer.freelancerprofile.model.position.PositionType;
import com.example.elancer.freelancerprofile.model.position.designer.Designer;
import com.example.elancer.freelancerprofile.model.position.developer.Developer;
import com.example.elancer.freelancerprofile.model.position.etc.PositionEtc;
import com.example.elancer.freelancerprofile.model.position.planner.Planner;
import com.example.elancer.freelancerprofile.model.position.publisher.Publisher;
import com.example.elancer.freelancerprofile.repository.FreelancerProfileRepository;
import com.example.elancer.freelancerprofile.repository.position.CrowdWorkerRepository;
import com.example.elancer.freelancerprofile.repository.position.designer.DesignerRepository;
import com.example.elancer.freelancerprofile.repository.position.developer.DeveloperRepository;
import com.example.elancer.freelancerprofile.repository.position.etc.PositionEtcRepository;
import com.example.elancer.freelancerprofile.repository.position.planner.PlannerRepository;
import com.example.elancer.freelancerprofile.repository.position.publisher.PublisherRepository;
import com.example.elancer.login.auth.dto.MemberDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class FreelancerPositionService {
    private final FreelancerProfileRepository freelancerProfileRepository;
    private final DeveloperRepository developerRepository;
    private final PublisherRepository publisherRepository;
    private final DesignerRepository designerRepository;
    private final PlannerRepository plannerRepository;
    private final CrowdWorkerRepository crowdWorkerRepository;
    private final PositionEtcRepository positionEtcRepository;

    /**
     * 1. ??????????????? ?????????????????? develop??? ????????????. -> developer db?????? num????????? ??????, ????????? developer??? ????????? ????????? ??????????????? ??????????????????.
     * 2. developer ??????????????? ?????? ????????????.
     * 3. ????????? developer ??????????????? skillset??? ???????????? ????????????. -> ????????? developer ???????????? ????????? develop ???????????? ?????? and ?????? ????????? ??????.
     * !!! ????????? ???????????? Developer????????? ???????????? coverDeveloperSkills???????????? ?????? ?????? ???????????? ????????? ?????? ????????? ??????????????? ?????? ????????? ??????. ?????? ?????? ????????? ??????????????? coverDeveloperSkills??? ???????????? ???????????? ????????????
     * ?????????.. ?????? coverDeveloperSkills????????? ???????????? Developer?????? ?????? ?????? ????????? ???????????? ?????? ??? ????????? Developer????????? ???????????? ???????????? ????????? ????????? ????????? ??????????????? ??????.
     **/
    @Transactional
    public void coverFreelancerPositionToDeveloper(MemberDetails memberDetails, DeveloperCoverRequest developerCoverRequest) {
        RightRequestChecker.checkMemberDetail(memberDetails);
        FreelancerProfile freelancerProfile = freelancerProfileRepository.findByFreelancerNum(memberDetails.getId()).orElseThrow(NotExistFreelancerProfileException::new);
        RightRequestChecker.checkFreelancerProfileAndRequester(freelancerProfile, memberDetails);
        Developer developer = Developer.createBasicDeveloper(
                PositionType.DEVELOPER, freelancerProfile,
                StringEditor.editStringListToString(developerCoverRequest.getFocusSkills()),
                StringEditor.editStringListToString(developerCoverRequest.getRoles())
        );

        developer.coverDeveloperSkills(
                developerCoverRequest.toJavaSkill(developer),
                developerCoverRequest.toMobileAppSkill(developer),
                developerCoverRequest.toPhpOrAspSkill(developer),
                developerCoverRequest.toDotNetSkill(developer),
                developerCoverRequest.toJavaScriptSkill(developer),
                developerCoverRequest.toCSkill(developer),
                developerCoverRequest.toDBSkill(developer),
                developerCoverRequest.getEtcSkill()
        );
        freelancerProfile.coverPosition(developerRepository.save(developer));
    }

    @Transactional
    public void coverFreelancerPositionToPublisher(MemberDetails memberDetails, PublisherCoverRequest publisherCoverRequest) {
        RightRequestChecker.checkMemberDetail(memberDetails);
        FreelancerProfile freelancerProfile = freelancerProfileRepository.findByFreelancerNum(memberDetails.getId()).orElseThrow(NotExistFreelancerProfileException::new);
        RightRequestChecker.checkFreelancerProfileAndRequester(freelancerProfile, memberDetails);
        Publisher publisher = Publisher.createBasicPublisher(PositionType.PUBLISHER, freelancerProfile, publisherCoverRequest.getEtcSkill());
        publisher.coverPublishingSkill(publisherCoverRequest.toPublishingSkill(publisher));

        freelancerProfile.coverPosition(publisherRepository.save(publisher));
    }

    @Transactional
    public void coverFreelancerPositionToDesigner(MemberDetails memberDetails, DesignerCoverRequest designerCoverRequest) {
        RightRequestChecker.checkMemberDetail(memberDetails);
        FreelancerProfile freelancerProfile = freelancerProfileRepository.findByFreelancerNum(memberDetails.getId()).orElseThrow(NotExistFreelancerProfileException::new);
        RightRequestChecker.checkFreelancerProfileAndRequester(freelancerProfile, memberDetails);
        Designer designer = Designer.createBasicDesigner(PositionType.DESIGNER, freelancerProfile);
        designer.coverDesignRoleAndSkill(
                designerCoverRequest.toDesignRoles(designer),
                designerCoverRequest.toDesignSkills(designer),
                designerCoverRequest.getEtcRole(),
                designerCoverRequest.getEtcSkill()
        );

        freelancerProfile.coverPosition(designerRepository.save(designer));
    }

    @Transactional
    public void coverFreelancerPositionToPlanner(MemberDetails memberDetails, PlannerCoverRequest plannerCoverRequest) {
        RightRequestChecker.checkMemberDetail(memberDetails);
        FreelancerProfile freelancerProfile = freelancerProfileRepository.findByFreelancerNum(memberDetails.getId()).orElseThrow(NotExistFreelancerProfileException::new);
        RightRequestChecker.checkFreelancerProfileAndRequester(freelancerProfile, memberDetails);
        Planner planner = Planner.createBasicPlanner(PositionType.PLANNER, freelancerProfile);
        planner.coverAllField(plannerCoverRequest.toPlannerField(planner), plannerCoverRequest.getEtcField());

        freelancerProfile.coverPosition(plannerRepository.save(planner));
    }

    @Transactional
    public void coverFreelancerPositionToCrowdWorker(MemberDetails memberDetails) {
        RightRequestChecker.checkMemberDetail(memberDetails);
        FreelancerProfile freelancerProfile = freelancerProfileRepository.findByFreelancerNum(memberDetails.getId()).orElseThrow(NotExistFreelancerProfileException::new);
        RightRequestChecker.checkFreelancerProfileAndRequester(freelancerProfile, memberDetails);
        CrowdWorker crowdWorker = new CrowdWorker(PositionType.CROWD_WORKER, freelancerProfile);

        freelancerProfile.coverPosition(crowdWorkerRepository.save(crowdWorker));
    }

    @Transactional
    public void coverFreelancerPositionToEtc(MemberDetails memberDetails, PositionEtcCoverRequest positionEtcCoverRequest) {
        RightRequestChecker.checkMemberDetail(memberDetails);
        FreelancerProfile freelancerProfile = freelancerProfileRepository.findByFreelancerNum(memberDetails.getId()).orElseThrow(NotExistFreelancerProfileException::new);
        RightRequestChecker.checkFreelancerProfileAndRequester(freelancerProfile, memberDetails);
        PositionEtc positionEtc = PositionEtc.createBasicPositionEtc(PositionType.ETC, freelancerProfile);
        positionEtc.coverAllField(positionEtcCoverRequest.toEtcRole(positionEtc), positionEtcCoverRequest.getPositionEtcRole());

        freelancerProfile.coverPosition(positionEtcRepository.save(positionEtc));
    }
}
