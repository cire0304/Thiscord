package com.example.thiscode.core.user.service;

import com.example.thiscode.core.user.repository.UserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest
@Transactional
class UserServiceTest {

    @Autowired
    private UserService userService;
    @Autowired
    private UserRepository userRepository;

    @DisplayName("회원 가입을 할 수 있다.")
    @Test
    public void singUp () {
        //given
        String email = "email";
        String password = "password";
        String nickname = "nickname";

        //when
        userService.singUp(email, password, nickname);

        //then
        assertThat(userRepository.existsByEmail(email)).isTrue();
     }

    @DisplayName("이미 가입된 이메일로 회원 가입을 할 수 없다.")
    @Test
    public void singUpWithDuplicateEmail () {
        //given
        String email = "email";
        String password = "password";
        String nickname = "nickname";
        userService.singUp(email, password, nickname);

        //when then
        assertThatThrownBy(() -> {
            userService.singUp(email, password, nickname);
        }).isInstanceOf(DuplicateKeyException.class);
    }

}
