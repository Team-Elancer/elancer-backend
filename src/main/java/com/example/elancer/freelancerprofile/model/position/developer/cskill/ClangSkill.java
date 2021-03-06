package com.example.elancer.freelancerprofile.model.position.developer.cskill;

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
public class ClangSkill extends BasicEntity {

    @Enumerated(EnumType.STRING)
    private CDetailSkill cDetailSkill;

    @ManyToOne(fetch = FetchType.LAZY)
    private Developer developer;

    public ClangSkill(CDetailSkill cDetailSkill, Developer developer) {
        this.cDetailSkill = cDetailSkill;
        this.developer = developer;
    }

    public static ClangSkill createCSkill(CDetailSkill cDetailSkill, Developer developer) {
        return new ClangSkill(cDetailSkill, developer);
    }

    public void setDeveloper(Developer developer) {
        this.developer = developer;
    }
}
