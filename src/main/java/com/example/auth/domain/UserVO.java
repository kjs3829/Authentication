package com.example.auth.domain;

import lombok.*;

@AllArgsConstructor @NoArgsConstructor
@Getter @Setter @ToString
public class UserVO {
    public String id;
    public String password;

    // 0 = user, 1 = admin
    // 숫자가 커질수록 권한의 등급도 높아진다.
    // 이와 같이 설계한 이유는 계층형 권한 구조를 간단하게 구현하기 위함.
    // 어드민은 모든 유저 권한 자원에 접근할 수 있어야 하며, 어드민 권한 자원에도 접근할 수 있어야 된다.
    public int role;

    public UserVO(String id, String password) {
        this.id = id;
        this.password = password;
    }

}
