package com.spring.mvc.web.member.repository;

import com.spring.mvc.web.member.domain.Auth;
import com.spring.mvc.web.member.domain.Member;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class MemberMapperTest {

    @Autowired
    MemberMapper mapper;

    @Test
    @DisplayName("회원가입에 성공해야 한다.")
    void registerTest() {
        Member member = Member.builder()
                .account("abc4321")
                .name("고길동")
                .auth(Auth.COMMON)
                .email("aaa4343@gmail.com")
                .password("aaa1234")
                .build();

        System.out.println("member = " + member);
        mapper.register(member);
        Member findUser = mapper.getUser(member.getAccount());

        assertEquals(member.getAccount(), findUser.getAccount());
    }

    @Test
    @DisplayName("아이디, 이메일 중복확인에 성공해야 한다.")
    void duplicateTest() {
        String inputAccount = "abc4321";

        Map<String, Object> datas = new HashMap<>();
        datas.put("type", "account");
        datas.put("keyword", inputAccount);

        int result = mapper.isDuplicate(datas);

        assertTrue(result == 1);
    }

    @Test
    @DisplayName("비밀번호가 암호화되어야 한다.")
    void encodePwTest() {
        //평문 패스워드
        String rawPw = "ddd5555";

        //패스워드 인코딩
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String encodedPw = encoder.encode(rawPw);

        System.out.println("==================================");
        System.out.println("평문 비밀번호: " + rawPw);
        System.out.println("암호화 비밀번호: " + encodedPw);
    }

    @Test
    @DisplayName("비밀번호가 암호화된 상태로 회원가입에 성공해야 한다.")
    void regTest2() {
        Member member = Member.builder()
                .account("admin123")
                .email("admin123@naver.com")
                .name("박관리자")
                .auth(Auth.ADMIN)
                .password(new BCryptPasswordEncoder().encode("1234"))
                .build();

        mapper.register(member);
    }

    @Test
    @DisplayName("로그인 검증을 수행해야 한다.")
    void loginTest() {
        //로그인 시도 아이디
        String inputId = "banana";
        //로그인 시도 패스워드
        String inputPw = "bbb1234";

        //로그인 시도 아이디를 기반으로 회원정보를 조회
        Member member = mapper.getUser(inputId);

        if (member != null) {
            //db에 저장된 비번
            String dbPw = member.getPassword();

            //암호화된 비번을 디코딩해서 비교
            BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

            if (encoder.matches(inputPw, dbPw)) {
                System.out.println("로그인 성공!");
            } else {
                System.out.println("비밀번호가 틀렸습니다.");
            }

        } else {
            System.out.println("회원가입을 먼저 진행하세요.");
        }
    }

}