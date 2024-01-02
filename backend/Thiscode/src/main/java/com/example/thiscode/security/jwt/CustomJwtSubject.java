package com.example.thiscode.security.jwt;

import com.example.thiscode.security.model.PrincipalUser;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;

@ToString
@Getter
@NoArgsConstructor
public class CustomJwtSubject implements Serializable {

    private Long userId;
    private String userCode;
    private String nickname;
    private String email;

    public CustomJwtSubject(PrincipalUser principalUser) {
        this.userId = principalUser.getId();
        this.userCode = principalUser.getUserCode();
        this.nickname = principalUser.getNickname();
        this.email = principalUser.getEmail();
    }

}
