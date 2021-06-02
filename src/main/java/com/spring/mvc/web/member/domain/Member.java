package com.spring.mvc.web.member.domain;

import lombok.*;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Date;

@Getter @Setter @ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Member {

    private String account; //계정명
    private String password; //비밀번호
    private String name; //사용자이름
    private String email; //이메일
    private Auth auth; //권한
    private Date regDate; //가입일자

    public void setPassword(String password) {
        this.password = new BCryptPasswordEncoder().encode(password);
    }
}
