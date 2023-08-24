package com.sk2.smartfactory_bearingrul.entity;

import javax.persistence.*;

import lombok.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDate;

@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
@Entity
public class Employee {
    @Id
    @Column(length = 100, name = "employee_id")
    private String employeeId;

    @Column(length = 100, nullable = false)
    private String name;

    @Column(columnDefinition = "date", length = 100, nullable = false)
    private LocalDate birthday;

    @Column(length = 1, nullable = false)
    private String gender;

    @Column(length = 100, nullable = false)
    private String phone;

    @Column(length = 100, nullable = false)
    private String email;

    @Column(length = 100, nullable = false)
    private String department;

    @Column(length = 100, nullable = false)
    private String position;

    @Column(length = 100, nullable = true)
    private String inCharge;
}