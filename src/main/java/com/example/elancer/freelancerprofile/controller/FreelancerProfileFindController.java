package com.example.elancer.freelancerprofile.controller;

import com.example.elancer.freelancerprofile.dto.response.FreelancerDetailResponse;
import com.example.elancer.freelancerprofile.service.FreelancerProfileFindService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotNull;

@RestController
@RequiredArgsConstructor
public class FreelancerProfileFindController {
    private final FreelancerProfileFindService freelancerProfileFindService;

    @GetMapping(FreelancerProfileFindControllerPath.FREELANCER_PROFILE_FIND)
    public ResponseEntity<FreelancerDetailResponse> findDetailFreelancerProfile(
            @NotNull @PathVariable Long freelancerNum
    ) {
        FreelancerDetailResponse detailFreelancerProfile = freelancerProfileFindService.findDetailFreelancerProfile(freelancerNum);
        return new ResponseEntity<FreelancerDetailResponse>(detailFreelancerProfile, HttpStatus.OK);
    }
}