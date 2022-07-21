package com.example.elancer.freelancer.repository;

import com.example.elancer.freelancer.model.FreelancerThumbnail;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface FreelancerThumbnailRepository extends JpaRepository<FreelancerThumbnail, Long> {

    Optional<FreelancerThumbnail> findByThumbnailPath(String path);

    boolean existsByThumbnailPath(String path);
}
