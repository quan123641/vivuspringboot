package com.congdinh.vivuspringboot.services;

import com.congdinh.vivuspringboot.entities.Member;
import com.congdinh.vivuspringboot.repository.MemberDAO;
import jakarta.servlet.http.HttpSession;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
@AllArgsConstructor
public class AuthServiceImlp implements AuthService, UserDetailsService {
    private MemberDAO memberDAO;

    @Autowired
    private HttpSession httpSession;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Member member = memberDAO.findByEmail(username).orElseThrow(
                ()-> new UsernameNotFoundException("User not found")
        );
        SimpleGrantedAuthority authority = new SimpleGrantedAuthority("ROLE_USER");
        List<GrantedAuthority> authorities = List.of(authority);
        httpSession.setAttribute("member", member);
        return new org.springframework.security.core.userdetails.User(
                member.getEmail(),
                member.getPassword(),
                authorities
        );
    }
}
