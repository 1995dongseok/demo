package com.example.demo.member.service;


import com.example.demo.config.auth.PrincipalDetail;
import com.example.demo.member.entity.Member;
import com.example.demo.member.entity.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class MemberService implements UserDetailsService {

    @Autowired
    MemberRepository memberRepository;


    public Member saveMember(Member member) {
        return memberRepository.save(member);
    }



    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        Member member = memberRepository.findByEmail(email);


        List<GrantedAuthority> authorities = new ArrayList<>();

        System.out.println(member.getRole());

        if(member.getRole().equals("ADMIN")) {
            authorities.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
        }else {
            authorities.add(new SimpleGrantedAuthority("ROLE_USER"));
        }

        System.out.println(authorities);


        // PrincipalDetail 객체에 member를 담아서 security에 보내줘야 security가 로그인가능 여부 판단해줌
        return new PrincipalDetail(member,authorities);


    }
}