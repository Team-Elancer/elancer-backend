package com.example.elancer.freelancer.join.service;

import com.example.elancer.common.checker.RightRequestChecker;
import com.example.elancer.freelancer.join.dto.FreelancerJoinRequest;
import com.example.elancer.freelancer.join.exception.ExistUserIdException;
import com.example.elancer.freelancer.model.Freelancer;
import com.example.elancer.freelancer.model.FreelancerThumbnail;
import com.example.elancer.freelancer.repository.FreelancerRepository;
import com.example.elancer.freelancer.repository.FreelancerThumbnailRepository;
import com.example.elancer.freelancerprofile.model.FreelancerProfile;
import com.example.elancer.freelancerprofile.model.position.PositionType;
import com.example.elancer.freelancerprofile.repository.FreelancerProfileRepository;
import com.example.elancer.member.domain.MemberType;
import com.example.elancer.s3.service.S3UploadService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;

@Service
@RequiredArgsConstructor
public class FreelancerJoinService {
    // min - 옮겨야 하는 로직. 당장은 감이 안잡힌다. 방식도 replace보다 좋은 방법 찾는게 좋을것 같다.
    public static final String GREETING_DEFAULT_MESSAGE = "안녕하세요 xx 입니다.";
    
    private final FreelancerRepository freelancerRepository;
    private final PasswordEncoder bCryptPasswordEncoder;
    private final FreelancerProfileRepository freelancerProfileRepository;
    private final FreelancerThumbnailRepository freelancerThumbnailRepository;
    private final S3UploadService s3UploadService;

    @Transactional
    public void joinFreelancer(FreelancerJoinRequest freelancerJoinRequest) {
        RightRequestChecker.checkPasswordMatch(freelancerJoinRequest.getMemberPassword(), freelancerJoinRequest.getMemberPasswordCheck());
        checkExistUserId(freelancerJoinRequest);
        Freelancer freelancer = Freelancer.createFreelancer(
                freelancerJoinRequest.getMemberId(),
                bCryptPasswordEncoder.encode(freelancerJoinRequest.getMemberPassword()),
                freelancerJoinRequest.getMemberName(),
                freelancerJoinRequest.getMemberPhone(),
                freelancerJoinRequest.getMemberEmail(),
                null,
                null,
                MemberType.FREELANCER,
                freelancerJoinRequest.getMailReceptionState(),
                freelancerJoinRequest.getWorkPossibleState(),
                freelancerJoinRequest.getWorkStartPossibleDate()
        );

        Freelancer savedFreelancer = freelancerRepository.save(freelancer);

        initializeFreelancerProfile(savedFreelancer, freelancerJoinRequest.getPositionType());
        saveFreelancerThumbnail(freelancerJoinRequest, savedFreelancer);
    }

    private void saveFreelancerThumbnail(FreelancerJoinRequest freelancerJoinRequest, Freelancer savedFreelancer) {
        if (freelancerJoinRequest.getThumbnail() == null) {
            return;
        }

        String thumbnailPath = s3UploadService.uploadForMultiFile(freelancerJoinRequest.getThumbnail());
        freelancerThumbnailRepository.save(FreelancerThumbnail.createFreelancerThumbnail(thumbnailPath, savedFreelancer));
    }

    private void initializeFreelancerProfile(Freelancer savedFreelancer, PositionType positionType) {
        freelancerProfileRepository.save(new FreelancerProfile(GREETING_DEFAULT_MESSAGE.replace("xx", savedFreelancer.getName()), savedFreelancer, positionType));
    }

    private void checkExistUserId(FreelancerJoinRequest freelancerJoinRequest) {
        if (freelancerRepository.existsByUserId(freelancerJoinRequest.getMemberId())) {
            throw new ExistUserIdException();
        }
    }
}
