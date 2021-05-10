package com.spring.mvc.web.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Setter @Getter
@ToString
//@NoArgsConstructor
public class User {

    private String userId;
    private String userPw;
    private String userName;
    private List<String> hobby;

    public User() {
        System.out.println("User 커맨드 객체 생성됨!");
    }

    public void setUserId(String userId) {
        System.out.println("userId Setter 호출됨!");
        this.userId = userId;
    }
}
