package com.example.thiscode.core.user.repository;

import com.example.thiscode.core.user.entity.Friend;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface FriendRepository extends JpaRepository<Friend, Long> {

    Optional<Friend> findBySenderId(Long senderId);
    Optional<Friend> findByReceiverId(Long receiverId);

    @Query("select f from Friend f where f.receiver.id = :userId or f.sender.id = :userId")
    List<Friend> findByReceiverIdOrSenderId(Long userId);

}
