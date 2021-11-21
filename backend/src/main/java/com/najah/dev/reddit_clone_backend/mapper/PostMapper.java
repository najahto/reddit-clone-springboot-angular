package com.najah.dev.reddit_clone_backend.mapper;

import com.github.marlonlom.utilities.timeago.TimeAgo;
import com.najah.dev.reddit_clone_backend.dto.PostRequest;
import com.najah.dev.reddit_clone_backend.dto.PostResponse;
import com.najah.dev.reddit_clone_backend.entity.Post;
import com.najah.dev.reddit_clone_backend.entity.Subreddit;
import com.najah.dev.reddit_clone_backend.entity.User;
import com.najah.dev.reddit_clone_backend.repository.CommentRepository;
import com.najah.dev.reddit_clone_backend.repository.VoteRepository;
import com.najah.dev.reddit_clone_backend.service.AuthService;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;

@Mapper(componentModel = "spring")
public abstract class PostMapper {

    @Autowired
    private CommentRepository commentRepository;
    @Autowired
    private VoteRepository voteRepository;
    @Autowired
    private AuthService authService;

    @Mapping(target = "createdAt", expression = "java(java.time.Instant.now())")
    @Mapping(target = "description", source = "postRequest.description")
    @Mapping(target = "subreddit", source = "subreddit")
    @Mapping(target = "user", source = "user")
    @Mapping(target ="voteCount", constant = "0")
    public abstract Post map(PostRequest postRequest, Subreddit subreddit, User user);

    @Mapping(target = "id", source = "postId")
    @Mapping(target = "subredditName", source = "subreddit.name")
    @Mapping(target = "username", source = "user.username")
    @Mapping(target = "commentCount", expression= "java(commentCount(post))")
    @Mapping(target = "duration", expression= "java(getDuration(post))")
    public abstract PostResponse mapToDto(Post post);

    Integer commentCount(Post post){
        return commentRepository.findByPost(post).size();
    }

    String getDuration(Post post) {
        return TimeAgo.using(post.getCreatedAt().toEpochMilli());
    }
}
