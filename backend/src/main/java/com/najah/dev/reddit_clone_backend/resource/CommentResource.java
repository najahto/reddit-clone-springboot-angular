package com.najah.dev.reddit_clone_backend.resource;

import com.najah.dev.reddit_clone_backend.dto.CommentDto;
import com.najah.dev.reddit_clone_backend.service.implementation.CommentServiceImpl;
import com.najah.dev.reddit_clone_backend.utility.Response;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Map;

import static java.time.LocalDateTime.now;
import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;

@RestController
@AllArgsConstructor
@Slf4j
@RequestMapping("/api/comments")
public class CommentResource {

    private final CommentServiceImpl commentService;

    @PostMapping("/create")
    public ResponseEntity<Response> createComment(@RequestBody CommentDto commentDto) {
        commentService.save(commentDto);
        return ResponseEntity.ok(
                Response.builder()
                        .timeStamp(now())
                        .message("Comment created")
                        .status(CREATED)
                        .statusCode(CREATED.value())
                        .build()
        );
    }

    @GetMapping("/post/{postId}")
    public ResponseEntity<Response> getCommentsByPost(@PathVariable Long postId) {
        return ResponseEntity.ok(
                Response.builder()
                        .timeStamp(now())
                        .data(Map.of("Comments", commentService.getCommentsByPost(postId)))
                        .message("Comments retrieved")
                        .status(OK)
                        .statusCode(OK.value())
                        .build()
        );
    }

    @GetMapping("/user/{username}")
    public ResponseEntity<Response> getCommentsByUser(@PathVariable String username) {
        return ResponseEntity.ok(
                Response.builder()
                        .timeStamp(now())
                        .data(Map.of("Comments", commentService.getCommentsByUser(username)))
                        .message("Comments retrieved")
                        .status(OK)
                        .statusCode(OK.value())
                        .build()
        );
    }

}
