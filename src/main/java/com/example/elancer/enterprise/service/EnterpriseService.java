package com.example.elancer.enterprise.service;

import com.example.elancer.common.checker.RightRequestChecker;
import com.example.elancer.enterprise.dto.*;
import com.example.elancer.enterprise.exception.EnterpriseCheckUserIdException;
import com.example.elancer.enterprise.exception.NotExistEnterpriseException;
import com.example.elancer.enterprise.model.enterprise.Enterprise;
import com.example.elancer.enterprise.model.enterprise.EnterpriseBizRegistration;
import com.example.elancer.enterprise.model.enterprise.EnterpriseThumbnail;
import com.example.elancer.enterprise.model.enterpriseintro.*;
import com.example.elancer.enterprise.repository.*;
import com.example.elancer.freelancerprofile.dto.FreelancerSimpleResponse;
import com.example.elancer.freelancerprofile.dto.FreelancerSimpleResponses;
import com.example.elancer.login.auth.dto.MemberDetails;
import com.example.elancer.wishfreelancer.model.WishFreelancer;
import com.example.elancer.wishfreelancer.repository.WishFreelancerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class EnterpriseService {

    private final PasswordEncoder passwordEncoder;
    private final EnterpriseRepository enterpriseRepository;
    private final EnterpriseIntroRepository enterpriseIntroRepository;
    private final MainBusinessRepository mainBusinessRepository;
    private final SubBusinessRepository subBusinessRepository;
    private final WishFreelancerRepository wishFreelancerRepository;
    private final EnterpriseThumbnailRepository enterpriseThumbnailRepository;
    private final EnterpriseBizRegistrationRepository enterpriseBizRegistrationRepository;
    private final CareerStatementRepository careerStatementRepository;
    private final PortfolioRepository portfolioRepository;
    private final EnterpriseMainBizRepository enterpriseMainBizRepository;
    private final EnterpriseSubBizRepository enterpriseSubBizRepository;




    /**
     * ?????? ?????? ??????
     * @param memberDetails
     * @return
     */
    public EnterpriseAccountDetailResponse findDetailEnterpriseAccount(MemberDetails memberDetails) {
        RightRequestChecker.checkMemberDetail(memberDetails);
        Enterprise enterprise = enterpriseRepository.findById(memberDetails.getId()).orElseThrow(NotExistEnterpriseException::new);
        return EnterpriseAccountDetailResponse.of(enterprise);
    }

    public EnterpriseSimpleDetailResponse findSimpleEnterpriseInfo(MemberDetails memberDetails) {
        RightRequestChecker.checkMemberDetail(memberDetails);
        Enterprise enterprise = enterpriseRepository.findById(memberDetails.getId()).orElseThrow(NotExistEnterpriseException::new);
        EnterpriseSimpleDetailResponse simpleDetailResponse = EnterpriseSimpleDetailResponse.of(enterprise);
        return simpleDetailResponse;
    }


    /**
     * ?????? ?????? ??????
     * @param memberDetails
     * @param enterpriseUpdateRequest
     */
    @Transactional
    public EnterpriseAccountDetailResponse coverEnterpriseAccountInfo(MemberDetails memberDetails, EnterpriseUpdateRequest enterpriseUpdateRequest) {

        Enterprise enterprise = enterpriseRepository.findById(memberDetails.getId()).orElseThrow(EnterpriseCheckUserIdException::new);

        updateThumbnail(enterpriseUpdateRequest.getThumbnail(), enterprise);
        updateBizRegistration(enterpriseUpdateRequest.getBizRegistration(), enterprise);


        if (StringUtils.hasText(enterpriseUpdateRequest.getPassword1())) {
            RightRequestChecker.checkPasswordMatchEnterprise(enterpriseUpdateRequest.getPassword1(), enterpriseUpdateRequest.getPassword2());
            enterprise.updateEnterprise(
                    passwordEncoder.encode(enterpriseUpdateRequest.getPassword1()),
                    enterpriseUpdateRequest.getName(),
                    enterpriseUpdateRequest.getPhone(),
                    enterpriseUpdateRequest.getEmail(),
                    enterpriseUpdateRequest.getCompanyName(),
                    enterpriseUpdateRequest.getCompanyPeople(),
                    enterpriseUpdateRequest.getPosition(),
                    enterpriseUpdateRequest.getTelNumber(),
                    enterpriseUpdateRequest.getWebsite(),
                    enterpriseUpdateRequest.getAddress(),
                    enterpriseUpdateRequest.getBizContents(),
                    enterpriseUpdateRequest.getSales(),
                    enterpriseUpdateRequest.getIdNumber()

            );
        }
        else {
            enterprise.updateEnterprise(
                    enterprise.getPassword(),
                    enterpriseUpdateRequest.getName(),
                    enterpriseUpdateRequest.getPhone(),
                    enterpriseUpdateRequest.getEmail(),
                    enterpriseUpdateRequest.getCompanyName(),
                    enterpriseUpdateRequest.getCompanyPeople(),
                    enterpriseUpdateRequest.getPosition(),
                    enterpriseUpdateRequest.getTelNumber(),
                    enterpriseUpdateRequest.getWebsite(),
                    enterpriseUpdateRequest.getAddress(),
                    enterpriseUpdateRequest.getBizContents(),
                    enterpriseUpdateRequest.getSales(),
                    enterpriseUpdateRequest.getIdNumber()
            );
        }


        return EnterpriseAccountDetailResponse.of(enterprise);
    }



    /**
     * ?????? ????????? ?????? ??????
     * @param memberDetails
     * @return
     */
    public EnterpriseProfileResponse findEnterpriseProfile(MemberDetails memberDetails) {
        RightRequestChecker.checkMemberDetail(memberDetails);
        Enterprise enterprise = enterpriseRepository.findById(memberDetails.getId()).orElseThrow(NotExistEnterpriseException::new);
        return EnterpriseProfileResponse.of(enterprise);

    }


    /**
     * ?????? ????????? ????????????
     * @param memberDetails
     * @param enterpriseProfileRequest
     */
    @Transactional
    public EnterpriseProfileResponse updateIntro(MemberDetails memberDetails, EnterpriseProfileRequest enterpriseProfileRequest) {
        RightRequestChecker.checkMemberDetail(memberDetails);
        Enterprise enterprise = enterpriseRepository.findById(memberDetails.getId()).orElseThrow(NotExistEnterpriseException::new);

        enterpriseMainBizRepository.deleteByEnterpriseIntroNum(enterprise.getEnterpriseIntro().getNum());
        enterpriseSubBizRepository.deleteByEnterpriseIntroNum(enterprise.getEnterpriseIntro().getNum());

        List<EnterpriseMainBiz> enterpriseMainBizs = getEnterpriseMainBizs(enterpriseProfileRequest, enterprise.getEnterpriseIntro());
        List<EnterpriseSubBiz> enterpriseSubBizs = getEnterpriseSubBizs(enterpriseProfileRequest, enterprise.getEnterpriseIntro());

        mainEtcInsert(enterpriseProfileRequest, enterpriseMainBizs);
        subEtcInsert(enterpriseProfileRequest, enterpriseSubBizs);

        EnterpriseIntro findEnterpriseIntro = enterprise.getEnterpriseIntro();


        findEnterpriseIntro.updateEnterpriseIntro(
                enterpriseProfileRequest.getIntroTitle(),
                enterpriseMainBizs,
                enterpriseSubBizs
        );

        updateBizRegistration(enterpriseProfileRequest.getBizRegistration(), enterprise);


        updateCareerStatement(enterpriseProfileRequest.getCareerStatementPath(), findEnterpriseIntro);
        updatePortfolio(enterpriseProfileRequest.getPortfolioPath(), findEnterpriseIntro);

        enterprise.updateIntro(
                enterpriseProfileRequest.getBizContents(),
                enterpriseProfileRequest.getSales(),
                enterpriseProfileRequest.getIdNumber());

        return EnterpriseProfileResponse.of(enterprise);
    }

    private void updateCareerStatement(String careerStatement, EnterpriseIntro enterpriseIntro) {
        if (careerStatement == null) {
            return;
        }
        if (enterpriseIntro.getCareerStatement() == null) {
            careerStatementRepository.save(CareerStatement.createCareerStatement(careerStatement, enterpriseIntro));
        } else {
            enterpriseIntro.getCareerStatement().updateCareerStatementPath(careerStatement);
        }
    }

    private void updatePortfolio(String portfolioPath, EnterpriseIntro enterpriseIntro) {
        if (portfolioPath == null) {
            return;
        }
        if (enterpriseIntro.getCareerStatement() == null) {
            portfolioRepository.save(Portfolio.createPortfolio(portfolioPath, enterpriseIntro));
        } else {
            enterpriseIntro.getPortfolio().updatePortfolioPath(portfolioPath);
        }
    }



    public EnterpriseDashBoardProfileResponse findDashBoardProfile(MemberDetails memberDetails) {
        RightRequestChecker.checkMemberDetail(memberDetails);
        Enterprise enterprise = enterpriseRepository.findById(memberDetails.getId()).orElseThrow(NotExistEnterpriseException::new);

        return EnterpriseDashBoardProfileResponse.of(enterprise);

    }

    public FreelancerSimpleResponses findWishFreelancer(MemberDetails memberDetails) {
        RightRequestChecker.checkMemberDetail(memberDetails);
        Enterprise enterprise = enterpriseRepository.findById(memberDetails.getId()).orElseThrow(NotExistEnterpriseException::new);
        RightRequestChecker.checkEnterpriseAndRequester(memberDetails, enterprise);

        List<WishFreelancer> findWishFreelancerByEnterprise = wishFreelancerRepository.findByEnterpriseNum(enterprise.getNum());
        List<FreelancerSimpleResponse> freelancerSimpleResponses = findWishFreelancerByEnterprise.stream().map(s ->
                FreelancerSimpleResponse.of(s.getFreelancer().getFreelancerProfile().getPosition()
                )
        ).collect(Collectors.toList());
        freelancerSimpleResponses.forEach(s -> s.switchWishState());

        return new FreelancerSimpleResponses(freelancerSimpleResponses, false);

    }

    public EnterpriseThumbnailResponse findThumbnail(MemberDetails memberDetails) {
        RightRequestChecker.checkMemberDetail(memberDetails);

        Enterprise enterprise = enterpriseRepository.findById(memberDetails.getId()).orElseThrow(NotExistEnterpriseException::new);

        return new EnterpriseThumbnailResponse(
                Optional.ofNullable(enterprise.getEnterpriseThumbnail()).map(EnterpriseThumbnail::getThumbnailPath).orElse(null)
        );
    }


    /**
     * ????????? ??????
     */

    private void checkDuplicate(String userId) {
        if (enterpriseRepository.existsByUserId(userId)) {
            throw new EnterpriseCheckUserIdException();
        }
    }
    private List<EnterpriseSubBiz> getEnterpriseSubBizs(EnterpriseProfileRequest enterpriseProfileRequest, EnterpriseIntro enterpriseIntro) {
        List<SubBusiness> subBusiness = subBusinessRepository.findSubBusiness(enterpriseProfileRequest.getSubBizCodes());
        return EnterpriseSubBiz.createList(subBusiness, enterpriseIntro);
    }

    private List<EnterpriseMainBiz> getEnterpriseMainBizs(EnterpriseProfileRequest enterpriseProfileRequest, EnterpriseIntro enterpriseIntro) {
        List<MainBusiness> mainBusiness = mainBusinessRepository.findMainBusiness(enterpriseProfileRequest.getMainBizCodes());
        return EnterpriseMainBiz.createList(mainBusiness, enterpriseIntro);
    }

    private void mainEtcInsert(EnterpriseProfileRequest enterpriseProfileRequest, List<EnterpriseMainBiz> enterpriseMainBizs) {
        for (EnterpriseMainBiz enterpriseMainBiz : enterpriseMainBizs) {
            if (enterpriseMainBiz.getMainBusiness().getCode().equals("main_etc")) {
                enterpriseMainBiz.setEtc(enterpriseProfileRequest.getMainEtc());
            }
        }
    }

    private void subEtcInsert(EnterpriseProfileRequest enterpriseProfileRequest, List<EnterpriseSubBiz> enterpriseSubBizs) {
        for (EnterpriseSubBiz enterpriseSubBiz : enterpriseSubBizs) {
            if (enterpriseSubBiz.getSubBusiness().getCode().equals("sub_etc")) {
                enterpriseSubBiz.setEtc(enterpriseProfileRequest.getSubEtc());
            }
        }
    }

    private void updateThumbnail(String thumbnail, Enterprise enterprise) {
        if (thumbnail == null) {
            return;
        }
        if (enterprise.getEnterpriseThumbnail() == null) {
            enterpriseThumbnailRepository.save(EnterpriseThumbnail.createEnterpriseThumbnail(thumbnail, enterprise));
        } else {
            enterprise.getEnterpriseThumbnail().updateThumbnailpath(thumbnail);
        }
    }

    private void updateBizRegistration(String fileUrl, Enterprise enterprise) {
        if (fileUrl == null) {
            return;
        }
        if (enterprise.getEnterpriseBizRegistration() == null) {
            enterpriseBizRegistrationRepository.save(EnterpriseBizRegistration.createEnterpriseBizRegistration(fileUrl, enterprise));
        } else {
            enterprise.getEnterpriseBizRegistration().updateBizRegistration(fileUrl);
        }
    }


}
