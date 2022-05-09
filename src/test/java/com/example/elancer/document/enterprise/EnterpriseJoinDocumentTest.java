//package com.example.elancer.document.enterprise;
//
//import com.example.elancer.common.utils.EnterpriseHelper;
//import com.example.elancer.document.common.DocumentBaseTest;
//import com.example.elancer.enterprise.domain.enterprise.Enterprise;
//import com.example.elancer.enterprise.dto.EnterpriseJoinRequest;
//import com.example.elancer.member.domain.Address;
//import com.example.elancer.member.domain.CountryType;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.HttpHeaders;
//import org.springframework.http.MediaType;
//import org.springframework.test.web.servlet.MockMvc;
//
//import static org.springframework.restdocs.headers.HeaderDocumentation.headerWithName;
//import static org.springframework.restdocs.headers.HeaderDocumentation.requestHeaders;
//import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
//import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
//import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
//import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
//
//public class EnterpriseJoinDocumentTest extends DocumentBaseTest {
//
//    @Autowired
//    protected ObjectMapper objectMapper;
//
//    @Autowired
//    protected MockMvc mockMvc;
//
//    @Test
//    @DisplayName("기업 회원가입 문서화 테스트")
//    public void 기업_회원가입_문서화() throws Exception {
//        EnterpriseJoinRequest enterpriseJoinRequest = new EnterpriseJoinRequest(
//                "joinDocsEnterprise",
//                "1234",
//                "1234",
//                "name",
//                "01000000000",
//                "test@gmail.com",
//                "test company",
//                10,
//                "사장",
//                "01011111111",
//                "www.test.com",
//                new Address(CountryType.KR, "123", "주소1", "주소2"),
//                "주요 사업",
//                10000000,
//                "사업자 번호(123-123-123)"
//        );
//
//        mockMvc.perform(post("/enterprise")
//                        .contentType(MediaType.APPLICATION_JSON_VALUE)
//                        .content(objectMapper.writeValueAsString(enterpriseJoinRequest)))
//                .andExpectAll(status().isCreated())
//                .andDo(print())
//                .andDo(document("enterprise-join",
//                        requestHeaders(
//                                headerWithName(HttpHeaders.CONTENT_TYPE).description("요청 데이터의 타입필드, 요청 객체는 JSON 형태로 요청")
//                        ),
//                        requestFields(
//                                fieldWithPath("userId").type("String").description("회원 아이디 필드"),
//                                fieldWithPath("password1").type("String").description("회원 비밀번호 필드"),
//                                fieldWithPath("password2").type("String").description("회원 비밀번호 확인 필드"),
//                                fieldWithPath("name").type("String").description("회원 담당자 성명 필드"),
//                                fieldWithPath("phone").type("String").description("회원 회사 전화번호 필드"),
//                                fieldWithPath("email").type("String").description("회원 이메일 필드"),
//                                fieldWithPath("companyName").type("String").description("회원 회사명 필드"),
//                                fieldWithPath("companyPeople").type("Integer").description("회원 회사 인원수 필드"),
//                                fieldWithPath("position").type("String").description("회원 담당자 직책 필드"),
//                                fieldWithPath("telNumber").type("String").description("회원 담당자 휴대폰 필드"),
//                                fieldWithPath("website").type("String").description("회원 웹사이트 필드"),
//                                fieldWithPath("address.country").type("CountryType.STRING").description("회원 주소 국적 필드"),
//                                fieldWithPath("address.zipcode").type("String").description("회원 우편번호 필드"),
//                                fieldWithPath("address.mainAddress").type("String").description("회원 주소 필드"),
//                                fieldWithPath("address.detailAddress").type("String").description("회원 상세 주소 필드"),
//                                fieldWithPath("bizContents").type("String").description("회원 주요 사업내용 필드"),
//                                fieldWithPath("sales").type("Integer").description("회원 연간 매출액 필드"),
//                                fieldWithPath("idNumber").type("String").description("회원 사업자 번호 필드")
//                        )
//                ));
//    }
//
//    @Test
//    @DisplayName("기업 계정 정보 수정 문서화 테스트")
//    public void 기업_계정정보_수정_문서화() throws Exception {
//
//        Enterprise enterprise = EnterpriseHelper.기업_생성(enterpriseRepository);
//
//        EnterpriseJoinRequest enterpriseJoinRequest = new EnterpriseJoinRequest(
//                "joinDocsEnterprise",
//                "1234",
//                "1234",
//                "name",
//                "01000000000",
//                "test@gmail.com",
//                "test company",
//                10,
//                "사장",
//                "01011111111",
//                "www.test.com",
//                new Address(CountryType.KR, "123", "주소1", "주소2"),
//                "주요 사업",
//                10000000,
//                "사업자 번호(123-123-123)"
//        );
//    }
//}