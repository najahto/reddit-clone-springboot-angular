package com.najah.dev.reddit_clone_backend.service.implementation;

import com.najah.dev.reddit_clone_backend.dto.PostRequest;
import com.najah.dev.reddit_clone_backend.dto.PostResponse;
import com.najah.dev.reddit_clone_backend.entity.Post;
import com.najah.dev.reddit_clone_backend.entity.Subreddit;
import com.najah.dev.reddit_clone_backend.entity.User;
import com.najah.dev.reddit_clone_backend.exception.PostNotFoundException;
import com.najah.dev.reddit_clone_backend.exception.RedditCloneException;
import com.najah.dev.reddit_clone_backend.exception.SubredditNotFoundException;
import com.najah.dev.reddit_clone_backend.mapper.PostMapper;
import com.najah.dev.reddit_clone_backend.repository.PostRepository;
import com.najah.dev.reddit_clone_backend.repository.SubredditRepository;
import com.najah.dev.reddit_clone_backend.repository.UserRepository;
import com.najah.dev.reddit_clone_backend.service.PostService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@AllArgsConstructor
@Transactional
public class PostServiceImpl implements PostService {

    private final PostRepository postRepository;
    private final SubredditRepository subredditRepository;
    private final AuthServiceImpl authService;
    private final UserRepository userRepository;
    private final PostMapper postMapper;

    @Override
    public List<PostResponse> getAllPosts() {
        List<Post> posts = postRepository.findAll();
        return posts.stream().map(postMapper::mapToDto).collect(Collectors.toList());
    }

    @Override
    public void save(PostRequest postRequest) {
        Subreddit subreddit = subredditRepository.findByName(postRequest.getSubredditName())
                .orElseThrow(() -> new RedditCloneException(postRequest.getSubredditName() + "subreddit no exists"));
        User currentUser = authService.getCurrentUser();
        log.info("Post subreddit:{}", subreddit);
        log.info("Current user:{}", currentUser);
        postRepository.save(postMapper.map(postRequest, subreddit, currentUser));
    }

    @Override
    public PostResponse getPost(Long id) {
        Post post = postRepository
                .findById(id)
                .orElseThrow(() -> new PostNotFoundException(id.toString()));
        return postMapper.mapToDto(post);
    }

    @Override
    public List<PostResponse> getPostsBySubreddit(Long subredditId) {
        Subreddit subreddit = subredditRepository
                .findById(subredditId)
                .orElseThrow(() -> new SubredditNotFoundException(subredditId.toString()));
        List<Post> posts = postRepository.findAllBySubreddit(subreddit);
        return posts.stream().map(postMapper::mapToDto).collect(Collectors.toList());
    }

    @Override
    public List<PostResponse> getPostsByUsername(String username) {
        User user = userRepository
                .findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException(username));
        List<Post> posts = postRepository.findByUser(user);
        return posts.stream().map(postMapper::mapToDto).collect(Collectors.toList());
    }
}
