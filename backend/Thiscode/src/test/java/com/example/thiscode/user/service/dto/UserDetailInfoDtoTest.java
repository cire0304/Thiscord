package com.example.thiscode.user.service.dto;

import com.example.thiscode.user.entity.User;
import com.example.thiscode.user.repository.UserRepository;
import com.example.thiscode.user.service.dto.UserDTO;
import com.example.thiscode.user.service.dto.UserMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
class UserDetailInfoDtoTest {

    @Autowired
    private UserMapper userMapper;
    @Autowired
    private UserRepository userRepository;

    @DisplayName("유저 엔티티를 DTO로 변환할 수 있다.")
    @Test
    public void convertEntityToDto() {
        //given
        String email = "email";
        String password = "password";
        String nickname = "nickname";
        String introduction = "introduction";
        User user = new User(email, password, nickname, introduction);
        User saveUser = userRepository.save(user);

        //when
        UserDTO userDetailInfoDto = userMapper.toUserDetailInfoDto(saveUser);

        //then
        assertThat(userDetailInfoDto.getEmail()).isEqualTo(email);
        assertThat(userDetailInfoDto.getNickname()).isEqualTo(nickname);
        assertThat(userDetailInfoDto.getIntroduction()).isEqualTo(introduction);
        assertThat(userDetailInfoDto.getCreatedAt()).isEqualTo(saveUser.getCreatedAt());
        assertThat(userDetailInfoDto.getUserCode()).isEqualTo(saveUser.getUserCode());
    }

}
