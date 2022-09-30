package com.example.demo.board.service.response;

import com.example.demo.board.entity.BoardEntity;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter

public class BoardDomain {
    private List<BoardEntity> boardEntity;
    private int totalCount;
}
