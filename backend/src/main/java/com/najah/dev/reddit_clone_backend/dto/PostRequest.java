package com.najah.dev.reddit_clone_backend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PostRequest {

    private Long id;
    private String postName;
    private String url;
    private String description;
    private String subredditName;


}
