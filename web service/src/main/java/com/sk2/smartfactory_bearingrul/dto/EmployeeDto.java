package com.sk2.smartfactory_bearingrul.dto;

import com.sk2.smartfactory_bearingrul.entity.Employee;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeDto {
    private String employeeId;
    private String name;
    private LocalDate birthday;
    private String gender;
    private String phone;
    private String email;
    private String department;
    private String position;
    private String inCharge;

    public static EmployeeDto from(Employee entity) {
        return EmployeeDto.builder()
                .employeeId(entity.getEmployeeId())
                .name(entity.getName())
                .birthday(entity.getBirthday())
                .gender(entity.getGender())
                .phone(entity.getPhone())
                .email(entity.getEmail())
                .department(entity.getDepartment())
                .position(entity.getPosition())
                .inCharge(entity.getInCharge())
                .build();
    }

    public Employee toEntity() {
        return Employee.builder()
                .employeeId(employeeId)
                .name(name)
                .birthday(birthday)
                .gender(gender)
                .phone(phone)
                .email(email)
                .department(department)
                .position(position)
                .inCharge(inCharge)
                .build();
    }

    public void updateFields(EmployeeDto newEmployeeDto) {
        if (newEmployeeDto.getPhone() != null) {
            this.phone = newEmployeeDto.getPhone();
        }
        if (newEmployeeDto.getDepartment() != null) {
            this.department = newEmployeeDto.getDepartment();
        }
        if (newEmployeeDto.getPosition() != null) {
            this.position = newEmployeeDto.getPosition();
        }
        if (newEmployeeDto.getInCharge() != null) {
            this.inCharge = newEmployeeDto.getInCharge();
        }
    }
}