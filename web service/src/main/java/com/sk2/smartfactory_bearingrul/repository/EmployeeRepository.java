package com.sk2.smartfactory_bearingrul.repository;

import com.sk2.smartfactory_bearingrul.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, String> {
    boolean existsByEmployeeId(String employeeId);
    boolean existsByEmployeeIdAndEmail(String employeeId, String email);

    Employee findByEmployeeId(String employeeId);
    Employee findByEmployeeIdAndEmail(String employeeId, String email);
    Optional<Employee> findByInCharge(String inCharge);
}