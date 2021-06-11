package com.spring.mvc.web.member.service;

import java.net.MalformedURLException;

public interface OAuthService {

    //액세스 토큰 발급 메서드
    String getAccessToken(String authCode) throws Exception;
}
