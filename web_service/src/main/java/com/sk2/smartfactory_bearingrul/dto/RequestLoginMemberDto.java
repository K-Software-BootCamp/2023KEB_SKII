package com.sk2.smartfactory_bearingrul.dto;

import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class RequestLoginMemberDto {
    private String memberId;
    private String password;
}
