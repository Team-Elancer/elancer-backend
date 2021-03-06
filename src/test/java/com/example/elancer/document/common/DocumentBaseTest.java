package com.example.elancer.document.common;

import com.example.elancer.common.database.DatabaseCleaner;
import com.example.elancer.enterprise.repository.EnterpriseRepository;
import com.example.elancer.freelancer.repository.FreelancerRepository;
import com.example.elancer.freelancerprofile.repository.FreelancerProfileRepository;
import com.example.elancer.testconfig.RestDocsConfiguration;
import com.example.elancer.token.service.JwtTokenService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("h2")
@AutoConfigureMockMvc
@AutoConfigureRestDocs
@Import(RestDocsConfiguration.class)
public class DocumentBaseTest {

    @Autowired
    protected ObjectMapper objectMapper;

    @Autowired
    protected MockMvc mockMvc;

    @Autowired
    protected DatabaseCleaner databaseCleaner;

    @Autowired
    protected JwtTokenService jwtTokenService;

    @Autowired
    protected FreelancerRepository freelancerRepository;

    @Autowired
    protected FreelancerProfileRepository freelancerProfileRepository;

    @Autowired
    protected EnterpriseRepository enterpriseRepository;

    @Autowired
    protected PasswordEncoder passwordEncoder;

}
