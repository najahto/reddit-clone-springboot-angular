package com.najah.dev.reddit_clone_backend.service;

import com.najah.dev.reddit_clone_backend.dto.PostRequest;
import com.najah.dev.reddit_clone_backend.dto.PostResponse;
import com.najah.dev.reddit_clone_backend.entity.Post;

import java.util.List;

public interface PostService {

    public List<PostResponse> getAllPosts();

    public void save(PostRequest postRequest);

    public PostResponse getPost(Long id);

    public List<PostResponse> getPostsBySubreddit(Long subredditId);

    public List<PostResponse> getPostsByUsername(String username);

}
