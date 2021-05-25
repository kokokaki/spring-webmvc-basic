package com.spring.mvc.web.board.repository;

import com.spring.mvc.web.board.domain.Board;
import com.spring.mvc.web.common.paging.Criteria;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface BoardMapper {

    //게시글 목록 가져오기
    //1. 페이징 없는 버전
    List<Board> getArticles();

    //2. 페이징 쿼리 추가버전
    List<Board> getArticles(Criteria criteria);

    //3. 검색 쿼리 추가버전
    List<Board> getSearchArticles(Criteria criteria);

    //총 게시물 수 조회
    int getTotalCount(Criteria criteria);

    //게시글 등록
    void insertArticle(Board article);

    //게시글 삭제
    void deleteArticle(int boardNo);

    //게시글 내용보기
    Board getContent(int boardNo);

    //게시글 수정
    void modifyArticle(Board article);

    //조회수 상승
    void upViewCount(int boardNo);

}
