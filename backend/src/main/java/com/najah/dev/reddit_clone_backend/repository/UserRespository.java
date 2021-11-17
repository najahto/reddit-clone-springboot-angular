package com.najah.dev.reddit_clone_backend.repository;

import com.najah.dev.reddit_clone_backend.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRespository extends JpaRepository<User,Long> {

    Optional<User> findByUsername(String username);
}
