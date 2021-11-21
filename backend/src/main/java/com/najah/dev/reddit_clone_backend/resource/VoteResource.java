package com.najah.dev.reddit_clone_backend.resource;

import com.najah.dev.reddit_clone_backend.dto.VoteDto;
import com.najah.dev.reddit_clone_backend.service.implementation.VoteServiceImpl;
import com.najah.dev.reddit_clone_backend.utility.Response;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

import static java.time.LocalDateTime.now;
import static org.springframework.http.HttpStatus.CREATED;

@RestController
@RequestMapping("api/vote")
@AllArgsConstructor
public class VoteResource {

    private final VoteServiceImpl voteService;

    @PostMapping
    public ResponseEntity<Response> vote(@RequestBody VoteDto voteDto) {
        voteService.vote(voteDto);
        return ResponseEntity.ok(
                Response.builder()
                        .timeStamp(now())
                        .message("Vote added")
                        .status(CREATED)
                        .statusCode(CREATED.value())
                        .build()
        );
    }
}
