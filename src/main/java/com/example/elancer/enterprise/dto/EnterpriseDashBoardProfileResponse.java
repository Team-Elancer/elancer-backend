package com.example.elancer.enterprise.dto;


import com.example.elancer.enterprise.model.enterprise.Enterprise;
import com.example.elancer.enterprise.model.enterprise.EnterpriseThumbnail;
import lombok.*;

import java.util.Optional;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class EnterpriseDashBoardProfileResponse {


    private String name;
    private int expertise;
    private int scheduleAdherence;
    private int initiative;
    private int communication;
    private int reEmploymentIntention;
    private double totalActiveScore;

    private String enterpriseType;

    private String idNumber;

    private Long sales;

    private String thumbnail;

    public static EnterpriseDashBoardProfileResponse of(Enterprise enterprise) {
        return EnterpriseDashBoardProfileResponse.builder()
                .name(enterprise.getName())
                .expertise(enterprise.getWorkAssessment().getExpertise())
                .scheduleAdherence(enterprise.getWorkAssessment().getScheduleAdherence())
                .initiative(enterprise.getWorkAssessment().getInitiative())
                .communication(enterprise.getWorkAssessment().getCommunication())
                .reEmploymentIntention(enterprise.getWorkAssessment().getReEmploymentIntention())
                .totalActiveScore(enterprise.getWorkAssessment().getTotalActiveScore())
                .enterpriseType((enterprise.getSales() < 30_000_000_000L) ? "중소기업" : "중견기업")
                .idNumber(enterprise.getIdNumber())
                .sales(enterprise.getSales())
                .thumbnail(Optional.ofNullable(enterprise.getEnterpriseThumbnail()).map(EnterpriseThumbnail::getThumbnailPath).orElse(null))
                .build();
    }

}
