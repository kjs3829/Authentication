package com.example.auth.domain;

import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class UserRepository {
    private final static Map<String, UserVO> repository = new HashMap<>();

    public UserVO insert(UserVO userVO) {
        repository.put(userVO.getId(), userVO);
        return userVO;
    }

    public String delete(String id) {
        repository.remove(id);
        return id;
    }

    public UserVO update(UserVO userVO) {
        repository.replace(userVO.getId(), userVO);
        return userVO;
    }

    public UserVO selectById(String id) {
        return repository.get(id);
    }

    public List<UserVO> selectAll() {
        return new ArrayList<>(repository.values());
    }
}
