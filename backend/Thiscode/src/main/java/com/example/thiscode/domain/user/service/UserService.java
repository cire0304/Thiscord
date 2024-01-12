package com.example.thiscode.domain.user.service;

import com.example.thiscode.domain.user.repository.UserRepository;
import com.example.thiscode.domain.user.entity.User;
import com.example.thiscode.domain.user.service.dto.UserDTO;
import com.example.thiscode.domain.user.service.dto.UserMapper;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Transactional
    public User singUp(String email, String password, String nickname) {
        if (userRepository.existsByEmail(email)) {
            throw new DuplicateKeyException("이미 사용중인 이메일입니다.");
        }

        User user = new User(email, password, nickname, "introduction");
        return userRepository.save(user);
    }

    @Transactional
    @Deprecated
    public UserDTO getUserDetailInfo(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("존재하지 않는 유저입니다."));
        return userMapper.toUserDetailInfoDto(user);
    }

    @Transactional
    public User updateUser(Long userId, String nickname, String introduction) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("존재하지 않는 유저입니다."));
        user.updateNicknameAndIntroduction(nickname, introduction);
        return user;
    }

}
