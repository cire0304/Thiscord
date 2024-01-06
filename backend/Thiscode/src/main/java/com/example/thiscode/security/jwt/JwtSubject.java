package com.example.thiscode.security.jwt;

import com.example.thiscode.security.model.PrincipalUser;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;

@ToString
@Getter
@NoArgsConstructor
public class JwtSubject implements Serializable {

    private Long id;
    private String userCode;
    private String nickname;
    private String email;
    private String introduction;

    public JwtSubject(PrincipalUser principalUser) {
        this.id = principalUser.getId();
        this.userCode = principalUser.getUserCode();
        this.nickname = principalUser.getNickname();
        this.email = principalUser.getEmail();
        this.introduction = principalUser.getIntroduction();
    }

}
