package com.spring.mvc.web.reply.service;

import com.spring.mvc.web.common.paging.Criteria;
import com.spring.mvc.web.common.paging.PageMaker;
import com.spring.mvc.web.reply.domain.Reply;
import com.spring.mvc.web.reply.repository.ReplyMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class ReplyService {

    private static final int SUCCESS = 1;

    private final ReplyMapper replyMapper;

    //댓글 등록 서비스
    public boolean register(Reply reply) {
        return replyMapper.save(reply) == SUCCESS;
    }

    //댓글 수정 서비스
    public boolean modify(Reply reply) {
        return replyMapper.update(reply) == SUCCESS;
    }

    //댓글 삭제 서비스
    public boolean remove(int replyNo) {
        return replyMapper.delete(replyNo) == SUCCESS;
    }

    //개별 댓글 조회 서비스
    public Reply get(int replyNo) {
        return replyMapper.read(replyNo);
    }

    //댓글 목록 조회 서비스
    public Map<String, Object> getList(int boardNo, Criteria criteria) {
        Map<String, Object> replyMap = new HashMap<>();
        replyMap.put("replyList", replyMapper.getList(boardNo, criteria));
        int count = replyMapper.getCount(boardNo);
        replyMap.put("count", count);
        replyMap.put("pageInfo", new PageMaker(criteria, count));
        return replyMap;
    }

}
