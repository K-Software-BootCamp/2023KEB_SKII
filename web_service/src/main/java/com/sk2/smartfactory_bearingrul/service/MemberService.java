package com.sk2.smartfactory_bearingrul.service;

import com.sk2.smartfactory_bearingrul.dto.LoginMemberDto;
import com.sk2.smartfactory_bearingrul.dto.RequestLoginMemberDto;
import com.sk2.smartfactory_bearingrul.dto.RequestSignupMemberDto;
import com.sk2.smartfactory_bearingrul.entity.Employee;
import com.sk2.smartfactory_bearingrul.entity.Member;
import com.sk2.smartfactory_bearingrul.repository.EmployeeRepository;
import com.sk2.smartfactory_bearingrul.repository.MemberRepository;
import com.sk2.smartfactory_bearingrul.util.jwt.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final EmployeeRepository employeeRepository;
    private final MemberRepository memberRepository;
    private final JwtTokenProvider jwtTokenProvider;
    private final BCryptPasswordEncoder passwordEncoder;
    private final RedisTemplate redisTemplate;

    @Transactional
    public String login(RequestLoginMemberDto requestLogin) {
        // 로그인 인증
        Member member = memberRepository.findByMemberId(requestLogin.getMemberId())
                .orElseThrow(() -> new IllegalArgumentException("사원을 찾을 수 없습니다."));
        if (!passwordEncoder.matches(requestLogin.getPassword(), member.getPassword()))
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");

        // position 가져오기 위한 employee
        Employee employee = employeeRepository.findByEmployeeId(member.getEmployeeId());

        // 토큰 생성
        String token = jwtTokenProvider.createToken(member.getMemberId(), employee.getPosition());

        // 레디스에 토큰 저장
        redisTemplate.opsForValue().set("JWT_TOKEN:" + requestLogin.getMemberId(), token);

        return token;
    }

    @Transactional
    public void logout() {
        LoginMemberDto loginMember = LoginMemberDto.builder()
                .member((Member) SecurityContextHolder.getContext().getAuthentication().getPrincipal())
                .build();
        
        if (redisTemplate.opsForValue().get("JWT_TOKEN:" + loginMember.getUsername()) != null) {
            redisTemplate.delete("JWT_TOKEN:" + loginMember.getUsername());
        }
    }

    public void registerMember(RequestSignupMemberDto requestSignup) {
        // 주어진 employeeId와 email로 Employee 테이블에서 확인
        Employee employee = employeeRepository.findByEmployeeIdAndEmail(
                requestSignup.getEmployeeId(), requestSignup.getEmail());
        if (employee != null) {
            // 새 Member 객체를 생성
            Member member = Member.builder()
                    .employeeId(requestSignup.getEmployeeId())
                    .memberId(requestSignup.getMemberId())
                    .password(passwordEncoder.encode(requestSignup.getPassword()))
                    .build();

            // member를 Member 테이블에 저장
            memberRepository.save(member);
        } else {
            // Employee 테이블에서 employeeId와 email이 일치하지 않는 경우 처리
            throw new IllegalArgumentException("사원을 찾을 수 없습니다.");
        }
    }

    public boolean checkAccess(HttpServletRequest request) {
        String token = jwtTokenProvider.resolveToken(request);

        if (token != null && jwtTokenProvider.validateToken(token)) {
            String storedToken = (String) redisTemplate.opsForValue().get("JWT_TOKEN:" + jwtTokenProvider.getMemberId(token));
            return storedToken  != null && storedToken.equals(token);
        }

        return false;
    }

    public boolean checkRegistration(String employeeId, String email) {
        return employeeRepository.existsByEmployeeIdAndEmail(employeeId, email);
    }

    public boolean isDuplicated(String memberId) {
        return memberRepository.existsByMemberId(memberId);
    }

    public boolean isExisted(String employeeId) {
        return memberRepository.existsByEmployeeId(employeeId);
    }
}
