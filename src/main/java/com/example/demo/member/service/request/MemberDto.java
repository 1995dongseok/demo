package com.example.demo.member.service.request;




import com.example.demo.member.entity.Member;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;



@Getter
@Setter
@ToString
public class MemberDto {
    private int userIdx;
    private String email;
    private String userName;
    private String password;
    private String phoneNumber;
    private String role;

}


