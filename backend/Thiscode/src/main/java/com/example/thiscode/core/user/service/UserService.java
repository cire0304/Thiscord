package com.example.thiscode.core.user.service;

import com.example.thiscode.core.user.repository.UserRepository;
import com.example.thiscode.core.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;

    @Transactional
    public void singUp(String email, String password, String nickname) {
        if (userRepository.existsByEmail(email)) {
            throw new DuplicateKeyException("이미 사용중인 이메일입니다.");
        }

        User user = new User(email, password, nickname, "introduction");
        userRepository.save(user);
    }


}
