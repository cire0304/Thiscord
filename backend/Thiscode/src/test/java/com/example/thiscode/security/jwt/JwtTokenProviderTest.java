package com.example.thiscode.security.jwt;

import com.example.thiscode.core.user.repository.UserRepository;
import com.example.thiscode.core.user.entity.User;
import com.example.thiscode.security.model.PrincipalUser;
import com.example.thiscode.security.model.ProviderUser;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.Authentication;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
class JwtTokenProviderTest {

    @Autowired
    private JwtTokenProvider jwtTokenProvider;
    @Autowired
    private UserRepository userRepository;

    @DisplayName("token으로 부터 JwtSubject 객체를 생성한다.")
    @Test
    public void  PrincipalUser() {
        //given
        User user = userRepository.save(new User("email", "password", "nickname", "introduction"));
        ProviderUser providerUser = new ProviderUser(user);
        PrincipalUser principalUser = new PrincipalUser(providerUser);
        String token = jwtTokenProvider.createJwtToken(principalUser);

        //when
        Authentication authentication =  jwtTokenProvider.getAuthentication(token);
        JwtSubject principal = (JwtSubject) authentication.getPrincipal();

        //then
        assertThat(principal.getId()).isEqualTo(user.getId());
        assertThat(principal.getUserCode()).isEqualTo(user.getUserCode());
        assertThat(principal.getNickname()).isEqualTo(user.getNickname());
        assertThat(principal.getEmail()).isEqualTo(user.getEmail());
        assertThat(principal.getIntroduction()).isEqualTo(user.getIntroduction());
     }


}
