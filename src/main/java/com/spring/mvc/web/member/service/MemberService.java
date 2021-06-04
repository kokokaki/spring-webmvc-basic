package com.spring.mvc.web.member.service;

import com.spring.mvc.web.member.domain.LoginInfo;
import com.spring.mvc.web.member.domain.Member;
import com.spring.mvc.web.member.repository.MemberMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberMapper memberMapper;

    //회원 가입 기능
    public void signUp(Member member) {
        String rp = member.getPassword();
        member.setPassword(new BCryptPasswordEncoder().encode(rp));

        memberMapper.register(member);
    }

    /**
     * 중복확인 기능
     *
     * @param type    중복검사유형 (계정, 이메일)
     * @param keyword 중복검사값
     * @return 중복되었으면 true, 중복되지않았으면 false
     */
    public boolean isDuplicate(String type, String keyword) {
        Map<String, Object> checkDataMap = new HashMap<>();
        checkDataMap.put("type", type);
        checkDataMap.put("keyword", keyword);

        return memberMapper.isDuplicate(checkDataMap) == 1;
    }

    //회원 정보 조회 기능
    public Member getMember(String account) {
        return memberMapper.getUser(account);
    }

    //로그인 처리 기능
    public String login(LoginInfo inputMember) {
        Member dbMember = memberMapper.getUser(inputMember.getAccount());

        if (dbMember != null) {
            BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
            if (encoder.matches(inputMember.getPassword(), dbMember.getPassword())) {
                return "success";
            } else {
                return "pwFail";
            }
        } else {
            return "idFail";
        }
    }

    //자동로그인 처리를 위한 메서드
    /*
        1. 쿠키를 생성해서 현재 로그인한 유저의 브라우저 세션의 고유ID를 저장한 후
           로컬에 쿠키 전송
        2. 데이터베이스에 로그인한 유저의 자동로그인 관련컬럼에 데이터 추가
           (쿠키값, 로그인 유지 시간)
     */
    public void keepLogin(
            HttpSession session
            , HttpServletResponse response
            , String account
    ) {
        //자동로그인 쿠키 생성 (세션고유아이디를 쿠키값으로 저장)
        String sessionId = session.getId();
        Cookie loginCookie = new Cookie("loginCookie", sessionId);
        //쿠키 설정값 세팅 (쿠키를 적용할 URL, 쿠키의 수명 등)
        loginCookie.setPath("/");
        //90일
        int limitTime = 60 * 60 * 24 * 90;
        loginCookie.setMaxAge(limitTime);

        //로컬에 쿠키 전송
        response.addCookie(loginCookie);

        //DB에 자동로그인 관련값 저장
        Map<String, Object> keepLoginMap = new HashMap<>();
        keepLoginMap.put("sid", sessionId);

        //자동 로그인 유지시간을 날짜로 변환
        long expiredMs = System.currentTimeMillis() + ((long)limitTime * 1000);
        Date limitDate = new Date(expiredMs);
        keepLoginMap.put("lt", limitDate);
        keepLoginMap.put("acc", account);

        memberMapper.saveKeepLogin(keepLoginMap);
    }

    //자동로그인 해제 기능
    public void logout(String account) {
        Map<String, Object> logoutMap = new HashMap<>();
        logoutMap.put("sid", "none");
        logoutMap.put("lt", new Date());
        logoutMap.put("acc", account);
        memberMapper.saveKeepLogin(logoutMap);
    }
}
