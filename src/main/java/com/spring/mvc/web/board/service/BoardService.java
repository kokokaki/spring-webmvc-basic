package com.spring.mvc.web.board.service;

import com.spring.mvc.web.board.domain.Board;
import com.spring.mvc.web.board.repository.BoardMapper;
import com.spring.mvc.web.board.repository.BoardRepository;
import com.spring.mvc.web.common.paging.Criteria;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BoardService {

    //    public final BoardRepository boardRepository;
    public final BoardMapper boardRepository;

//    @Autowired
//    public BoardService(BoardRepository boardRepository) {
//        this.boardRepository = boardRepository;
//    }

    //게시글 목록 가져오기
    public List<Board> getArticles(Criteria criteria) {
//        List<Board> temp = boardRepository.getArticles();
//        List<Board> articles = new ArrayList<>();
//        for (int i = temp.size() - 1; i >= 0; i--) {
//            Board board = temp.get(i);
//            articles.add(board);
//        }
//        return articles;


//        return boardRepository.getArticles(criteria);

        //검색 쿼리
        List<Board> articles = boardRepository.getSearchArticles(criteria);

        //3분 이내 신규글 new마크 붙이기
        for (Board article : articles) {
            //각 게시물들의 등록시간 읽어오기 (밀리초)
            long regTime = article.getRegDate().getTime();
            //현재시간 읽어오기 (밀리초)
            long now = System.currentTimeMillis();

            //1일의 밀리초: 60 * 60 * 24 * 1000
            if (now - regTime < 60 * 3 * 1000) {
                article.setNewArticle(true);
            }
        }

        return articles;
    }

    //총 게시물 수 확인
    public int getTotal(Criteria criteria) {
        return boardRepository.getTotalCount(criteria);
    }

    //게시글 등록
    @Transactional //트랜잭션 처리 자동화
    public void insertArticle(Board article) {
        boardRepository.insertArticle(article);

        //만약에 첨부파일이 존재한다면 추가 쿼리를 동작해야 함
        List<String> filePathList = article.getFilePathList();
        if (filePathList != null) {
            for (String path : filePathList) {
                boardRepository.addFile(path);
            }
        }
    }

    //게시글 삭제
    public void deleteArticle(int boardNo) {
        boardRepository.deleteArticle(boardNo);
    }

    //게시글 내용보기
    @Transactional
    public Board getContent(int boardNo, boolean viewFlag) {
        Board content = boardRepository.getContent(boardNo);

        if (viewFlag) {
            //content.upViewCount(); //조회수상승
            boardRepository.upViewCount(boardNo);
        }
        return content;
    }
    //첨부파일 경로목록 구하기
    public List<String> getFilePaths(int boardNo) {
        return boardRepository.getFilePaths(boardNo);
    }

    //게시글 수정
    public void modifyArticle(Board article) {
        boardRepository.modifyArticle(article);
    }

}
