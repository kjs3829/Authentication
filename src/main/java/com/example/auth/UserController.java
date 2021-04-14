package com.example.auth;

import com.example.auth.domain.UserService;
import com.example.auth.domain.UserVO;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;
import java.util.List;

@Controller
@AllArgsConstructor
public class UserController {
    private UserService service;

    @GetMapping("/users")
    @ResponseBody
    public List<UserVO> userList() {
        return service.getUserList();
    }

    @PostMapping("/users")
    @ResponseBody
    public UserVO signUp(@ModelAttribute UserVO userVO) {
        return service.signUp(userVO);
    }

    @GetMapping("/users/new")
    public String signUpPage() {
        return "signUpPage";
    }

    @GetMapping("/users/{id}")
    @ResponseBody
    public UserVO userInfo(@PathVariable(name = "id")String id) {
        return service.getUser(id);
    }

    @PostConstruct
    public void init() {
        UserVO admin = new UserVO("admin","admin","Admin");
        UserVO user = new UserVO("user", "user", "User");
        service.signUp(admin);
        service.signUp(user);
    }

}
