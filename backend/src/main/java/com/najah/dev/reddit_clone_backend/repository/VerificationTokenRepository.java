package com.najah.dev.reddit_clone_backend.repository;

import com.najah.dev.reddit_clone_backend.entity.VerificationToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface VerificationTokenRepository extends JpaRepository<VerificationToken,Long> {

    Optional<VerificationToken> findByToken(String token);

}
