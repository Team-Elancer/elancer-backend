package com.example.elancer.enterprise.domain;

import lombok.*;

import javax.persistence.*;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@Table(name = "enterprise_subbiz")
@Getter @Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class EnterpriseSubBiz {

    @Id @GeneratedValue
    @Column(name = "enterprise_subbiz_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "enDetails_id")
    private EnterpriseIntro enterpriseIntro;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sub_code")
    private SubBusiness subBusiness;

    private String etc;

    @Builder
    public EnterpriseSubBiz(Long id, EnterpriseIntro enterpriseIntro, SubBusiness subBusiness, String etc) {
        this.id = id;
        this.enterpriseIntro = enterpriseIntro;
        this.subBusiness = subBusiness;
        this.etc = etc;
    }

    public static List<EnterpriseSubBiz> createList(List<SubBusiness> subBusinesses, String etc) {
        return subBusinesses.stream().map((s) ->
                        EnterpriseSubBiz.builder()
                                .subBusiness(s)
                                .build())
                .collect(Collectors.toList());

    }
}