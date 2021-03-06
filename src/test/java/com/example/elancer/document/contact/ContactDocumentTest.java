package com.example.elancer.document.contact;

import com.example.elancer.common.EnterpriseHelper;
import com.example.elancer.contact.dto.ContactDeleteRequest;
import com.example.elancer.contact.dto.ContactRequest;
import com.example.elancer.contact.dto.ContactSaveRequest;
import com.example.elancer.contact.model.Contact;
import com.example.elancer.contact.repository.ContactRepository;
import com.example.elancer.document.common.DocumentBaseTest;
import com.example.elancer.enterprise.model.enterprise.Enterprise;
import com.example.elancer.integrate.enterprise.EnterpriseLoginHelper;
import com.example.elancer.member.domain.Address;
import com.example.elancer.member.domain.CountryType;
import com.example.elancer.member.dto.MemberLoginResponse;
import com.example.elancer.project.dto.ProjectDeleteRequest;
import com.example.elancer.project.model.*;
import com.example.elancer.token.jwt.JwtTokenProvider;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.restdocs.headers.HeaderDocumentation.*;
import static org.springframework.restdocs.headers.HeaderDocumentation.headerWithName;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class ContactDocumentTest extends DocumentBaseTest {

    @Autowired
    private ContactRepository contactRepository;

    @AfterEach
    void tearDown() {
        databaseCleaner.clean();
    }

    @Test
    @DisplayName("?????? ?????? GET ????????? ?????????")
    public void ??????_??????_GET_?????????() throws Exception {
        Enterprise enterprise = EnterpriseHelper.??????_??????(enterpriseRepository, passwordEncoder);
        MemberLoginResponse memberLoginResponse = EnterpriseLoginHelper.?????????(enterprise.getUserId(), jwtTokenService);

        mockMvc.perform(get("/contact-save")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .header(JwtTokenProvider.AUTHORITIES_KEY, memberLoginResponse.getAccessToken()))
                .andExpect(status().isOk())
                .andDo(print())
                .andDo(document("contact-save-get",
                        requestHeaders(
                                headerWithName(JwtTokenProvider.AUTHORITIES_KEY).description("jwt ?????? ?????? ?????? ??????.")
                        ),
                        responseHeaders(
                                headerWithName(HttpHeaders.CONTENT_TYPE).description("?????? ???????????? ????????????, ?????? ????????? JSON ????????? ??????")
                        ),
                        responseFields(
                                fieldWithPath("name").type("String").description("??????"),
                                fieldWithPath("phone").type("Integer").description("?????????"),
                                fieldWithPath("email").type("String").description("?????????")
                        )
                ));
    }

    @Test
    @DisplayName("?????? ?????? ????????? ?????????")
    public void ??????_??????_?????????() throws Exception {
        Enterprise enterprise = EnterpriseHelper.??????_??????(enterpriseRepository, passwordEncoder);
        MemberLoginResponse memberLoginResponse = EnterpriseLoginHelper.?????????(enterprise.getUserId(), jwtTokenService);

        ContactSaveRequest contactSaveRequest = new ContactSaveRequest(
                "?????? title",
                "?????? ??????"
        );

        mockMvc.perform(post("/contact-save")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .header(JwtTokenProvider.AUTHORITIES_KEY, memberLoginResponse.getAccessToken())
                        .content(objectMapper.writeValueAsString(contactSaveRequest)))
                .andExpectAll(status().isCreated())
                .andDo(print())
                .andDo(document("contact-save",
                        requestHeaders(
                                headerWithName(HttpHeaders.CONTENT_TYPE).description("?????? ???????????? ????????????, ?????? ????????? JSON ????????? ??????"),
                                headerWithName(JwtTokenProvider.AUTHORITIES_KEY).description("jwt ?????? ?????? ?????? ??????.")
                        ),
                        requestFields(
                                fieldWithPath("title").type("String").description("??????"),
                                fieldWithPath("content").type("String").description("??????")
                        )
                ));
    }

    @Test
    @DisplayName("?????? ?????? ?????? ?????????")
    public void ??????_??????_??????_?????????() throws Exception {

        Enterprise enterprise = EnterpriseHelper.??????_??????(enterpriseRepository, passwordEncoder);
        MemberLoginResponse memberLoginResponse = EnterpriseLoginHelper.?????????(enterprise.getUserId(), jwtTokenService);

        Contact contact = new Contact("?????? title", "?????? content");
        contactRepository.save(contact);

        ContactRequest contactRequest = new ContactRequest(contact.getNum(), "????????? ?????? title", "????????? ?????? content");

        mockMvc.perform(put("/contact-cover")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .header(JwtTokenProvider.AUTHORITIES_KEY, memberLoginResponse.getAccessToken())
                        .content(objectMapper.writeValueAsString(contactRequest)))
                .andExpectAll(status().isOk())
                .andDo(print())
                .andDo(document("contact-cover",
                        requestHeaders(
                                headerWithName(HttpHeaders.CONTENT_TYPE).description("?????? ???????????? ????????????, ?????? ????????? JSON ????????? ??????"),
                                headerWithName(JwtTokenProvider.AUTHORITIES_KEY).description("jwt ?????? ?????? ?????? ??????.")
                        ),
                        requestFields(
                                fieldWithPath("num").type("Long").description("?????????"),
                                fieldWithPath("title").type("String").description("??????"),
                                fieldWithPath("content").type("String").description("??????")
                        )
                ));
    }

    @Test
    @DisplayName("?????? ????????? ?????? ????????? ?????????")
    public void ??????_?????????_??????_?????????() throws Exception {
        Enterprise enterprise = EnterpriseHelper.??????_??????(enterpriseRepository, passwordEncoder);
        MemberLoginResponse memberLoginResponse = EnterpriseLoginHelper.?????????(enterprise.getUserId(), jwtTokenService);

        Contact contact1 = new Contact("?????? title1", "?????? content1");
        Contact contact2 = new Contact("?????? title2", "?????? content2");
        Contact contact3 = new Contact("?????? title3", "?????? content3");

        contact1.setMember(enterprise);
        contact2.setMember(enterprise);
        contact3.setMember(enterprise);

        List<Contact> contacts = List.of(contact1, contact2, contact3);

        contactRepository.saveAll(contacts);

        mockMvc.perform(get("/contacts")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .header(JwtTokenProvider.AUTHORITIES_KEY, memberLoginResponse.getAccessToken()))
                .andExpect(status().isOk())
                .andDo(print())
                .andDo(document("contacts",
                        requestHeaders(
                                headerWithName(JwtTokenProvider.AUTHORITIES_KEY).description("jwt ?????? ?????? ?????? ??????.")
                        ),
                        responseHeaders(
                                headerWithName(HttpHeaders.CONTENT_TYPE).description("?????? ???????????? ????????????, ?????? ????????? JSON ????????? ??????")
                        ),
                        responseFields(
                                fieldWithPath("[]").type("List<ContactResponse>").description("?????? ?????????"),
                                fieldWithPath("[].num").type("Long").description("?????? ?????????"),
                                fieldWithPath("[].title").type("String").description("?????? ??????"),
                                fieldWithPath("[].content").type("String").description("?????? ??????"),
                                fieldWithPath("[].localDate").type("LocalDate").description("?????? ??????")
                        )
                ));
    }

    @Test
    @DisplayName("?????? ?????? ????????? ?????????")
    public void ??????_??????_?????????() throws Exception{
        Enterprise enterprise = EnterpriseHelper.??????_??????(enterpriseRepository, passwordEncoder);
        MemberLoginResponse memberLoginResponse = EnterpriseLoginHelper.?????????(enterprise.getUserId(), jwtTokenService);

        Contact contact = contactRepository.save(new Contact(
                "title",
                "content"
        ));

        ContactDeleteRequest contactDeleteRequest = new ContactDeleteRequest(
                contact.getNum()
        );


        mockMvc.perform(delete("/contact-delete")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .header(JwtTokenProvider.AUTHORITIES_KEY, memberLoginResponse.getAccessToken())
                        .content(objectMapper.writeValueAsString(contactDeleteRequest)))
                .andExpect(status().isOk())
                .andDo(print())
                .andDo(document("contact-delete",
                        requestHeaders(
                                headerWithName(HttpHeaders.CONTENT_TYPE).description("?????? ???????????? ????????????, ?????? ????????? JSON ????????? ??????"),
                                headerWithName(JwtTokenProvider.AUTHORITIES_KEY).description("jwt ?????? ?????? ?????? ??????.")
                        ),
                        requestHeaders(
                                headerWithName(HttpHeaders.CONTENT_TYPE).description("?????? ???????????? ????????????, ?????? ????????? JSON ????????? ??????")
                        ),
                        requestFields(
                                fieldWithPath("contactNum").type("Long").description("???????????? ?????????")
                        )

                ));
    }
}
