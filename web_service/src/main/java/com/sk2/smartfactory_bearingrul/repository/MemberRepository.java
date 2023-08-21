package com.sk2.smartfactory_bearingrul.repository;

import com.sk2.smartfactory_bearingrul.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MemberRepository extends JpaRepository<Member, String> {

    // employeeId를 통해 Member 존재 여부를 반환하는 메서드
    boolean existsByEmployeeId(String employeeId);

    // memberId를 통해 Member 존재 여부를 반환하는 메서드
    boolean existsByMemberId(String memberId);

    Optional<Member> findByEmployeeId(String employeeId);

    Optional<Member> findByMemberId(String memberId);
}