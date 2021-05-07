package com.spring.mvc.web.v2;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;

@WebServlet("/mvc/check")
public class MvcCheckServlet extends HttpServlet {

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        int age = Integer.parseInt(request.getParameter("age"));

        String txt = "";
        if (age > 19) {
            txt = "성인";
        } else {
            txt = "미성년자";
        }


        //필요한 데이터를 model에 담아 둠
        //model로 사용할 수 있는 객체 (request, session, application)
        request.setAttribute("result", txt);

        //model에 담아둔 데이터를 jsp파일로 전송
        RequestDispatcher dispatcher
                = request.getRequestDispatcher("/WEB-INF/result.jsp");
        dispatcher.forward(request, response);

    }
}
