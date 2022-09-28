package com.example.demo.board.entity;

import com.example.demo.board.service.request.BoardInsertCommand;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface BoardRepository extends JpaRepository<BoardEntity, Integer> {
    BoardEntity  findByBoardNum(int boardNum);

    Page<BoardEntity> findAll(Pageable pageable);

    @Modifying
    @Query("update BoardEntity b set b.boardViewcounts = b.boardViewcounts+1 where b.boardNum=:boardNum")
    int updateView(int boardNum);
}
