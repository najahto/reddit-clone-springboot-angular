package com.najah.dev.reddit_clone_backend.resource;

import com.najah.dev.reddit_clone_backend.dto.SubredditDto;
import com.najah.dev.reddit_clone_backend.service.implementation.SubredditServiceImpl;
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
@RequestMapping("api/subreddit")
@AllArgsConstructor
@Slf4j
public class SubredditResource {

    private final SubredditServiceImpl subredditService;

    @GetMapping("/list")
    public ResponseEntity<Response> getAllSubreddits() {
        return ResponseEntity.ok(
                Response.builder()
                        .timeStamp(now())
                        .data(Map.of("Subreddits", subredditService.getAll()))
                        .message("Subreddits retrieved")
                        .status(OK)
                        .statusCode(OK.value())
                        .build()
        );
    }

    @PostMapping("/create")
    public ResponseEntity<Response> createSubreddit(@RequestBody SubredditDto subredditDto) {
        return ResponseEntity.ok(
                Response.builder()
                        .timeStamp(now())
                        .data(Map.of("Subreddit", subredditService.save(subredditDto)))
                        .message("Subreddit created")
                        .status(CREATED)
                        .statusCode(CREATED.value())
                        .build()
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<Response> getSubreddit(@PathVariable Long id) {
        return ResponseEntity.ok(
                Response.builder()
                        .timeStamp(now())
                        .data(Map.of("Subreddit", subredditService.getSubreddit(id)))
                        .message("Subreddit retrieved")
                        .status(OK)
                        .statusCode(OK.value())
                        .build()
        );
    }

}
