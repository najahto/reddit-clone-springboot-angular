package com.najah.dev.reddit_clone_backend.mapper;

import com.najah.dev.reddit_clone_backend.dto.PostRequest;
import com.najah.dev.reddit_clone_backend.dto.PostResponse;
import com.najah.dev.reddit_clone_backend.entity.Post;
import com.najah.dev.reddit_clone_backend.entity.Subreddit;
import com.najah.dev.reddit_clone_backend.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public abstract class PostMapper {

    @Mapping(target = "createdAt", expression = "java(java.time.Instant.now())")
    @Mapping(target = "description", source = "postRequest.description")
    @Mapping(target = "subreddit", source = "subreddit")
    @Mapping(target = "user", source = "user")
    public abstract Post map(PostRequest postRequest, Subreddit subreddit, User user);

    @Mapping(target = "id", source = "postId")
    @Mapping(target = "subredditName", source = "subreddit.name")
    @Mapping(target = "username", source = "user.username")
    public abstract PostResponse mapToDto(Post post);
}
