package com.example.elancer.enterprise.domain;

import lombok.*;

import javax.persistence.*;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@Table(name = "enterprise_mainbiz")
@Getter @Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class EnterpriseMainBiz {

    @Id @GeneratedValue
    @Column(name = "enterprise_mainbiz_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "enDetails_id")
    private EnterpriseIntro enterpriseIntro;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "main_code")
    private MainBusiness mainBusiness;

    private String etc;

    @Builder
    public EnterpriseMainBiz(Long id, EnterpriseIntro enterpriseIntro, MainBusiness mainBusiness, String etc) {
        this.id = id;
        this.enterpriseIntro = enterpriseIntro;
        this.mainBusiness = mainBusiness;
        this.etc = etc;
    }

    public static List<EnterpriseMainBiz> createList(List<MainBusiness> mainBusinesses, String etc) {
        return mainBusinesses.stream().map((s) ->
                        EnterpriseMainBiz.builder()
                                .mainBusiness(s)
                                .build())
                .collect(Collectors.toList());

    }

}