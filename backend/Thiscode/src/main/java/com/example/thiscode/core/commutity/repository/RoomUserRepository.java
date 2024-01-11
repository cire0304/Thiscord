package com.example.thiscode.core.commutity.repository;

import com.example.thiscode.core.commutity.entity.RoomUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface RoomUserRepository extends JpaRepository<RoomUser, Long> {
    List<RoomUser> findAllByUserId(Long userId);

    List<RoomUser> findAllByRoomId(Long roomId);

    Optional<RoomUser> findByRoomIdAndUserId(Long roomId, Long userId);

}