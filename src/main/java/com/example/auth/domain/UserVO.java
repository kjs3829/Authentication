package com.example.auth.domain;

import lombok.*;

@AllArgsConstructor @NoArgsConstructor
@Getter @Setter @ToString
public class UserVO {
    public String id;
    public String password;
    public String power;    // 'Admin', 'User'

    public UserVO(String id, String password) {
        this.id = id;
        this.password = password;
    }

}
