package com.najah.dev.reddit_clone_backend.resource;

import com.najah.dev.reddit_clone_backend.dto.PostRequest;
import com.najah.dev.reddit_clone_backend.dto.SubredditDto;
import com.najah.dev.reddit_clone_backend.service.implementation.PostServiceImpl;
import com.najah.dev.reddit_clone_backend.utility.Response;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

import static java.time.LocalDateTime.now;
import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("api/posts")
@AllArgsConstructor
@Slf4j
public class PostResource {

    private final PostServiceImpl postService;

    // Get all posts
    @GetMapping("/list")
    public ResponseEntity<Response> getAllPosts() {
        return ResponseEntity.ok(
                Response.builder()
                        .timeStamp(now())
                        .data(Map.of("Posts", postService.getAllPosts()))
                        .message("Posts retrieved")
                        .status(OK)
                        .statusCode(OK.value())
                        .build()
        );
    }

    // Create new post
    @PostMapping("/create")
    public ResponseEntity<Response> createPost(@RequestBody PostRequest postRequest) {
        log.info("post request: {}",postRequest);
        postService.save(postRequest);
        return ResponseEntity.ok(
                Response.builder()
                        .timeStamp(now())
                        .message("Post created")
                        .status(CREATED)
                        .statusCode(CREATED.value())
                        .build()
        );
    }

    // Get post by id
    @GetMapping("/{id}")
    public ResponseEntity<Response> getPost(@PathVariable Long id) {
        return ResponseEntity.ok(
                Response.builder()
                        .timeStamp(now())
                        .data(Map.of("Post", postService.getPost(id)))
                        .message("Post retrieved")
                        .status(OK)
                        .statusCode(OK.value())
                        .build()
        );
    }

    // Get posts by subreddit
    @GetMapping("by-subreddit/{subredditId}")
    public ResponseEntity<Response> getPostBySubreddit(@PathVariable Long subredditId) {
        return ResponseEntity.ok(
                Response.builder()
                        .timeStamp(now())
                        .data(Map.of("Post", postService.getPostsBySubreddit(subredditId)))
                        .message("Post retrieved")
                        .status(OK)
                        .statusCode(OK.value())
                        .build()
        );
    }

    // Get posts by Username
    @GetMapping("by-username/{username}")
    public ResponseEntity<Response> getPostByUsername(@PathVariable String username) {
        return ResponseEntity.ok(
                Response.builder()
                        .timeStamp(now())
                        .data(Map.of("Post", postService.getPostsByUsername(username)))
                        .message("Post retrieved")
                        .status(OK)
                        .statusCode(OK.value())
                        .build()
        );
    }
}
