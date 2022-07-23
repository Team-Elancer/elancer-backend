package com.example.elancer.enterprise.model.enterpriseintro;

import com.example.elancer.common.model.BasicEntity;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotNull;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Portfolio extends BasicEntity {

    @NotNull
    private String portfolioPath;

    @OneToOne(fetch = FetchType.LAZY)
    private EnterpriseIntro enterpriseIntro;

    public Portfolio(String portfolioPath, EnterpriseIntro enterpriseIntro) {
        this.portfolioPath = portfolioPath;
        this.enterpriseIntro = enterpriseIntro;
    }

    public void updatePortfolioPath(String portfolioPath) {
        this.portfolioPath = portfolioPath;
    }

    public static Portfolio createPortfolio(String portfolioPath, EnterpriseIntro enterpriseIntro) {
        return new Portfolio(portfolioPath, enterpriseIntro);
    }
}
