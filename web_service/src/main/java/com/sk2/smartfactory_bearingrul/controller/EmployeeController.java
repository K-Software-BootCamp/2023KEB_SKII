package com.sk2.smartfactory_bearingrul.controller;

import com.sk2.smartfactory_bearingrul.dto.EmployeeDto;
import com.sk2.smartfactory_bearingrul.dto.RequestSignupMemberDto;
import com.sk2.smartfactory_bearingrul.service.EmployeeService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/employees")
public class EmployeeController {

    private final EmployeeService employeeService;

    @ApiOperation(value = "사원 목록 조회", notes = "테이블의 모든 사원 정보를 조회합니다.")
    @GetMapping
    public ResponseEntity<List<EmployeeDto>> getAllEmployees() {
        List<EmployeeDto> employees = employeeService.getAllEmployees();
        return new ResponseEntity<>(employees, HttpStatus.OK);
    }

    @ApiOperation(value = "사원 생성 - 사원 확인", notes = "사원을 생성할 때 사원의 정보가 존재하는지 확인합니다.")
    @PostMapping("/register/check-employee")
    public ResponseEntity<Boolean> checkRegistration(@RequestBody EmployeeDto employeeDto) {
        return ResponseEntity.ok().body(employeeService.checkRegistration(employeeDto));
    }

    @ApiOperation(value = "사원 생성", notes = "사원을 생성합니다.")
    @PostMapping("/register")
    public ResponseEntity<EmployeeDto> registerEmployee(@RequestBody EmployeeDto employeeDto) {
        employeeService.registerEmployee(employeeDto);
        return ResponseEntity.status(201).build();
    }

    @ApiOperation(value = "사원 수정", notes = "사원의 연락처, 부서, 직급, 담당을 수정합니다.")
    @PutMapping("/{employeeId}")
    public ResponseEntity<Void> updateEmployee(@PathVariable String employeeId, @RequestBody EmployeeDto employeeDto) {
        employeeService.updateEmployee(employeeId, employeeDto);
        return ResponseEntity.ok().build();
    }

    @ApiOperation(value = "사원 삭제", notes = "입력 받은 사원 아이디에 해당하는 사원을 삭제합니다.")
    @DeleteMapping("/{employeeId}")
    public ResponseEntity<Void> deleteEmployee(@PathVariable String employeeId) {
        employeeService.deleteEmployee(employeeId);
        return ResponseEntity.ok().build();
    }

    @ApiOperation(value = "사원 정보 조회", notes = "입력 받은 사원 아이디에 해당하는 사원의 정보를 조회합니다.")
    @GetMapping("/{memberId}")
    public ResponseEntity<EmployeeDto> getEmployeeInfo(@PathVariable String memberId) {
        return ResponseEntity.ok().body(employeeService.getEmployeeInfo(memberId));
    }

    @ApiOperation(value = "담당 사원 정보 조회", notes = "입력 받은 table을 담당하는 사원의 정보를 조회합니다.")
    @GetMapping("/inCharge/{table}")
    public ResponseEntity<EmployeeDto> getEmployeeInCharge(@PathVariable String table) {
        return ResponseEntity.ok().body(employeeService.getEmployeeInCharge(table));
    }
}