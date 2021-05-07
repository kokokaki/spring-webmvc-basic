package com.spring.mvc.web.v3;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AgeController {

    //WEB-INF폴더의 age.jsp를 열어주는 요청 처리
    @GetMapping("/spring/age")
    public String springAge() {
        System.out.println("/spring/age GET요청 발생!");
        return "/WEB-INF/age.jsp";
    }

    //입력받은 나이를 검증하는 요청 처리
    @GetMapping("/spring/check")
    public String check(int age, Model model) {

        String txt = (age > 19) ? "성인" : "미성년자";
        model.addAttribute("result", txt);

        return "/WEB-INF/result.jsp";
    }

}
