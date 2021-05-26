package com.spring.mvc.web.reply.repository;

import com.spring.mvc.web.reply.domain.Reply;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ReplyMapperTest {

    @Autowired ReplyMapper replyMapper;

    @Test
    @DisplayName("특정 게시물에 댓글 20개를 삽입해야 한다.")
    void insertTest() {
        for (int i = 1; i <= 20; i++) {
            Reply reply = new Reply();
            reply.setBoardNo(322);
            reply.setReplyText("테스트댓글" + i);
            reply.setReplyWriter("야옹이");

            replyMapper.save(reply);
        }
    }

    @Test
    @DisplayName("특정 댓글 1개를 조회할 수 있어야 한다.")
    void readTest() {
        Reply findReply = replyMapper.read(20);

        assertEquals("테스트댓글20", findReply.getReplyText());
    }

    @Test
    @DisplayName("특정 게시물의 댓글 목록을 조회할 수 있어야 한다.")
    void getListTest() {
        List<Reply> list = replyMapper.getList(322);

        assertTrue(list.size() == 20);
    }

    @Test
    @DisplayName("특정 게시물을 수정할 수 있어야 한다.")
    void updateTest() {
        Reply reply = replyMapper.read(15);
        reply.setReplyText("수정된 댓글");

        replyMapper.update(reply);

        assertEquals(reply.getReplyText(), replyMapper.read(15).getReplyText());
    }

    @Test
    @DisplayName("특정 게시물을 삭제할 수 있어야 한다.")
    @Transactional @Rollback
    void deleteTest() {
        replyMapper.delete(11);
        replyMapper.delete(12);

        assertTrue(replyMapper.getList(322).size() == 16);
    }
}