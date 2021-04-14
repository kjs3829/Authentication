package com.example.auth.basic;

import com.example.auth.domain.UserService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.nio.charset.StandardCharsets;
import java.util.Base64;


@Controller
@RequestMapping("/basic")
@AllArgsConstructor
public class BasicController {
    private UserService service;

    @GetMapping("/Authentication")
    @ResponseBody
    public String authenticationTest(HttpServletRequest request, HttpServletResponse response) {
        String authorization = request.getHeader("Authorization");
        if (authorization == null) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.setHeader("WWW-Authenticate", "Basic");
            return "로그인이 필요합니다.";
        }
        String base64Credentials = authorization.substring("Basic".length()).trim();
        byte[] credDecoded = Base64.getDecoder().decode(base64Credentials);
        String credentials = new String(credDecoded, StandardCharsets.UTF_8);
        String[] values = credentials.split(":",2);
        if (service.checking(values[0],values[1])) {
            response.setStatus(HttpServletResponse.SC_OK);
            return "인증된 유저입니다.";
        }
        else {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return "인증에 실패하였습니다.";
        }
    }

    @GetMapping("/Admin")
    @ResponseBody
    public String adminTest(HttpServletRequest request, HttpServletResponse response) {
        String authorization = request.getHeader("Authorization");
        if (authorization == null) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.setHeader("WWW-Authenticate", "Basic");
            return "로그인이 필요합니다.";
        }
        String base64Credentials = authorization.substring("Basic".length()).trim();
        byte[] credDecoded = Base64.getDecoder().decode(base64Credentials);
        String credentials = new String(credDecoded, StandardCharsets.UTF_8);
        String[] values = credentials.split(":",2);
        if (service.checking(values[0],values[1])) {
            response.setStatus(HttpServletResponse.SC_OK);
            if (service.getPower(values[0]).equals("Admin")) {
                return "인가된 유저입니다.";
            }
            else return "인가되지 않은 유저입니다.";
        }
        else {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return "인증되지 않은 유저입니다.";
        }
    }

    @GetMapping("/User")
    @ResponseBody
    public String userTest(HttpServletRequest request, HttpServletResponse response) {
        String authorization = request.getHeader("Authorization");
        if (authorization == null) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.setHeader("WWW-Authenticate", "Basic");
            return "로그인이 필요합니다.";
        }
        String base64Credentials = authorization.substring("Basic".length()).trim();
        byte[] credDecoded = Base64.getDecoder().decode(base64Credentials);
        String credentials = new String(credDecoded, StandardCharsets.UTF_8);
        String[] values = credentials.split(":",2);
        if (service.checking(values[0],values[1])) {
            response.setStatus(HttpServletResponse.SC_OK);
            if (service.getPower(values[0]).equals("User") ||
                    service.getPower(values[0]).equals("Admin")) {
                return "인가된 유저입니다.";
            }
            else return "인가되지 않은 유저입니다.";
        }
        else {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return "인증되지 않은 유저입니다.";
        }
    }

    @GetMapping("/logout")
    @ResponseBody
    public String logout(HttpServletResponse response) {
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        return "로그아웃 하셨습니다.";
    }
}
