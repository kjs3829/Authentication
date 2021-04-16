package com.example.auth.basic;

import com.example.auth.domain.UserRepository;
import com.example.auth.domain.UserService;
import com.example.auth.domain.UserVO;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

@Service
@AllArgsConstructor
public class BasicAuthService {
    private UserService userService;
    private UserRepository userRepository;

    public String authentication(String authorization, HttpServletResponse response) {

        // Authorization 헤더가 없으면 클라이언트에게 인증을 요구한다.
        if (noAuthorization(authorization)) {
            response.setHeader("WWW-Authenticate", "Basic");
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return "로그인이 필요합니다.";
        }

        // Authorization을 Basic으로 디코딩하여 id와 pwd를 얻는다.
        // 로그인에 사용된 id, pwd 와 DB에 저장된 id, pwd가 일치하는지 확인한다.
        // 일치하지 않을 경우 403에러를 보낸다. (Http Spec)
        if (!authenticated(authorization)) {
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
            return "인증에 실패하였습니다.";
        }

        // 인증에 성공할 경우 성공했다는 의미의 200 상태 코드를 보낸다. (Http Spec)
        response.setStatus(HttpServletResponse.SC_OK);
        return "인증에 성공하였습니다.";

    }

    public String authorization(HttpServletResponse response, String authorization, int requiredRole) {

        // Authorization 헤더가 없으면 클라이언트에게 인증을 요구한다.
        if (noAuthorization(authorization)) {
            response.setHeader("WWW-Authenticate", "Basic");
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return "로그인이 필요합니다.";
        }

        // Authorization을 Basic으로 디코딩하여 id와 pwd를 얻는다.
        // 로그인에 사용된 id, pwd 와 DB에 저장된 id, pwd가 일치하는지 확인한다.
        // 일치하지 않을 경우 403에러를 보낸다. (Http Spec)
        if (!authenticated(authorization)) {
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
            return "인증에 실패하였습니다.";
        }

        // Authorization을 Basic으로 디코딩하여 id를 얻는다.
        // 해당 id의 role을 확인한다.
        // 접근에 요구되는 role 보다 접근을 요청한 id의 권한 등급이 낮으면 403에러
        if (!authorized(authorization, requiredRole)) {
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
            return "인가에 실패하였습니다.";
        }

        // 인가에 성공할 경우 성공했다는 의미의 200 상태 코드를 보낸다. (Http Spec)
        response.setStatus(HttpServletResponse.SC_OK);
        return "인가에 성공하였습니다.";
    }

    // Authorization 헤더의 유무 판별
    private boolean noAuthorization(String authorization) {
        return authorization == null;
    }

    // TODO : Optional 공부 및 적용
    private boolean checking(String id, String pwd) {
        UserVO user = userRepository.selectById(id);
        if (user != null) {
            return user.getPassword().equals(pwd);
        }
        return false;
    }

    private boolean authenticated(String authorization) {
        String base64Credentials = authorization.substring("Basic".length()).trim();
        byte[] credDecoded = Base64.getDecoder().decode(base64Credentials);
        String credentials = new String(credDecoded, StandardCharsets.UTF_8);
        String[] values = credentials.split(":",2);
        return checking(values[0],values[1]);
    }

    private boolean authorized(String authorization, int requiredRole) {
        String base64Credentials = authorization.substring("Basic".length()).trim();
        byte[] credDecoded = Base64.getDecoder().decode(base64Credentials);
        String credentials = new String(credDecoded, StandardCharsets.UTF_8);
        String[] values = credentials.split(":",2);
        return userService.getRole(values[0]) >= requiredRole;
    }
}
