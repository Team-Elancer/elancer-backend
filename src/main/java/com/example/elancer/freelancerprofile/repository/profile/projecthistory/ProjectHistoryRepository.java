package com.example.elancer.freelancerprofile.repository.profile.projecthistory;

import com.example.elancer.freelancerprofile.model.projecthistory.ProjectHistory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProjectHistoryRepository extends JpaRepository<ProjectHistory, Long> {
}
