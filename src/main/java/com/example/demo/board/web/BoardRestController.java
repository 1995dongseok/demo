package com.example.demo.board.web;

import com.example.demo.board.entity.BoardEntity;
import com.example.demo.board.entity.LikeEntity;
import com.example.demo.board.service.BoardService;
import com.example.demo.board.service.request.BoardInsertCommand;
import com.example.demo.config.auth.PrincipalDetail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RestController
@RequestMapping("/v1")
public class BoardRestController {

    @Autowired
    BoardService boardService;

    @PostMapping("/boardAdd")
    public void boardAdd(BoardInsertCommand command , @AuthenticationPrincipal PrincipalDetail principalDetail, HttpServletResponse response)throws IOException{
        command.setUser_num(principalDetail.getUserIdx());
        boardService.boardInsert(command);
        response.sendRedirect("/board");
    }

    @GetMapping("/boardList")
    public String boardList(Pageable pageable){
        return boardService.findAll(pageable);
    }

    @GetMapping("/detailOne/{boardNum}")
    public ModelAndView detailOne(@PathVariable int boardNum, @AuthenticationPrincipal PrincipalDetail principalDetail){
       BoardEntity boardOne = boardService.findOne(boardNum);
       int likeCnt = boardService.likeCnt(boardNum);
       int userIdx = principalDetail.getUserIdx();
        System.out.println("좋아요:"+likeCnt);

        ModelAndView mav = new ModelAndView("boardDetail");
        mav.addObject("boardOne", boardOne);
        mav.addObject("userIdx", userIdx);
        mav.addObject("likeCnt",likeCnt);
        return mav;
    }

    @GetMapping("/delete/{boardNum}")
    public void delete(@PathVariable int boardNum,
                       @AuthenticationPrincipal PrincipalDetail principalDetail,
                       HttpServletResponse response) throws IOException {
        String email = principalDetail.getUsername();
        int userIdx = principalDetail.getUserIdx();
        System.out.println(userIdx);

        BoardEntity boardOne = boardService.findOne(boardNum);
        int dbUserIdx = boardOne.getMember().getUserIdx();
        System.out.println(dbUserIdx);


        if(userIdx == dbUserIdx) {
            System.out.println("삭제성공");
            boardService.deleteOne(boardNum);
        }else{
            System.out.println("삭제실패");
            response.sendRedirect("/board");
        }

    }

    @PostMapping("/boardModi/{boardNum}")
    public void boardModi(BoardInsertCommand command,
                          @PathVariable int boardNum,
                          @AuthenticationPrincipal PrincipalDetail principalDetail,
                          HttpServletResponse response) throws IOException{
        command.setUser_num(principalDetail.getUserIdx());
        command.setBoard_num(boardNum);
        boardService.boardModi(command);
        response.sendRedirect("/v1/detailOne/"+boardNum);
    }

    @GetMapping("/boardRead/{boardNum}")
    public void boardRead(@PathVariable int boardNum, Model model){
        model.addAttribute("view", boardService.updateView(boardNum));
    }

    @PostMapping("/updateLike/{boardNum}")
    public int updateLike( @PathVariable int boardNum , @AuthenticationPrincipal PrincipalDetail principalDetail){

        LikeEntity likeEntity = new LikeEntity();

        int userIdx = principalDetail.getUserIdx();
        int likeCnt = boardService.likeCnt(boardNum);


        likeEntity.setUserNum(userIdx);
        likeEntity.setBoardIdx(boardNum);

        int likeCheck = boardService.likeCheck(boardNum,userIdx);

        if (likeCheck == 1 ) {
            boardService.likeDelete(boardNum,userIdx);

        }else {
            boardService.likeInsert(likeEntity);
        }

        return likeCnt;

    }




}
