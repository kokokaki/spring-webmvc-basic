package com.spring.mvc.web.api.v1;

import com.spring.mvc.web.board.domain.Board;
import com.spring.mvc.web.board.service.BoardService;
import com.spring.mvc.web.common.paging.Criteria;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/api/v1")
public class ApiControllerV1 {

    @GetMapping("/hello")
    @ResponseBody //클라이언트 요청을 비동기로 받음
    public String hello() {
        return "안녕안녕~~";
    }

    @GetMapping("/hobby")
    @ResponseBody
    public String[] hobby() {
        return new String[] {"음악감상", "축구", "꽃꽂이"};
    }

    @GetMapping("/major")
    @ResponseBody
    public List<String> major() {
        return Arrays.asList("정보보안", "컴퓨터공학", "경영학", "수학과");
    }

    @GetMapping("/board")
    @ResponseBody
    public Board board() {
        return new Board(105, "김레스트"
                , "레스트api 학습중", "메롱메롱~", 0);
    }

    @GetMapping("/food")
    @ResponseBody
    public Map<String, String> food() {
        Map<String, String> foodMap = new HashMap<>();
        foodMap.put("한식", "비빔밥");
        foodMap.put("양식", "피자");
        foodMap.put("중식", "짬뽕");
        return foodMap;
    }

    @Autowired
    private BoardService boardService;

    @GetMapping("/board-list")
    @ResponseBody
    public List<Board> list() {
        return boardService.getArticles(new Criteria());
    }

}
