package com.example.thiscode.core.user.entity;

import com.example.thiscode.core.common.BaseEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;


@ToString
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "user")
@Entity
public class User extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "email")
    private String email;

    @Column(name = "password", length = 68)
    private String password;

    @Column(name = "nickname")
    private String nickname;

    @Column(name = "user_code")
    private String userCode;

    @Column(name = "introduction")
    private String introduction;

    public User(String email, String password, String nickname, String introduction) {
        StringBuilder randomCode = new StringBuilder();
        for (int i = 0; i < 6; i++) {
            int n = (int) (Math.random() * 10);
            randomCode.append(n);
        }

        this.email = email;
        this.password = password;
        this.nickname = nickname;
        this.userCode = randomCode.toString();
        this.introduction = introduction;
    }

}
