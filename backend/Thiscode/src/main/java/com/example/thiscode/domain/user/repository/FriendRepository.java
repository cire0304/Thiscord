package com.example.thiscode.domain.user.repository;

import com.example.thiscode.domain.user.entity.Friend;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface FriendRepository extends JpaRepository<Friend, Long> {

    List<Friend> findBySenderId(Long senderId);
    List<Friend> findByReceiverId(Long receiverId);

    @Query("select f from Friend f where f.receiver.id = :userId or f.sender.id = :userId")
    List<Friend> findByReceiverIdOrSenderId(@Param("userId") Long userId);

}
