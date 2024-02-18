package com.example.thiscode.notification.repository;

import com.example.thiscode.notification.entity.Profile;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;
import java.util.List;

public interface ProfileRepository extends JpaRepository<Profile, Long> {

    List<Profile> findAllByUserIdIn(Collection<Long> userIds);

    List<Profile> findAllByUserId(Long userId);

}
