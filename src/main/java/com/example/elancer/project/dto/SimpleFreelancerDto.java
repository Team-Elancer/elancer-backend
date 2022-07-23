package com.example.elancer.project.dto;

import com.example.elancer.freelancer.model.Freelancer;
import com.example.elancer.freelancer.model.FreelancerThumbnail;
import com.example.elancer.project.model.EnterpriseLogo;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Optional;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SimpleFreelancerDto {
    private String thumbnailUrl;
    private String username;

    public SimpleFreelancerDto(String thumbnailUrl, String username) {
        this.thumbnailUrl = thumbnailUrl;
        this.username = username;
    }

    public static SimpleFreelancerDto of(Freelancer freelancer) {
        return new SimpleFreelancerDto(
                Optional.ofNullable(freelancer.getFreelancerThumbnail()).map(FreelancerThumbnail::getThumbnailPath).orElse(null),
                freelancer.getName()
        );
    }
}
