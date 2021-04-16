package com.example.auth.domain;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@AllArgsConstructor
public class UserService {
    private UserRepository repository;

    public int getRole(String id) {
        return repository.selectById(id).getRole();
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
