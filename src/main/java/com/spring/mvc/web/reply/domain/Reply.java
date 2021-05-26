package com.spring.mvc.web.reply.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

@Getter @Setter @ToString
public class Reply {

    private int replyNo; //댓글번호
    private int boardNo; //원본게시글 번호
    private String replyText; //댓글 내용
    private String replyWriter; //댓글 작성자
    private Date replyDate; //댓글 작성 시간

}