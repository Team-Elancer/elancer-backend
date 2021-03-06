package com.example.elancer.freelancerprofile.dto.request.position;

import com.example.elancer.common.validatemessages.PositionRequestMessages;
import com.example.elancer.freelancerprofile.model.position.publisher.Publisher;
import com.example.elancer.freelancerprofile.model.position.publisher.PublishingDetailSkill;
import com.example.elancer.freelancerprofile.model.position.publisher.PublishingSkill;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class PublisherCoverRequest {
    @NotNull(message = PositionRequestMessages.PUBLISHER_SKILLS_NULL_MESSAGE)
    private List<PublishingDetailSkill> publishingDetailSkills;
    private String etcSkill;

    public List<PublishingSkill> toPublishingSkill(Publisher publisher) {
        if (this.publishingDetailSkills == null) {
            return new ArrayList<>();
        }

        return this.publishingDetailSkills.stream()
                .map(publishingDetailSkill -> PublishingSkill.createPublishingSkill(publishingDetailSkill, publisher))
                .collect(Collectors.toList());
    }
}
