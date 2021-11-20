package com.najah.dev.reddit_clone_backend.mapper;

import com.najah.dev.reddit_clone_backend.dto.CommentDto;
import com.najah.dev.reddit_clone_backend.entity.Comment;
import com.najah.dev.reddit_clone_backend.entity.Post;
import com.najah.dev.reddit_clone_backend.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CommentMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "post", source ="post")
    @Mapping(target = "user", source = "user")
    @Mapping(target = "text", source ="commentDto.text")
    @Mapping(target = "createdAt", expression ="java(java.time.Instant.now())")
    Comment map(CommentDto commentDto, Post post, User user);

    @Mapping(target = "postId", expression ="java(comment.getPost().getPostId())")
    @Mapping(target = "username", expression ="java(comment.getUser().getUsername())")
    CommentDto mapToDto(Comment comment);

}
