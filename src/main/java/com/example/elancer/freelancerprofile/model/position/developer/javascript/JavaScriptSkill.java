package com.example.elancer.freelancerprofile.model.position.developer.javascript;

import com.example.elancer.common.model.BasicEntity;
import com.example.elancer.freelancerprofile.model.position.developer.Developer;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class JavaScriptSkill extends BasicEntity {

    @Enumerated(EnumType.STRING)
    private JavaScriptDetailSkill javaScriptDetailSkill;

    @ManyToOne(fetch = FetchType.LAZY)
    private Developer developer;
}
