package com.najah.dev.reddit_clone_backend.service;

import com.najah.dev.reddit_clone_backend.dto.CommentDto;

import java.util.List;

public interface CommentService {

    public void save(CommentDto commentDto);

    public List<CommentDto> getCommentsByPost(Long postId);

    public List<CommentDto> getCommentsByUser(String username);
}
