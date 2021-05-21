package com.spring.mvc.web.board.repository;

import com.spring.mvc.web.board.domain.Board;
import com.spring.mvc.web.common.paging.Criteria;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class BoardMapperTest {

    @Autowired BoardMapper boardMapper;

    @Test
    @DisplayName("300개의 게시글을 등록해야 한다.")
    void bulkInsert() {
        for (int i = 1; i <= 300; i++) {
            Board board = new Board();
            board.setTitle("테스트제목" + i);
            board.setContent("테스트내용입니다. " + i);
            board.setWriter("USER" + i);

            boardMapper.insertArticle(board);
        }
    }

    @Test
    @DisplayName("페이지 정보에 따른 게시물을 조회해야 한다.")
    void pagingTest1() {
        System.out.println("=======================================================");
        Criteria criteria = new Criteria(1, 10);
        for (Board article : boardMapper.getArticles(criteria)) {
            System.out.println(article);
        }
        System.out.println("=======================================================");
    }

}