package com.spring.mvc.web.board.controller;

import com.spring.mvc.web.board.domain.Board;
import com.spring.mvc.web.board.domain.ModifyBoard;
import com.spring.mvc.web.board.repository.BoardRepository;
import com.spring.mvc.web.board.service.BoardService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@Log4j2
public class BoardController {

    private final BoardService boardService;

    @Autowired
    public BoardController(BoardService boardService) {
        this.boardService = boardService;
    }

    //글 작성 화면 요청
    @GetMapping("/board/write")
    public String write() {
        log.info("/board/write GET! ");
        return "board/write";
    }

    //글 작성 처리 요청
    @PostMapping("/board/write")
    public String write(Board article) {
        boardService.insertArticle(article);
        return "redirect:/board/list";
    }

    //글 목록 요청
    @GetMapping("/board/list")
    public String list(Model model) {
        model.addAttribute("articles", boardService.getArticles());
        return "board/list";
    }

    //글 삭제 요청
    @GetMapping("/board/delete")
    public String delete(int boardNo) {
        boardService.deleteArticle(boardNo);
        return "redirect:/board/list";
    }

    //글 상세보기 요청
    @GetMapping("/board/content")
    public String content(int boardNo
            , @RequestParam("vf") boolean viewCntFlag
            , Model model) {
        Board content = boardService.getContent(boardNo, viewCntFlag);
        model.addAttribute("article", content);
        return "board/content";
    }

    //글 수정하기 화면요청
    @GetMapping("/board/modify")
    public String modify(int boardNo
                    , @RequestParam("vf") boolean viewCntFlag
                    , Model model) {
        model.addAttribute("article", boardService.getContent(boardNo, viewCntFlag));
        return "board/modify";
    }

    //글 수정 처리요청
    @PostMapping("/board/modify")
    public String modify(ModifyBoard modArticle
            , @RequestParam("vf") boolean viewCntFlag) {

        Board board = boardService.getContent(modArticle.getBoardNo(), viewCntFlag);
        board.setWriter(modArticle.getWriter());
        board.setTitle(modArticle.getTitle());
        board.setContent(modArticle.getContent());
        boardService.modifyArticle(board);

        return "redirect:/board/content?boardNo=" + modArticle.getBoardNo() + "&vf=false";
    }
}
