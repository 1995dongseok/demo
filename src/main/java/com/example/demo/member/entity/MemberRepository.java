package com.example.demo.member.entity;



import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;



public interface MemberRepository extends JpaRepository<Member,Integer> {
   Member findByEmail(String email);

   Member findByUserIdx(int userIdx);
}
