package com.example.thiscode.domain.commutity.repository;

import com.example.thiscode.domain.commutity.entity.RoomUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

public interface RoomUserRepository extends JpaRepository<RoomUser, Long> {
    List<RoomUser> findAllByUserId(Long userId);

    List<RoomUser> findAllByRoomId(Long roomId);

    List<RoomUser> findAllByRoomIdIn(Collection<Long> roomIds);

    Optional<RoomUser> findByRoomIdAndUserId(Long roomId, Long userId);

}
