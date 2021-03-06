package com.example.elancer.freelancerprofile.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class ProfileEnumResponse {
    private List<String> schoolLevelNames;
    private List<String> academicStateNames;
    private List<String> companyPositionNames;
    private List<String> languageAbilityNames;
}
