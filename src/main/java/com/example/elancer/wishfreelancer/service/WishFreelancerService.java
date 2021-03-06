package com.example.elancer.wishfreelancer.service;

import com.example.elancer.common.checker.RightRequestChecker;
import com.example.elancer.enterprise.model.enterprise.Enterprise;
import com.example.elancer.enterprise.exception.NotExistEnterpriseException;
import com.example.elancer.enterprise.repository.EnterpriseRepository;
import com.example.elancer.freelancer.exception.NotExistFreelancerException;
import com.example.elancer.freelancer.model.Freelancer;
import com.example.elancer.freelancer.repository.FreelancerRepository;
import com.example.elancer.login.auth.dto.MemberDetails;
import com.example.elancer.wishfreelancer.dto.WishFreelancerRequest;
import com.example.elancer.wishfreelancer.exception.NotExistWishFreelancerException;
import com.example.elancer.wishfreelancer.exception.PresentWishFreelancerException;
import com.example.elancer.wishfreelancer.model.WishFreelancer;
import com.example.elancer.wishfreelancer.repository.WishFreelancerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly = true)
@Service
@RequiredArgsConstructor
public class WishFreelancerService {

    private final EnterpriseRepository enterpriseRepository;
    private final FreelancerRepository freelancerRepository;
    private final WishFreelancerRepository wishFreelancerRepository;

    @Transactional
    public void addWishFreelancer(MemberDetails memberDetails, WishFreelancerRequest wishFreelancerRequest) {

        Enterprise enterprise = enterpriseRepository.findById(memberDetails.getId()).orElseThrow(NotExistEnterpriseException::new);
        Freelancer freelancer = freelancerRepository.findById(wishFreelancerRequest.getFreelancerNum()).orElseThrow(NotExistFreelancerException::new);
        if (wishFreelancerRepository.findByFreelancerNumAndEnterpriseNum(freelancer.getNum(), enterprise.getNum()).isPresent()) {
            throw new PresentWishFreelancerException();
        }

        WishFreelancer wishFreelancer = WishFreelancer.createWishFreelancer(enterprise, freelancer);
        wishFreelancerRepository.save(wishFreelancer);

    }
    @Transactional
    public void deleteWishFreelancer(MemberDetails memberDetails, Long freelancerNum) {
        RightRequestChecker.checkMemberDetail(memberDetails);
        Freelancer freelancer = freelancerRepository.findById(freelancerNum).orElseThrow(NotExistFreelancerException::new);

        wishFreelancerRepository.delete(
                wishFreelancerRepository.findByFreelancerNumAndEnterpriseNum(freelancer.getNum(), memberDetails.getId()).orElseThrow(NotExistWishFreelancerException::new)
        );
    }
}
