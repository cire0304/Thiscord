package com.example.thiscode.user.service;

import com.example.thiscode.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;

import java.util.concurrent.TimeUnit;

@Slf4j
@RequiredArgsConstructor
@Controller
public class AuthService {

    private final UserService userService;
    private final UserRepository userRepository;
    private final EmailModule emailModule;
    private final RedisTemplate<String, String> redisTemplate;

    @Transactional(readOnly = true)
    public String sendAuthEmail(String email) {
        if (userRepository.existsByEmail(email)) {
            throw new DuplicateKeyException("이미 회원가입한 이메일입니다.");
        }

        String checkCode = emailModule.generateCheckCode();
        redisTemplate.opsForValue().set(checkCode,email, 60*30L, TimeUnit.SECONDS);
        emailModule.sendCheckCodeEmail(email, checkCode);

        return checkCode;
    }

    @Transactional
    public void checkEmail(String key) {
        String email = (String) redisTemplate.opsForValue().get(key);
        if (email == null) {
            throw new IllegalArgumentException("인증코드가 만료되었습니다.");
        } else {
            redisTemplate.delete(key);
        }
    }

}
