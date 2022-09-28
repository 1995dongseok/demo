package com.example.demo.board.entity;


import com.example.demo.board.service.request.BoardInsertCommand;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BoardInsertRepository extends JpaRepository<BoardInsertCommand,String> {

//    @Modifying(clearAutomatically = true)
//    @Query("update BoardEntity b set b.boardType = :boardType, b.boardTitle = :boardTitle, b.boardContents = :boardContetns, b.boardModidate = :boardModidate where b.boardNum = :boardNum")
//    int boardUpdate(int boardNum, BoardInsertCommand command);
}
