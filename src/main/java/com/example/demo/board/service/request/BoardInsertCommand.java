package com.example.demo.board.service.request;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@Entity
@Table(name = "tb_board")
@DynamicInsert
public class BoardInsertCommand {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Integer board_num;
    private String board_title;
    private String board_contents;

    @ColumnDefault("0")
    private Integer board_viewcounts;

    private String board_type;

    private LocalDateTime board_regidate = LocalDateTime.now();
    private LocalDateTime board_modidate = LocalDateTime.now();

    private Integer user_num=1;
}
