package com.sk2.smartfactory_bearingrul.dto;

import com.sk2.smartfactory_bearingrul.entity.Member;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MemberDto {
    private String employeeId;
    private String memberId;
    private String password;
    private LocalDateTime createdAt;

    public static MemberDto from(Member entity) {
        return MemberDto.builder()
                .employeeId(entity.getEmployeeId())
                .memberId(entity.getMemberId())
                .password(entity.getPassword())
                .createdAt(entity.getCreatedAt())
                .build();
    }

    public Member toEntity() {
        return Member.builder()
                .employeeId(employeeId)
                .memberId(memberId)
                .password(password)
                .createdAt(createdAt)
                .build();
    }
}