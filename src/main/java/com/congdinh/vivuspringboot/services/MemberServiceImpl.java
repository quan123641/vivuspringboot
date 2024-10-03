package com.congdinh.vivuspringboot.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.congdinh.vivuspringboot.entities.Member;
import com.congdinh.vivuspringboot.repository.MemberDAO;

@Service
public class MemberServiceImpl implements MemberService{
  
    @Autowired
    private MemberDAO memberDAO;
    public void save(Member member) {
        memberDAO.save(member);
    }
    public void update(Member member) {
        memberDAO.save(member);
    }
    public void delete(Integer id) {
        memberDAO.deleteById(id);
    }
    @Override
    public Member getById(Integer id) {
        Optional<Member> member = memberDAO.findById(id);
        return member.orElse(null);  // Trả về null nếu không tìm thấy thành viên
    }
    @Override
    public Member login(String email, String password) {
        return memberDAO.findByEmailAndPassword(email, password);}

    @Override
    public List<Member> getAll() {
        return List.of();}
    @Override
    public boolean existsByEmail(String email) {
        return memberDAO.existsByEmail(email);
    }
   
}
   
    

