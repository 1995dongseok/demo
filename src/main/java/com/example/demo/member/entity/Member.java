package com.example.demo.member.entity;

import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;
import javax.persistence.*;



@Getter
@Setter
@ToString
@Entity
@Table(name = "tb_user")
@DynamicInsert
@AllArgsConstructor
@NoArgsConstructor
public class Member  {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(length = 11,name = "user_num")
    private int userIdx ;

    @Column(length = 300,name = "user_email")
    private String email;
    @Column(length = 300,name = "user_name")
    private String userName;
    @Column(length = 300,name = "user_pw")
    private String password;
    @Column(length = 300,name = "user_phone")
    private String phoneNumber;
    @Column (name = "user_admin",length = 300)
    private String role;


}