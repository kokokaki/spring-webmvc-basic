package com.spring.mvc.web.member.service;

import java.net.MalformedURLException;
import java.util.Map;

public interface OAuthService {

    //액세스 토큰 발급 메서드
    String getAccessToken(String authCode) throws Exception;

    Map<String, Object> getKakaoUserInfo(String accessToken) throws Exception;

    void logout(String accessToken) throws Exception;
}
