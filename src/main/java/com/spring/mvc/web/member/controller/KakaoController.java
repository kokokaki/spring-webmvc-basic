package com.spring.mvc.web.member.controller;

import com.spring.mvc.web.member.domain.OAuthValue;
import com.spring.mvc.web.member.service.OAuthService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

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
    public String kakaoLogin(String code) throws Exception {

        log.info("/auth/kakao 요청: 인가코드 - " + code);
        //인가 코드로 토큰을 발급 받아야 함.
        //우리 서버에서 카카오 서버로 요청을 보내야 함.
        String accessToken = kakaoService.getAccessToken(code);

        return "redirect:/kakao-test";
    }
}
