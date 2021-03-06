package com.example.elancer.freelancerprofile.model.position.designer;

import com.example.elancer.common.model.BasicEntity;
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
public class DesignRole extends BasicEntity {

    @Enumerated(EnumType.STRING)
    private DesignDetailRole designDetailRole;

    @ManyToOne(fetch = FetchType.LAZY)
    private Designer designer;

    public DesignRole(DesignDetailRole designDetailRole, Designer designer) {
        this.designDetailRole = designDetailRole;
        this.designer = designer;
    }

    public static DesignRole createDesignRole(DesignDetailRole designDetailRole, Designer designer) {
        return new DesignRole(designDetailRole, designer);
    }

    public void setDesigner(Designer designer) {
        this.designer = designer;
    }
}
