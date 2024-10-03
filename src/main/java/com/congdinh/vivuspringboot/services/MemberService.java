package com.congdinh.vivuspringboot.services;

import java.util.List;

import com.congdinh.vivuspringboot.entities.Member;

public interface  MemberService {
    void save(Member member);
    void update(Member member);
    void delete(Integer id);
    List<Member> getAll();
    Member getById(Integer id);
     Member login(String email, String password) ;
    boolean existsByEmail(String email);

}
