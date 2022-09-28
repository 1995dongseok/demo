package com.example.demo.member.web;


import com.example.demo.member.entity.Member;
import com.example.demo.member.service.MemberService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RestController

public class MemberRestController {

    @Autowired
    MemberService memberService;


    @PostMapping("/joinAdd")
    public void joinAdd(Member member, HttpServletResponse response) throws IOException {

        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        System.out.println(member);
        member.setPassword(passwordEncoder.encode(member.getPassword()));
       memberService.saveMember(member);

       response.sendRedirect("/board");
    }


}
