package com.spring.mvc.web.member.repository;

import com.spring.mvc.web.member.domain.Member;
import org.apache.ibatis.annotations.Mapper;

import java.util.Map;

@Mapper
public interface MemberMapper {

    //회원 가입 기능
    void register(Member member);

    //계정, 이메일 중복체크
    int isDuplicate(Map<String, Object> datas);

    //회원정보 조회 기능
    Member getUser(String account);

    //자동로그인 관련 기능
    void saveKeepLogin(Map<String, Object> datas);

    //세션 아이디값을 통한 회원정보 조회기능
    Member getUserBySessionId(String sessionId);

}
