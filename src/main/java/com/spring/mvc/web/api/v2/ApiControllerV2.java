package com.spring.mvc.web.api.v2;

import com.spring.mvc.web.board.domain.Board;
import com.spring.mvc.web.board.service.BoardService;
import com.spring.mvc.web.common.paging.Criteria;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

//API를 만들 때 사용하는 컨트롤러 아노테이션
@RestController
@RequestMapping("/api/v2")
@CrossOrigin // 다른 서버에서 요청이 올 때 CORS 정책을 해제함.
@Log4j2
public class ApiControllerV2 {

    /*
        # ResponseEntity
        - 스프링 REST API가 응답을 할 때
         응답 상태 코드, 응답 헤더 등을 조작해서
         전송할 수 있게 도와주는 객체
     */

    @GetMapping("/hello")
    public ResponseEntity<String> hello(String p) {
        if (p.equals("hello")) {
            return new ResponseEntity<>("안녕안녕", HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>("페이지를 찾을 수 없음.", HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/hobby")
    public String[] hobby(HttpServletRequest request) {
        log.info(request.getRemoteAddr());
        return new String[] {"음악감상", "축구", "꽃꽂이", "요리"};
    }

    @GetMapping("/major")
    public List<String> major() {
        return Arrays.asList("정보보안", "컴퓨터공학", "경영학", "수학과");
    }

    @GetMapping("/board")
    public Board board() {
//        return new Board(105, "김레스트"
//                , "레스트api 학습중", "메롱메롱~", 0);
        return null;
    }

    @GetMapping("/food")
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
    public List<Board> list() {
        return boardService.getArticles(new Criteria());
    }

}
