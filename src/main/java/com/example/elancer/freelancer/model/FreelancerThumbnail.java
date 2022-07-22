package com.example.elancer.freelancer.model;

import com.example.elancer.common.model.BasicEntity;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Service;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class FreelancerThumbnail extends BasicEntity {
    @NotNull
    @Size(max = 1500)
    private String thumbnailPath;

    @OneToOne(fetch = FetchType.LAZY)
    private Freelancer freelancer;

    public FreelancerThumbnail(String thumbnailPath, Freelancer freelancer) {
        this.thumbnailPath = thumbnailPath;
        this.freelancer = freelancer;
    }

    public static FreelancerThumbnail createFreelancerThumbnail(String thumbnailPath, Freelancer freelancer) {
        return new FreelancerThumbnail(thumbnailPath, freelancer);
    }

}
