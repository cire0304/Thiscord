package com.example.thiscode.core.user.repository;

import com.example.thiscode.core.user.entity.User;
import com.example.thiscode.core.user.repository.UserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@Transactional
@SpringBootTest
class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @DisplayName("유저 아이디로 유저를 찾는다.")
    @Test
    public void findByUserId () {
        //given
        User user = new User("abc@gmail.com", "1234", "nickname", "introduction");
        User saveUser = userRepository.save(user);

        //when
        User findUser = userRepository.findById(saveUser.getId())
                .orElseThrow(() -> new IllegalArgumentException("해당 유저가 없습니다."));

        //then
        assertThat(findUser).usingRecursiveComparison()
                .isEqualTo(saveUser);
     }

    @DisplayName("유저 아이디로 유저를 찾는다.")
    @Test
    public void findByEmail () {
        //given
        String email = "abc@gmail.com";;
        User user = new User(email, "1234", "nickname", "introduction");
        User saveUser = userRepository.save(user);

        //when
        User findUser = userRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("해당 유저가 없습니다."));

        //then
        assertThat(findUser).usingRecursiveComparison()
                .isEqualTo(saveUser);
    }

}
