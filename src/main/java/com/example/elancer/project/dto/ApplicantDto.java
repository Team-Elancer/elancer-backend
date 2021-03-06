package com.example.elancer.project.dto;

import com.example.elancer.freelancer.model.Freelancer;
import com.example.elancer.freelancerprofile.model.position.PositionType;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ApplicantDto {

    private Long num;
    private String name;
    private int careerYear;
    private PositionType positionType;

    public ApplicantDto(Long num, String name) {
        this.num = num;
        this.name = name;
    }

    public ApplicantDto(Long num, String name, int careerYear, PositionType positionType) {
        this.num = num;
        this.name = name;
        this.careerYear = careerYear;
        this.positionType = positionType;
    }

    public static ApplicantDto of(Freelancer freelancer) {
        return new ApplicantDto(
                freelancer.getNum(),
                freelancer.getName(),
                freelancer.getFreelancerAccountInfo().getCareerYear(),
                freelancer.getFreelancerProfile().getPosition().getPositionType());
    }

    public static List<ApplicantDto> createApplicantList(List<Freelancer> freelancers) {
        return freelancers.stream().map(s ->
                ApplicantDto.of(s))
                .collect(Collectors.toList());
    }
}
