package com.example.elancer.enterprise.dto;

import lombok.*;

import javax.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.List;

@Getter @Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class EnterpriseProfileRequest {

    private String introTitle;

    @NotBlank
    private String bizContents;

    private Long sales;
    @NotBlank
    private String idNumber;

    private List<String> mainBizCodes = new ArrayList<>();
    private String mainEtc;

    private List<String> subBizCodes = new ArrayList<>();
    private String subEtc;

    private String bizRegistration;

    private String careerStatementPath;
    private String portfolioPath;

}
