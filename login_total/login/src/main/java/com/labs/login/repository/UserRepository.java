package com.labs.login.repository;

import com.labs.login.db.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<User, UUID> {
    Optional<User> findByUserId(UUID userId);
    Optional<User> findByUserAccount(String userAccount);
    Optional<User> findByUserIdAndUserState(UUID userId, int userState);
    Optional<User> findByUserAccountAndUserState(String userAccount, int userState);
    ArrayList<User> findByUserState(int userState);
}
