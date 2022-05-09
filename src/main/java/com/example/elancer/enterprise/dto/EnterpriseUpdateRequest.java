package com.example.elancer.enterprise.dto;


import com.example.elancer.member.domain.Address;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Getter @Setter
@AllArgsConstructor
public class EnterpriseUpdateRequest {

    @NotBlank
    private String companyName;

    @NotBlank
    private Integer companyPeople;

    @NotBlank
    private String name;

    private String position;

    @NotBlank
    private String password1;

    @NotBlank
    private String password2;

    @NotBlank
    private String phone;

    @NotBlank
    private String telNumber;

    @NotBlank
    @Email
    private String email;

    private String website;

    private Address address;

    private String bizContents;

    private Integer sales;

    private String idNumber;

    // todo - 이후에 사업자 등록증 파일 구현


}