package com.spring.mvc.web.reply.api;

import com.spring.mvc.web.common.paging.Criteria;
import com.spring.mvc.web.reply.domain.Reply;
import com.spring.mvc.web.reply.service.ReplyService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/reply")
@Log4j2
@RequiredArgsConstructor
public class ReplyApiController {

    private final ReplyService replyService;

    //댓글 목록 조회 요청 처리
    @GetMapping("/{bno}/{page}")
    public ResponseEntity<Map<String, Object>> getList(
            @PathVariable("bno") int boardNo
            , @PathVariable("page") int page
    ) {
        log.info("/api/v1/reply/"+boardNo + " GET!!");

        Criteria criteria = new Criteria(page, 10);
        Map<String, Object> replyMap = replyService.getList(boardNo, criteria);

        if (replyMap != null) {
            return new ResponseEntity<>(replyMap, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    //댓글 등록 처리 요청
    @PostMapping("")
    public ResponseEntity<String> create(@RequestBody Reply reply) {
        log.info("/api/v1/reply POST - " + reply);

        return replyService.register(reply)
                ? new ResponseEntity<>("insertSuccess", HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    //댓글 수정 요청 처리
    @PutMapping("/{rno}")
    public ResponseEntity<String> modify(
            @PathVariable("rno") int replyNo,
            @RequestBody Reply reply
    ) {
        reply.setReplyNo(replyNo);
        log.info("/api/v1/reply/" + replyNo + " PUT! - " + reply);
        return replyService.modify(reply)
                ? new ResponseEntity<>("modSuccess", HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    //댓글 삭제 요청 처리
    @DeleteMapping("/{rno}")
    public ResponseEntity<String> remove(
            @PathVariable("rno") int replyNo
    ) {
        log.info("/api/v1/reply/" + replyNo + " DELETE! - ");

        return replyService.remove(replyNo)
                ? new ResponseEntity<>("delSuccess", HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
