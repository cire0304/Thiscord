package com.example.thiscode.security.jwt;

import com.example.thiscode.core.repository.UserRepository;
import com.example.thiscode.core.user.entity.User;
import com.example.thiscode.security.authentication.model.GoogleUser;
import com.example.thiscode.security.authentication.model.PrincipalUser;
import com.example.thiscode.security.authentication.model.ProviderUser;
import io.jsonwebtoken.Claims;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.Authentication;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class JwtTokenProviderTest {

    @Autowired
    private JwtTokenProvider jwtTokenProvider;
    @Autowired
    private UserRepository userRepository;

    @DisplayName("token으로 부터 CustomJwtSubject 객체를 생성한다.")
    @Test
    public void  PrincipalUser() {
        //given
        User user = userRepository.save(new User("email", "password", "nickname", "userCode"));
        GoogleUser googleUser = new GoogleUser(user);
        PrincipalUser principalUser = new PrincipalUser(googleUser);
        String token = jwtTokenProvider.createToken(principalUser);

        //when
        CustomJwtSubject customJwtSubject = jwtTokenProvider.getCustomJwtSubject(token);

        //then
        assertThat(customJwtSubject.getUserId()).isEqualTo(user.getId());
        assertThat(customJwtSubject.getUserCode()).isEqualTo(user.getUserCode());
        assertThat(customJwtSubject.getNickname()).isEqualTo(user.getNickname());
        assertThat(customJwtSubject.getEmail()).isEqualTo(user.getEmail());
     }


}
