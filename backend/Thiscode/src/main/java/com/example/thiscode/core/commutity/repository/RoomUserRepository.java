package com.example.thiscode.core.commutity.repository;

import com.example.thiscode.core.commutity.entity.RoomUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface RoomUserRepository extends JpaRepository<RoomUser, Long> {
    List<RoomUser> findByUserId(Long userId);

    Optional<RoomUser> findByRoomIdAndUserId(Long roomId, Long userId);

}