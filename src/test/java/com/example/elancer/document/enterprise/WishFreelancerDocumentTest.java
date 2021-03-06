package com.example.elancer.document.enterprise;

import com.example.elancer.common.EnterpriseHelper;
import com.example.elancer.common.FreelancerHelper;
import com.example.elancer.common.LoginHelper;
import com.example.elancer.document.common.DocumentBaseTest;
import com.example.elancer.enterprise.model.enterprise.Enterprise;
import com.example.elancer.freelancer.model.Freelancer;
import com.example.elancer.freelancerprofile.model.FreelancerProfile;
import com.example.elancer.freelancerprofile.model.position.PositionType;
import com.example.elancer.member.dto.MemberLoginResponse;
import com.example.elancer.token.jwt.JwtTokenProvider;
import com.example.elancer.wishfreelancer.dto.WishFreelancerRequest;
import com.example.elancer.wishfreelancer.model.WishFreelancer;
import com.example.elancer.wishfreelancer.repository.WishFreelancerRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

import static org.springframework.restdocs.headers.HeaderDocumentation.headerWithName;
import static org.springframework.restdocs.headers.HeaderDocumentation.requestHeaders;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.requestParameters;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class WishFreelancerDocumentTest extends DocumentBaseTest {

    @Autowired
    private WishFreelancerRepository wishFreelancerRepository;

    @AfterEach
    void tearDown() {
        databaseCleaner.clean();
    }

    @DisplayName("???????????? ?????? ????????? ?????? ????????? ?????????")
    @Test
    public void ????????????_??????_?????????_??????_?????????() throws Exception {

        Enterprise enterprise = EnterpriseHelper.??????_??????(enterpriseRepository, passwordEncoder);
        Freelancer freelancer = FreelancerHelper.????????????_??????(freelancerRepository, passwordEncoder);
        MemberLoginResponse memberLoginResponse = LoginHelper.?????????(enterprise.getUserId(), jwtTokenService);

        freelancerProfileRepository.save(new FreelancerProfile("greeting", freelancer, PositionType.ETC));

        WishFreelancerRequest wishFreelancerRequest = new WishFreelancerRequest(freelancer.getNum());

        mockMvc.perform(post("/wish-freelancer")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .header(JwtTokenProvider.AUTHORITIES_KEY, memberLoginResponse.getAccessToken())
                        .content(objectMapper.writeValueAsString(wishFreelancerRequest)))
                .andExpect(status().isCreated())
                .andDo(print())
                .andDo(document("wish-freelancer-save",
                        requestHeaders(
                                headerWithName(HttpHeaders.CONTENT_TYPE).description("?????? ???????????? ????????????, ?????? ????????? JSON ????????? ??????")
                        ),
                        requestFields(
                                fieldWithPath("freelancerNum").type("Long").description("???????????? ?????????")
                        )
                ));
    }

    @DisplayName("???????????? ?????? ????????? ?????? ????????? ?????????")
    @Test
    public void ????????????_??????_?????????_??????_?????????() throws Exception {

        Enterprise enterprise = EnterpriseHelper.??????_??????(enterpriseRepository, passwordEncoder);
        Freelancer freelancer = FreelancerHelper.????????????_??????(freelancerRepository, passwordEncoder);
        MemberLoginResponse memberLoginResponse = LoginHelper.?????????(enterprise.getUserId(), jwtTokenService);

        freelancerProfileRepository.save(new FreelancerProfile("greeting", freelancer, PositionType.DEVELOPER));

        wishFreelancerRepository.save(WishFreelancer.createWishFreelancer(enterprise, freelancer));


        mockMvc.perform(delete("/wish-freelancer/{freelancerNum}", freelancer.getNum())
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .header(JwtTokenProvider.AUTHORITIES_KEY, memberLoginResponse.getAccessToken()))
                .andExpect(status().isOk())
                .andDo(print())
                .andDo(document("wish-freelancer-delete",
                        requestHeaders(
                                headerWithName(HttpHeaders.CONTENT_TYPE).description("?????? ???????????? ????????????, ?????? ????????? JSON ????????? ??????")
                        )
                ));
    }
}
