package com.example.elancer.freelancerprofile.dto;

import com.example.elancer.freelancer.model.FreelancerThumbnail;
import com.example.elancer.freelancer.model.IntroBackGround;
import com.example.elancer.freelancerprofile.model.position.Position;
import com.example.elancer.freelancerprofile.model.position.developer.Developer;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class FreelancerSimpleResponse {
    private Long freelancerNum;
    private String positionName;
    private String freelancerName;
    private IntroBackGround introBackGround;
    private int careerYear;
    private boolean wishState;
    private String greeting;
    private List<String> skills;
    private List<String> projectNames;
    private String thumbnailPath;

    public FreelancerSimpleResponse(
            Long freelancerNum,
            String positionName,
            String freelancerName,
            IntroBackGround introBackGround,
            int careerYear,
            String greeting,
            List<String> skills,
            List<String> projectNames,
            String thumbnailPath
    ) {
        this.freelancerNum = freelancerNum;
        this.positionName = positionName;
        this.freelancerName = freelancerName;
        this.introBackGround = introBackGround;
        this.careerYear = careerYear;
        this.greeting = greeting;
        this.skills = skills;
        this.projectNames = projectNames;
        this.thumbnailPath = thumbnailPath;
    }

    public static FreelancerSimpleResponse of(Position position) {
        return new FreelancerSimpleResponse(
                position.getFreelancerProfile().getFreelancer().getNum(),
                position.getPositionType().getDesc(),
                position.getFreelancerProfile().getFreelancer().getName(),
                position.getFreelancerProfile().getIntroBackGround(),
                position.getFreelancerProfile().getFreelancer().getFreelancerAccountInfo().getCareerYear(),
                position.getFreelancerProfile().getGreeting(),
                position.getAllSkillNames(),
                position.getFreelancerProfile().getProjectHistoryNames(),
                Optional.ofNullable(position.getFreelancerProfile().getFreelancer().getFreelancerThumbnail()).map(FreelancerThumbnail::getThumbnailPath).orElse(null)
        );
    }

    public void switchWishState() {
        this.wishState = true;
    }

}
