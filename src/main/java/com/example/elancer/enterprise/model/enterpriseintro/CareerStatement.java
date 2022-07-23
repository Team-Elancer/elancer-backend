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
public class CareerStatement extends BasicEntity {

    @NotNull
    private String careerStatementPath;

    @OneToOne(fetch = FetchType.LAZY)
    private EnterpriseIntro enterpriseIntro;

    public CareerStatement(String careerStatementPath, EnterpriseIntro enterpriseIntro) {
        this.careerStatementPath = careerStatementPath;
        this.enterpriseIntro = enterpriseIntro;
    }

    public void updateCareerStatementPath(String careerStatementPath) {
        this.careerStatementPath = careerStatementPath;
    }

    public static CareerStatement createCareerStatement(String careerStatementPath, EnterpriseIntro enterpriseIntro) {
        return new CareerStatement(careerStatementPath, enterpriseIntro);
    }
}
