package com.example.auth.basic;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
@RequestMapping("/basic")
@AllArgsConstructor
public class BasicController {
    private BasicAuthService basicAuthService;

    @GetMapping("/Authentication")
    @ResponseBody
    public String authenticationTest(HttpServletRequest request, HttpServletResponse response) {
        return basicAuthService.authentication(request.getHeader("Authorization"), response);
    }

    @GetMapping("/Admin")
    @ResponseBody
    public String adminTest(HttpServletRequest request, HttpServletResponse response) {
        return basicAuthService.authorization(response, request.getHeader("Authorization"),1);
    }

    @GetMapping("/User")
    @ResponseBody
    public String userTest(HttpServletRequest request, HttpServletResponse response) {
        return basicAuthService.authorization(response, request.getHeader("Authorization"),0);
    }

    @GetMapping("/logout")
    @ResponseBody
    public String logout(HttpServletResponse response) {
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        return "로그아웃 하셨습니다.";
    }
}
