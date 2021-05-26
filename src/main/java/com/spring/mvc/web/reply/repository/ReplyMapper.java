package com.spring.mvc.web.reply.repository;

import com.spring.mvc.web.reply.domain.Reply;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ReplyMapper {

    //댓글 입력
    int save(Reply reply);

    //댓글 수정
    int update(Reply reply);

    //댓글 삭제
    int delete(int replyNo);

    //댓글 1개 조회
    Reply read(int replyNo);

    //특정 게시글의 댓글 목록 조회
    List<Reply> getList(int boardNo);
}
