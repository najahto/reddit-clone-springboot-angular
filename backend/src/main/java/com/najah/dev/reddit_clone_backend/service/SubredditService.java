package com.najah.dev.reddit_clone_backend.service;

import com.najah.dev.reddit_clone_backend.dto.SubredditDto;

import java.util.List;

public interface SubredditService {

    public List<SubredditDto> getAll();

    public SubredditDto save(SubredditDto subredditDto);

    public SubredditDto getSubreddit(Long id);

}
