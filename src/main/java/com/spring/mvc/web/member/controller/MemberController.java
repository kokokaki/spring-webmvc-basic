package com.spring.mvc.web.member.controller;

import com.spring.mvc.web.member.domain.LoginInfo;
import com.spring.mvc.web.member.domain.Member;
import com.spring.mvc.web.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.util.WebUtils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Date;

@Controller
@Log4j2
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @GetMapping("/member/sign-up")
    public String signUp() {
        return "member/join";
    }

    @PostMapping("/member/sign-up")
    public String signUp(Member member) {
        log.info("/member/sign-up POST - " + member);
        memberService.signUp(member);
        return "redirect:/board/list";
    }

    //아이디, 이메일 중복체크 비동기 요청 처리
    @GetMapping("/check")
    @ResponseBody
    public ResponseEntity<Boolean> check(String type, String keyword) {
        log.info("/check GET 비동기 요청! - " + type + " | " + keyword);
        boolean flag = memberService.isDuplicate(type, keyword);
        return new ResponseEntity<>(flag, HttpStatus.OK);
    }

    //로그인 화면 열기 요청처리
    @GetMapping("/member/sign-in")
    public String signIn() {
        return "member/login";
    }

    //로그인 검증 요청처리
    @PostMapping("/loginCheck")
    public String loginCheck(LoginInfo inputMember
            , Model model
            , HttpSession session
            , HttpServletResponse response
    ) {
        log.info("/loginCheck POST : " + inputMember);

        //로그인 처리
        String resultMessage = memberService.login(inputMember);
        log.info("result: " + resultMessage);
        model.addAttribute("result", resultMessage);

        if (resultMessage.equals("success")) {
            //로그인이 성공하면 세션에 로그인한 유저정보 저장
            session.setAttribute("loginUser", memberService.getMember(inputMember.getAccount()));

            //자동로그인 여부에 따라 추가처리
            if (inputMember.isAutoLogin()) {
                //자동로그인 처리 수행
                log.info("자동로그인 처리 수행!");
                memberService.keepLogin(session, response, inputMember.getAccount());
            }

            return "redirect:/board/list";
        }
        return "member/login-result";
    }

    //로그아웃 요청처리
    @GetMapping("/member/logout")
    public String logout(
            HttpSession session
            , HttpServletRequest request
            , HttpServletResponse response
    ) {
        log.info("/member/logout GET !");

        //로그인을 한 유저라면 세션에 로그인회원정보가 있을 것임
        Member loginUser = (Member) session.getAttribute("loginUser");

        if (loginUser != null) {
            //로그인을 한 사람은 세션을 무효화(지움)
            session.removeAttribute("loginUser");
            session.invalidate();

            //로그아웃 시 자동로그인 쿠키 삭제 및 해당 회원 정보에서 session_id제거
			/*
			 1. loginCookie를 읽어온 뒤 해당 쿠키가 존재하는지 여부 확인
			 2. 쿠키가 존재한다면 쿠키의 수명을 0초로 다시 설정한 후(setMaxAge사용)
			 3. 응답객체를 통해 로컬에 0초짜리 쿠키 재전송 -> 쿠키 삭제
			 4. service를 통해 keepLogin을 호출하여 DB컬럼 레코드 재설정
			   (session_id -> "none", limit_time -> 현재시간으로)
			 */
            Cookie loginCookie = WebUtils.getCookie(request, "loginCookie");
            if(loginCookie != null) {
                loginCookie.setMaxAge(0);
                response.addCookie(loginCookie);
                memberService.logout(loginUser.getAccount());
            }
            return "redirect:/board/list";
        }
        return "redirect:/member/login";
    }
}
