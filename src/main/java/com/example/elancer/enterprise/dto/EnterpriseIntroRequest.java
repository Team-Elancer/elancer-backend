package com.example.elancer.enterprise.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter @Setter
@AllArgsConstructor
public class EnterpriseIntroRequest {

    private String introTitle;
    private List<String> mainBizCodes = new ArrayList<>();
    private List<String> subBizCodes = new ArrayList<>();

}
