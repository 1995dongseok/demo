package com.example.demo.board.service;

import com.example.demo.board.entity.*;
import com.example.demo.board.service.request.BoardInsertCommand;
import com.example.demo.board.service.response.BoardDomain;
import com.example.demo.member.entity.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.*;

@Service
@RequiredArgsConstructor
public class BoardService {

    @Autowired
    BoardInsertRepository boardinsertRepository;

    @Autowired
    LikeRepository likeRepository;

    @Autowired
    MemberRepository memberRepository;

    @Autowired
    BoardRepository boardRepository;

    public void boardInsert(BoardInsertCommand command) {
        boardinsertRepository.save(command);
    }
//model view controller = mvc 구조
    public BoardDomain findAll(Pageable pageable){
        Page<BoardEntity> pageData = boardRepository.findAll(pageable);
        BoardDomain boardDomain = new BoardDomain();
        boardDomain.setBoardEntity(pageData.getContent());
        boardDomain.setTotalCount((int) pageData.getTotalElements());
        return boardDomain;
    }

    public BoardEntity findOne(int boardNum){
        BoardEntity boardOne = boardRepository.findByBoardNum(boardNum);
        return boardOne;
    }

    public void deleteOne(int boardNum){
        boardRepository.deleteById(boardNum);
    }

    public void boardModi(BoardInsertCommand command)  {boardinsertRepository.save(command);}

    @Transactional
    public int updateView(int boardNum) {
        return boardRepository.updateView(boardNum);
    }

    public void likeInsert(LikeEntity likeEntity){


        likeRepository.save(likeEntity);
    }

    public int likeCheck(int boardNum,int userNum) {
        LikeEntity find =  likeRepository.findByBoardIdxAndUserNum(boardNum,userNum);

        if (find != null) {
            return 1;
        }else {
            return 0;
        }
    }

    public void likeDelete(int boardNum,int userNum) {
        LikeEntity find =  likeRepository.findByBoardIdxAndUserNum(boardNum,userNum);
        likeRepository.delete(find);
    }

    public int likeCnt(int boardNum) { return likeRepository.countByBoardIdx(boardNum); }



}
