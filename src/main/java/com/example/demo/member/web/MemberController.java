package com.example.demo.member.web;



import com.example.demo.member.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;


@Controller

public class MemberController {
    @Autowired
    MemberService memberService;

    @GetMapping("join")
    public String join() { return "member/memberJoin";}

    @GetMapping("login")
    public String login() { return "member/login";}


    @GetMapping("/user/login")
    public String loginResult() {return "board";}
}
