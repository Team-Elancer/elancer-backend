package com.example.elancer.freelancerprofile.model.position;

import com.example.elancer.freelancerprofile.model.position.developer.cskill.COrCPlusplusSkill;
import com.example.elancer.freelancerprofile.model.position.developer.dbskill.DBSkill;
import com.example.elancer.freelancerprofile.model.position.developer.dotnet.DotNetSkill;
import com.example.elancer.freelancerprofile.model.position.developer.javaskill.JavaSkill;
import com.example.elancer.freelancerprofile.model.position.developer.javascript.JavaScriptSkill;
import com.example.elancer.freelancerprofile.model.position.developer.mobileskill.MobileAppSkill;
import com.example.elancer.freelancerprofile.model.position.developer.phpaspskill.PhpOrAspSkill;
import com.sun.istack.NotNull;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.CascadeType;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;


@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@DiscriminatorValue("FREELANCER")
public class Developer extends Position{

    @NotNull
    private String focusSkill;

    @NotNull
    private String role;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "developer", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<JavaSkill> javaSkills = new ArrayList<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "developer", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<MobileAppSkill> mobileAppSkills = new ArrayList<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "developer", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PhpOrAspSkill> phpOrAspSkills = new ArrayList<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "developer", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<DotNetSkill> dotNetSkills = new ArrayList<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "developer", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<JavaScriptSkill> javaScriptSkills = new ArrayList<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "developer", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<COrCPlusplusSkill> cOrCPlusplusSkills = new ArrayList<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "developer", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<DBSkill> dbSkills = new ArrayList<>();

    private String etcSkill;
}
