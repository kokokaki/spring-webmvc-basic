package com.spring.mvc.web.v1;

import org.springframework.http.MediaType;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/*
    # 서블릿: 웹 브라우저의 동적 요청을 처리하여
             서버에서 html을 생성해서 응답하는 클래스
 */
@WebServlet(urlPatterns = "/hello")
public class HelloServlet extends HttpServlet {

    public HelloServlet() {
        System.out.println("헬로 서블릿 객체가 생성됨!");
    }

    //http요청이 왔을 때 WAS에 의해 자동 호출되는 메서드
    //용도: 핵심 로직을 수행할 메서드
    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//        System.out.println("서블릿의 서비스 메서드 호출됨!");

        String clientIp = req.getRemoteAddr();
//        System.out.println(clientIp);

        String age = req.getParameter("age");
        String name = req.getParameter("name");
        System.out.println("요청정보 name: " + name);
        System.out.println("요청정보 age: " + age);

//        resp.sendRedirect("https://www.daum.net");

        //웹 브라우저에 텍스트 응답
        //문서 타입, 문서 인코딩 설정
//        resp.setContentType("text/plain");

        //웹브라우저에 html응답 : MIME TYPE
        resp.setContentType(MediaType.TEXT_HTML_VALUE);

        resp.setCharacterEncoding("utf-8");

        PrintWriter w = resp.getWriter();

        w.write("hello~~~ " + name + ": age = " + age);

        w.write("<ol>");
        w.write("<li>하이여~</li>");
        w.write("<li>하이여2~</li>");
        w.write("<li>하이여3~</li>");
        w.write("</ol>");

        w.close();
    }
}
