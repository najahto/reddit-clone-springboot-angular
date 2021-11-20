package com.najah.dev.reddit_clone_backend.repository;

import com.najah.dev.reddit_clone_backend.entity.Comment;
import com.najah.dev.reddit_clone_backend.entity.Post;
import com.najah.dev.reddit_clone_backend.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {

    List<Comment> findByPost(Post post);

    List<Comment> findByUser(User user);

}
