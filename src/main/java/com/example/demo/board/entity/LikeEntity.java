package com.example.demo.board.entity;

import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "tb_likes")
public class LikeEntity {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name="like_num",nullable = false,length = 11)
    int likeNum;
    @Column(name="board_num",nullable = false,length = 11)
    int boardIdx;
    @Column(name="user_num",nullable = false,length = 11)
    int userNum;
}
