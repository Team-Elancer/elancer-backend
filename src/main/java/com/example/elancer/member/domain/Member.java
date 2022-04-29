package com.example.elancer.member.domain;

import com.example.elancer.common.model.BasicEntity;
import com.sun.istack.NotNull;
import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(name = "member_type")
public abstract class Member extends BasicEntity {
    @NotNull
    private String userId;

    @NotNull
    private String password;
    @NotNull
    private String name;
    private String phone;
    private String email;


    /**
     * enterprise, freelancer 공통 부분 추가
     * website, address
     */

    private String website;

    @Embedded
    private Address address;

    @Enumerated(EnumType.STRING)
    private MemberType role;

    public Member(String userId, String password, String name, String phone, String email, String website, Address address, MemberType role) {
        this.userId = userId;
        this.password = password;
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.website = website;
        this.address = address;
        this.role = role;
    }

    public void updateMember(
            String name,
            String password,
            String email,
            String phone,
            String website,
            Address address
    ) {
        this.name = name;
        this.password = password;
        this.email = email;
        this.phone = phone;
        this.website = website;
        this.address = address;
    }

    public String getRoleKey() {
        return this.role.getKey();
    }

    public String getRoleType() {
        return this.role.getType();
    }

}
