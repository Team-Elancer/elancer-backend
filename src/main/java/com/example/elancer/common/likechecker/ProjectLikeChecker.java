package com.example.elancer.common.likechecker;

import com.example.elancer.login.auth.dto.MemberDetails;
import com.example.elancer.member.domain.MemberType;
import com.example.elancer.project.dto.ProjectBoxResponse;
import com.example.elancer.project.model.Project;
import com.example.elancer.wishprojects.model.WishProject;
import com.example.elancer.wishprojects.repository.WishProjectRepository;

import java.util.List;
import java.util.stream.Collectors;

public class ProjectLikeChecker {

    public static void confirmWishProjectToRequest(MemberDetails memberDetails, List<ProjectBoxResponse> projectBoxResponseList, WishProjectRepository wishProjectRepository) {
        if (memberDetails != null && memberDetails.getRole().equals(MemberType.FREELANCER)) {
            List<WishProject> wishProjectByFreelancer = wishProjectRepository.findByFreelancerNum(memberDetails.getId());
            List<Long> wishProjectNums = wishProjectByFreelancer.stream()
                    .map(WishProject::getProject)
                    .map(Project::getNum)
                    .collect(Collectors.toList());
            checkSearchResultInWishProject(projectBoxResponseList, wishProjectNums);

        }
    }

    private static void checkSearchResultInWishProject(List<ProjectBoxResponse> projectBoxResponseList, List<Long> wishProjectNums) {
        for (ProjectBoxResponse projectBoxResponse : projectBoxResponseList) {
            if (wishProjectNums.contains(projectBoxResponse.getProjectNum())) {
                projectBoxResponse.switchWishState();
            }
        }
    }
}
