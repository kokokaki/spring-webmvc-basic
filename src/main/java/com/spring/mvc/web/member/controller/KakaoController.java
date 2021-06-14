package com.spring.mvc.web.member.controller;

import com.spring.mvc.web.member.domain.Member;
import com.spring.mvc.web.member.domain.OAuthValue;
import com.spring.mvc.web.member.service.OAuthService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpSession;
import java.util.Map;

@Controller
@Log4j2
@RequiredArgsConstructor
public class KakaoController {

    private final OAuthService kakaoService;
    
    //kakao-test페이지 화면 요청
    @GetMapping("/kakao-test")
    public String kakaoTest(Model model) {
        
        model.addAttribute("appKey", OAuthValue.KAKAO_APP_KEY);
        model.addAttribute("redirectUri", OAuthValue.KAKAO_REDIRECT_URI);
        return "kakao-test";
    }

    //카카오 서버가 보내준 리다이렉션에 대해 처리할 요청 메서드
    //인가 코드를 받아 사용자 정보에 접근할 수 있는 토큰을 발급받는 과정
    @GetMapping(OAuthValue.KAKAO_REDIRECT_URI)
    public String kakaoLogin(String code, HttpSession session) throws Exception {

        log.info("/auth/kakao 요청: 인가코드 - " + code);
        //인가 코드로 토큰을 발급 받아야 함.
        //우리 서버에서 카카오 서버로 요청을 보내야 함.
        String accessToken = kakaoService.getAccessToken(code);

        //액세스토큰을 통해 사용자 정보 요청(프로필, 닉네임, 이메일 등)
        Map<String, Object> userInfo = kakaoService.getKakaoUserInfo(accessToken);

        if (userInfo != null) {

            Member member = new Member();
            member.setAccount((String) userInfo.get("email"));
            member.setEmail((String) userInfo.get("email"));
            member.setName((String) userInfo.get("nickName"));

            //세션에 저장
            session.setAttribute("loginUser", member);
            session.setAttribute("profile_path", userInfo.get("profileImg"));
            session.setAttribute("loginMethod", "kakao");
            session.setAttribute("accessToken", accessToken);
        }

        return "redirect:/kakao-test";
    }

    //카카오 로그아웃 요청처리
    @GetMapping("/kakao/logout")
    public String kakaoLogout(HttpSession session) throws Exception {

        //카카오 서버 로그아웃
        if (session.getAttribute("loginMethod").equals("kakao")) {
            kakaoService.logout((String) session.getAttribute("accessToken"));
        }

        //우리 서비스 로그아웃
        session.removeAttribute("loginUser");
        session.invalidate();

        return "redirect:/kakao-test";
    }

}
