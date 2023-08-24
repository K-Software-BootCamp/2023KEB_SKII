package com.sk2.smartfactory_bearingrul.dto;

import com.sk2.smartfactory_bearingrul.entity.Member;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;

@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LoginMemberDto implements UserDetails {

    private Member member;
    private String department;
    private String position;
    private static final String ROLE_PREFIX = "ROLE_";

    public Member toEntity() {
        return Member.builder()
                .employeeId(member.getEmployeeId())
                .memberId(member.getMemberId())
                .password(member.getPassword())
                .createdAt(member.getCreatedAt())
                .build();
    }

    public static LoginMemberDto from(Member entity, String position, String department) {
        return LoginMemberDto.builder()
                .member(entity)
                .position(position)
                .department(department)
                .build();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Collection<GrantedAuthority> authorities = new ArrayList<>();

        if ("관리자".equals(position)) {
            authorities.add(new SimpleGrantedAuthority(ROLE_PREFIX + "ADMIN"));
        } else {
            authorities.add(new SimpleGrantedAuthority(ROLE_PREFIX + "USER"));
        }

        return authorities;
    }

    @Override
    public String getUsername() {
        return member.getMemberId();
    }

    @Override
    public String getPassword() {
        return member.getPassword();
    }

    @Override
    public boolean isAccountNonExpired() {
        return false;
    }

    @Override
    public boolean isAccountNonLocked() {
        return false;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return false;
    }

    @Override
    public boolean isEnabled() {
        return false;
    }
}
