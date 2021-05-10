package com.spring.mvc.web.response;

import com.spring.mvc.web.request.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServletRequest;

@Controller
public class ResponseController {

    @GetMapping("/response/res-ex")
    public void resEx() {}

    @GetMapping("/response/test")
    public String test(int age
            //, HttpServletRequest request
                       , Model model
    ) {
        System.out.println("age = " + age);
        //request.setAttribute("userAge", age + 10);
        model.addAttribute("userAge", age);
        model.addAttribute("nickName", "바보");
        return "response/test";
    }

    @PostMapping("/response/test2")
    public String test2(User user, Model model) {
        model.addAttribute("user", user);
        return "response/test2";
    }

    //=============== quiz ===================//
    @GetMapping("/response/quiz")
    public String quiz() {
        return "response/res-quiz";
    }

    @PostMapping("/response/quiz")
    public String quiz(String userId, String userPw, Model model) {

        model.addAttribute("account", userId);

        if (userId.equals("kim123") && userPw.equals("kkk1234")) {
            return "response/success";
        } else {
            return "response/fail";
        }
    }
}
