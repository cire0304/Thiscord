package com.example.thiscode.user.repository;

import com.example.thiscode.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findById(Long id);

    Optional<User> findByEmail(String email);

    Optional<User> findByNicknameAndUserCode(String nickname, String userCode);

    boolean existsByEmail(String email);

}
