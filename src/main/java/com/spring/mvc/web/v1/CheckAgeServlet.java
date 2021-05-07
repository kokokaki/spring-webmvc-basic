package com.spring.mvc.web.v1;


import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/check-age")
public class CheckAgeServlet extends HttpServlet {

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        int age = Integer.parseInt(request.getParameter("age"));

        String txt = "";
        if (age > 19) {
            txt = "성인";
        } else {
            txt = "미성년자";
        }

        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/html");

        PrintWriter w = response.getWriter();
        w.println("<html>");
        w.println("<head>");
        w.println("</head>");
        w.println("<body>");
        for (int i = 0; i < 3; i++) {
            w.println("<h1>");
            w.println("당신은 " + txt + "입니다.");
            w.println("</h1>");
        }
        w.println("</body>");
        w.println("</html>");
    }
}
