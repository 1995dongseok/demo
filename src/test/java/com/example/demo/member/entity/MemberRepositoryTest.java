package com.example.demo.member.entity;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;


import java.util.Optional;



class MemberRepositoryTest {

    @Autowired
    MemberRepository memberRepository;

    @Test
    public void findbyUser_emailTest () throws Exception {
        String email="babo34@gmail.com";


        System.out.println(email);


    }


}