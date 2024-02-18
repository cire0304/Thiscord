package com.example.thiscode.commutity.repository;

import com.example.thiscode.commutity.entity.RoomUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

public interface RoomUserRepository extends JpaRepository<RoomUser, Long> {
    List<RoomUser> findAllByUserId(Long userId);

    List<RoomUser> findAllByUserIdIn(Collection<Long> userIds);

    List<RoomUser> findAllByRoomId(Long roomId);

    List<RoomUser> findAllByRoomIdIn(Collection<Long> roomIds);

    Optional<RoomUser> findByRoomIdAndUserId(Long roomId, Long userId);

}
