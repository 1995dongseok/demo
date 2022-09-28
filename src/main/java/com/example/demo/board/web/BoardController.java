package com.example.demo.board.web;

import com.example.demo.board.entity.BoardEntity;
import com.example.demo.board.service.BoardService;
import com.example.demo.config.auth.PrincipalDetail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class BoardController {

    @Autowired
    BoardService boardService;

    @RequestMapping("/board")
    public String board() {
        return "board";
    }

    @RequestMapping("/editor")
    public String editor(){
        return "editor";
    }

    @RequestMapping("/detail")
    public String detail(Model model) {
        return "boardDetail";
    }

    @GetMapping("/modify/{boardNum}")
    public ModelAndView modify(@PathVariable int boardNum, @AuthenticationPrincipal PrincipalDetail principalDetail) {
        BoardEntity boardOne = boardService.findOne(boardNum);
        int userIdx = principalDetail.getUserIdx();
        ModelAndView mav = new ModelAndView("boardModify");
        mav.addObject("boardOne", boardOne);
        mav.addObject("userIdx", userIdx);
        return mav;
    }
}

