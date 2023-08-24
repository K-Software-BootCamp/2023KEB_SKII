package com.sk2.smartfactory_bearingrul.service;

import com.sk2.smartfactory_bearingrul.dto.EmployeeDto;
import com.sk2.smartfactory_bearingrul.entity.Employee;
import com.sk2.smartfactory_bearingrul.entity.Member;
import com.sk2.smartfactory_bearingrul.repository.EmployeeRepository;
import com.sk2.smartfactory_bearingrul.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class EmployeeService {

    private final EmployeeRepository employeeRepository;
    private final MemberRepository memberRepository;

    public List<EmployeeDto> getAllEmployees() {
        List<Employee> employees = employeeRepository.findAll();
        return employees.stream().map(EmployeeDto::from).collect(Collectors.toList());
    }

    public boolean checkRegistration(EmployeeDto employeeDto) {
        return employeeRepository.existsByEmployeeId(employeeDto.getEmployeeId());
    }

    public void registerEmployee(EmployeeDto employeeDto) {
        if (employeeRepository.existsByEmployeeId(employeeDto.getEmployeeId())) {
            throw new IllegalStateException("중복된 사원번호입니다.");
        }

        employeeRepository.save(employeeDto.toEntity());
    }

    public void updateEmployee(String employeeId, EmployeeDto updateEmployeeDto) {
        Employee employee = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 사원입니다."));

        EmployeeDto employeeDto = EmployeeDto.from(employee);
        employeeDto.updateFields(updateEmployeeDto);

        employeeRepository.save(employeeDto.toEntity());
    }

    public void deleteEmployee(String employeeId) {
        Employee employee = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 사원입니다."));

        employeeRepository.delete(employee);

        // 해당 사원번호로 가입한 member가 있으면 삭제
        Optional<Member> member = memberRepository.findByEmployeeId(employeeId);
        member.ifPresent(memberRepository::delete);
    }

    public EmployeeDto getEmployeeInfo(String memberId) {
        Member member = memberRepository.findByMemberId(memberId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 회원입니다."));
        Employee employee = employeeRepository.findByEmployeeId(member.getEmployeeId());

        return EmployeeDto.from(employee);
    }

    public EmployeeDto getEmployeeInCharge(String table) {
        Employee employee = employeeRepository.findByInCharge(table)
                .orElseThrow(() -> new IllegalArgumentException("담당하는 회원이 존재하지 않습니다."));

        return EmployeeDto.from(employee);
    }
}