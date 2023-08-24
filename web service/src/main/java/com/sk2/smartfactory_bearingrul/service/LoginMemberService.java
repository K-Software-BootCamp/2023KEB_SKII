package com.sk2.smartfactory_bearingrul.service;

import com.sk2.smartfactory_bearingrul.dto.LoginMemberDto;
import com.sk2.smartfactory_bearingrul.entity.Employee;
import com.sk2.smartfactory_bearingrul.entity.Member;
import com.sk2.smartfactory_bearingrul.repository.EmployeeRepository;
import com.sk2.smartfactory_bearingrul.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LoginMemberService implements UserDetailsService {

    private final MemberRepository memberRepository;
    private final EmployeeRepository employeeRepository;

    @Override
    public LoginMemberDto loadUserByUsername(String memberId) throws UsernameNotFoundException {
        // 토큰을 통해 member를 읽고 UserDetail을 implement한 LoginMemberDto를 반환
        Member member = memberRepository.findByMemberId(memberId)
                .orElseThrow(() -> new IllegalArgumentException("일치하는 회원이 없습니다."));

        // LoginMemberDto에 position을 넣어 권한 설정
        Employee employee = employeeRepository.findById(member.getEmployeeId())
                .orElseThrow(() -> new IllegalArgumentException("일치하는 회원이 없습니다."));

        return LoginMemberDto.from(member, employee.getPosition(), employee.getDepartment());
    }
}
