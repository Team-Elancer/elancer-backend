package com.example.elancer.applyproject.service;

import com.example.elancer.applyproject.dto.ApplyProjectCreateRequest;
import com.example.elancer.applyproject.exception.DuplicationApplyProjectException;
import com.example.elancer.applyproject.model.ApplyProject;
import com.example.elancer.applyproject.repository.ApplyProjectRepository;
import com.example.elancer.freelancer.exception.NotExistFreelancerException;
import com.example.elancer.freelancer.model.Freelancer;
import com.example.elancer.freelancer.repository.FreelancerRepository;
import com.example.elancer.login.auth.dto.MemberDetails;
import com.example.elancer.project.model.Project;
import com.example.elancer.project.repository.ProjectRepository;
import com.example.elancer.wishprojects.exception.NotExistProjectException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ApplyProjectService {

    private final ApplyProjectRepository applyProjectRepository;
    private final ProjectRepository projectRepository;
    private final FreelancerRepository freelancerRepository;

    @Transactional
    public void createApplyProject(ApplyProjectCreateRequest applyProjectCreateRequest, MemberDetails memberDetails) {
        Optional<ApplyProject> byFreelancerNumAndProjectNum = applyProjectRepository.findByFreelancerNumAndProjectNum(memberDetails.getId(), applyProjectCreateRequest.getProjectNum());
        if (byFreelancerNumAndProjectNum.isPresent()) {
            throw new DuplicationApplyProjectException();
        }

        Freelancer freelancer = freelancerRepository.findById(memberDetails.getId()).orElseThrow(NotExistFreelancerException::new);
        Project project = projectRepository.findById(applyProjectCreateRequest.getProjectNum()).orElseThrow(NotExistProjectException::new);
        applyProjectRepository.save(ApplyProject.createApplyProject(freelancer, project));
    }
}
