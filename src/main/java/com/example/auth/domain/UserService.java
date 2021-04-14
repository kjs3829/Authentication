package com.example.auth.domain;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class UserService {
    private UserRepository repository;

    // TODO : Optional 공부 및 적용
    public boolean checking(String id, String pwd) {
        UserVO user = repository.selectById(id);
        if (user != null) {
            return user.getPassword().equals(pwd);
        }
        return false;
    }

    public String getPower(String id) {
        return repository.selectById(id).getPower();
    }

    public UserVO signUp(UserVO userVO) {
        return repository.insert(userVO);
    }

    public List<UserVO> getUserList() {
        return repository.selectAll();
    }

    public UserVO getUser(String id) {
        return repository.selectById(id);
    }

}
