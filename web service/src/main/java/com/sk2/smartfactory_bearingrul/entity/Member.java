package com.sk2.smartfactory_bearingrul.entity;

import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
@Entity
public class Member {
    @Id
    @Column(length = 100, name = "employee_id")
    private String employeeId;

    @Column(length = 100, name = "member_id", nullable = false)
    private String memberId;

    @Column(length = 100, nullable = false)
    private String password;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    @CreatedDate
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    public static Member createMember(String employeeId, String memberId, String password, String admin, LocalDateTime createdAt) {
        Member member = new Member();
        member.setEmployeeId(employeeId);
        member.setMemberId(memberId);
        member.setPassword(password);
        member.setCreatedAt(createdAt);
        return member;
    }
}