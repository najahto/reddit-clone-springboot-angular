package com.najah.dev.reddit_clone_backend.dto;

import com.najah.dev.reddit_clone_backend.enumeration.VoteType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class VoteDto {
    private VoteType type;
    private Long postId;
}
