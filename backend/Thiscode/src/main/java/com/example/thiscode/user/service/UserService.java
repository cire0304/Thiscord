package com.example.thiscode.user.service;

import com.example.thiscode.user.repository.UserRepository;
import com.example.thiscode.user.entity.User;
import com.example.thiscode.user.service.dto.UserDTO;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public User singUp(String email, String password, String nickname) {
        if (userRepository.existsByEmail(email)) {
            throw new DuplicateKeyException("이미 회원가입한 이메일입니다.");
        }

        String encodedPassword = passwordEncoder.encode(password);

        User user = new User(email, encodedPassword, nickname, "introduction");
        return userRepository.save(user);
    }

    @Transactional
    public User updateUser(Long userId, String nickname, String introduction) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("존재하지 않는 유저입니다."));
        user.updateNicknameAndIntroduction(nickname, introduction);
        return user;
    }

}
