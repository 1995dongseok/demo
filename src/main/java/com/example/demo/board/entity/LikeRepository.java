package com.example.demo.board.entity;

import org.springframework.data.jpa.repository.JpaRepository;

public interface LikeRepository extends JpaRepository<LikeEntity,Integer> {

    LikeEntity findByBoardIdxAndUserNum(int boardNum,int userNum);

    int countByBoardIdx(int boardNum);
}
