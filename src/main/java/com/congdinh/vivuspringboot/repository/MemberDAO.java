package com.congdinh.vivuspringboot.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.congdinh.vivuspringboot.entities.Member;

import java.util.Optional;

public interface  MemberDAO extends JpaRepository<Member, Integer> {
    Member findByEmailAndPassword(String email, String password);
    boolean existsByEmail(String email);
    Optional<Member> findByEmail(String email);
}
