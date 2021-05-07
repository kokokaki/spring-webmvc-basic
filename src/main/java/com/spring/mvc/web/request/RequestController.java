package com.spring.mvc.web.request;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

//스프링 컨테이너에 해당 클래스의 객체가 빈(컨트롤러기능)으로 등록됨
@Controller
public class RequestController {

    //요청 처리 메서드 (서블릿 역할)
    //해당 URL에 따른 GET요청을 처리하게 함.
//    @RequestMapping(value = "/req/test", method = RequestMethod.GET)
    @GetMapping("/req/test")
    public String test() {
        System.out.println("## /req/test GET요청 발생!!");
        //return "/WEB-INF/views/test.jsp"; //리턴은 뷰 파일 포워딩 개념
        return "test";
    }
}
