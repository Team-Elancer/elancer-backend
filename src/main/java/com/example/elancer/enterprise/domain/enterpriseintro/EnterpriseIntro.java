package com.example.elancer.enterprise.domain.enterpriseintro;

import com.example.elancer.enterprise.domain.enterprise.Enterprise;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "enterprise_intro")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class EnterpriseIntro {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "enDetails_id")
    private Long id;

    private String introTitle;

    @OneToMany(mappedBy = "enterpriseIntro", cascade = CascadeType.ALL)
    private List<EnterpriseMainBiz> enterpriseMainBizs = new ArrayList<>();

    @OneToMany(mappedBy = "enterpriseIntro", cascade = CascadeType.ALL)
    private List<EnterpriseSubBiz> enterpriseSubBizs = new ArrayList<>();

    @OneToOne(mappedBy = "enterpriseIntro", cascade = CascadeType.ALL)
    private Enterprise enterprise;



    // TODO 파일 저장 테이블이 정해지면 포트폴리오 및 기타문서 파일 구현

    @Builder
    public EnterpriseIntro(Long id, String introTitle, Enterprise enterprise) {
        this.id = id;
        this.introTitle = introTitle;
        this.enterprise = enterprise;
    }

    public static EnterpriseIntro of(String introTitle, List<EnterpriseMainBiz> enterpriseMainBizs, List<EnterpriseSubBiz> enterpriseSubBizs, Enterprise enterprise) {
        EnterpriseIntro enterpriseIntro = EnterpriseIntro.builder()
                .introTitle(introTitle)
                .enterprise(enterprise)
                .build();
        for (EnterpriseMainBiz enterpriseMainBiz : enterpriseMainBizs) {
            enterpriseIntro.addEnterpriseMainBiz(enterpriseMainBiz);
        }
        for (EnterpriseSubBiz enterpriseSubBiz : enterpriseSubBizs) {
            enterpriseIntro.addEnterpriseSubBiz(enterpriseSubBiz);
        }
        return enterpriseIntro;
    }

    public void addEnterpriseMainBiz(EnterpriseMainBiz enterpriseMainBiz) {
        enterpriseMainBizs.add(enterpriseMainBiz);
        enterpriseMainBiz.setEnterpriseIntro(this);
    }

    public void addEnterpriseSubBiz(EnterpriseSubBiz enterpriseSubBiz) {
        enterpriseSubBizs.add(enterpriseSubBiz);
        enterpriseSubBiz.setEnterpriseIntro(this);
    }
}