package com.spring.mvc.web.member.service;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.spring.mvc.web.member.domain.OAuthValue;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

@Service
@Log4j2
public class KakaoService implements OAuthValue, OAuthService {

    //카카오 로그인시에 사용자 정보에 접근할 수 있는 토큰을 발급받는 메서드
    @Override
    public String getAccessToken(String authCode) throws Exception {

        //액세스토큰을 발급받을 요청 URI
        String reqURI = "https://kauth.kakao.com/oauth/token";

        //서버 to 서버 요청 보내기
        //URL정보를 담을 객체 생성
        URL url = new URL(reqURI);

        //해당 요청 URL을 연결하고 연결정보를 담을 Connection객체 생성
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();

        //요청 정보 설정
        conn.setRequestMethod("POST");
        //응답 결과를 받을 것인가
        conn.setDoOutput(true);

        //필요한 파라미터 정보를 스트림을 통해 전송
        sendRequestData(authCode, conn);

        //응답 데이터를 스트림을 통해 읽음
        return takeResponseData(conn);
    }

    @Override
    public Map<String, Object> getKakaoUserInfo(String accessToken) throws Exception {

        String reqUrl = "https://kapi.kakao.com/v2/user/me";

        URL url = new URL(reqUrl);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();

        //요청 메서드 설정
        conn.setRequestMethod("POST");
        conn.setDoOutput(true);

        //요청 헤더정보 설정
        conn.setRequestProperty("Authorization", "Bearer " + accessToken);

        //요청 보내기
        try (BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(conn.getOutputStream()))) {

            //응답 상태코드 200이면 성공
            int responseCode = conn.getResponseCode();
            log.info("응답 코드(사용자 정보 요청): " + responseCode);

        } catch (Exception e) {
            e.printStackTrace();
        }

        try (BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()))) {

            //응답데이터를 입력스트림으로부터 읽어내기
            String responseData = br.readLine();
            log.info("response-data: " + responseData);

            //JSON문자열을 Gson라이브러리를 사용하여 자바 객체로 파싱
            JsonParser parser = new JsonParser();

            //JsonElement는 자바로 변환된 JSON객체
            JsonElement element = parser.parse(responseData);

            //필요한 데이터를 json프로퍼티 키를 사용해서 추출
            //kakao_account 정보 추출
            JsonObject kakaoAccount
                    = element.getAsJsonObject()
                    .get("kakao_account").getAsJsonObject();

            log.info("카카오계정정보: " + kakaoAccount);

            String nickName
                    = kakaoAccount.get("profile").getAsJsonObject()
                    .get("nickname").getAsString();
            String profileImgPath = kakaoAccount.get("profile").getAsJsonObject()
                    .get("profile_image_url").getAsString();
            String email = kakaoAccount.get("email").getAsString();

            log.info("별명: " + nickName);
            log.info("프로필사진경로: " + profileImgPath);
            log.info("이메일: " + email);

            //추출한 정보를 맵에 담아 리턴.
            Map<String, Object> kakaoInfoMap = new HashMap<>();
            kakaoInfoMap.put("nickName", nickName);
            kakaoInfoMap.put("profileImg", profileImgPath);
            kakaoInfoMap.put("email", email);

            return kakaoInfoMap;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    private String takeResponseData(HttpURLConnection conn) {
        try (BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()))) {

            //응답데이터를 입력스트림으로부터 읽어내기
            String responseData = br.readLine();
            log.info("response-data: " + responseData);

            //JSON문자열을 Gson라이브러리를 사용하여 자바 객체로 파싱
            JsonParser parser = new JsonParser();

            //JsonElement는 자바로 변환된 JSON객체
            JsonElement element = parser.parse(responseData);

            //필요한 데이터를 json프로퍼티 키를 사용해서 추출
            JsonObject jsonObject = element.getAsJsonObject();
            String accessToken = jsonObject.get("access_token").getAsString();
            String refreshToken = jsonObject.get("refresh_token").getAsString();

            log.info("accessToken : " + accessToken);
            log.info("refreshToken : " + refreshToken);

            return accessToken;

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private void sendRequestData(String authCode, HttpURLConnection conn) {
        try (BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(conn.getOutputStream()))) {

            String queryParam = "grant_type=authorization_code"
                                + "&client_id=" + KAKAO_APP_KEY
                                + "&redirect_uri=http://localhost:8181" + KAKAO_REDIRECT_URI
                                + "&code=" + authCode;

            //스트림을 통해 파라미터 전송
            bw.write(queryParam);
            //출력버퍼 비우기
            bw.flush();

            //응답 상태코드 200이면 성공
            int responseCode = conn.getResponseCode();
            log.info("응답 코드: " + responseCode);


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //로그아웃 요청
    public void logout(String accessToken) throws Exception {
        String reqUrl = "https://kapi.kakao.com/v1/user/logout";

        URL url = new URL(reqUrl);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();

        //요청 메서드 설정
        conn.setRequestMethod("POST");
        conn.setDoOutput(true);

        //요청 헤더정보 설정
        conn.setRequestProperty("Authorization", "Bearer " + accessToken);

        try (BufferedReader br
                     = new BufferedReader(new InputStreamReader(conn.getInputStream()))) {

            //응답 상태코드 200이면 성공
            int responseCode = conn.getResponseCode();
            log.info("응답 코드(로그아웃): " + responseCode);

            //응답데이터를 입력스트림으로부터 읽어내기
            String responseData = br.readLine();
            log.info("logout-response-data: " + responseData);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }


}
