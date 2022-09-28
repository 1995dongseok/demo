package com.example.demo.board.entity;

import com.example.demo.member.entity.Member;
import lombok.*;
import net.bytebuddy.asm.Advice;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Entity
@Table(name = "tb_board")
public class BoardEntity {
    // 기본키 매핑//
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name = "board_num", nullable = false, length = 11)
    private Integer boardNum;

    // not null, varchar(10)
    @Column(name = "board_title", nullable = false, length = 300)
    private String boardTitle;

    @Column(name = "board_contents", nullable = false, length = 4000)
    private String boardContents;

    @ColumnDefault("0")
    @Column(name = "board_viewcounts", length = 11)
    private Integer boardViewcounts =0;

    @Column(name = "board_type", nullable = false, length = 20)
    private String boardType;

    @Column(name = "board_regidate", length = 6)
    private LocalDateTime boardRegidate = LocalDateTime.now();

    @Column(name = "board_modidate", length = 6)
    private LocalDateTime boardModidate = LocalDateTime.now();

    @ColumnDefault("0")
    @Column(name = "board_isdel", length = 1)
    private Integer boardIsdel;

    @OneToOne(targetEntity = Member.class)
    @JoinColumn(name = "user_num")
    private Member member;




}