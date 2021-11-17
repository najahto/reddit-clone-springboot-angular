package com.najah.dev.reddit_clone_backend.repository;

import com.najah.dev.reddit_clone_backend.entity.Subreddit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SubredditRepository extends JpaRepository<Subreddit,Long> {
}
