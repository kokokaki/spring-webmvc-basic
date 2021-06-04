package com.spring.mvc.web.interceptor;

import com.spring.mvc.web.member.domain.Member;
import com.spring.mvc.web.member.repository.MemberMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.util.WebUtils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Configuration
public class AutoLoginInterceptor implements HandlerInterceptor {

    @Autowired
    private MemberMapper memberMapper;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        //1. 자동 로그인 쿠키가 존재하는 확인
        //로컬의 쿠키를 읽어오는 메서드
        Cookie loginCookie = WebUtils.getCookie(request, "loginCookie");

        //2. 자동로그인한 회원인 경우 쿠키값을 읽어서 DB와 대조
        if (loginCookie != null) {
            String value = loginCookie.getValue();
            //3. 쿠키 세션아이디를 통해 자동 로그인한 회원의 정보 조회
            Member member = memberMapper.getUserBySessionId(value);
            if (member != null) {
                //4. 세션에 loginUser로 해당 회원정보 저장
                request.getSession().setAttribute("loginUser", member);
            }
        }
        return true;
    }
}
