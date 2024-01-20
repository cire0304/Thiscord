package com.example.thiscode.domain.notification.repository;

import com.example.thiscode.domain.notification.entity.Profile;
import com.example.thiscode.domain.user.entity.Friend;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

public interface ProfileRepository extends JpaRepository<Profile, Long> {

    List<Profile> findAllByUserIdIn(Collection<Long> userIds);

    List<Profile> findAllByUserId(Long userId);

}
