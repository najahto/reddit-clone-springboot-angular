package com.najah.dev.reddit_clone_backend.service.implementation;

import com.najah.dev.reddit_clone_backend.dto.VoteDto;
import com.najah.dev.reddit_clone_backend.entity.Post;
import com.najah.dev.reddit_clone_backend.entity.Vote;
import com.najah.dev.reddit_clone_backend.exception.PostNotFoundException;
import com.najah.dev.reddit_clone_backend.exception.RedditCloneException;
import com.najah.dev.reddit_clone_backend.repository.PostRepository;
import com.najah.dev.reddit_clone_backend.repository.VoteRepository;
import com.najah.dev.reddit_clone_backend.service.VoteService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

import static com.najah.dev.reddit_clone_backend.enumeration.VoteType.UPVOTE;

@Service
@AllArgsConstructor
@Slf4j
@Transactional
public class VoteServiceImpl implements VoteService {

    private final VoteRepository voteRepository;
    private final PostRepository postRepository;
    private AuthServiceImpl authService;

    @Override
    public void vote(VoteDto voteDto) {
        Post post = postRepository.findById(voteDto.getPostId())
                .orElseThrow(() -> new PostNotFoundException("no post found with id:" + voteDto.getPostId()));

        Optional<Vote> voteByPostAndUser = voteRepository.findTopByPostAndUserOrderByIdDesc(post, authService.getCurrentUser());
        if (voteByPostAndUser.isPresent() &&
                voteByPostAndUser.get().getType()
                        .equals(voteDto.getType())) {
            throw new RedditCloneException("You have already "
                    + voteDto.getType() + "'d for this post");
        }

        if(UPVOTE.equals(voteDto.getType())){
            post.setVoteCount(post.getVoteCount() + 1);
        } else {
            post.setVoteCount(post.getVoteCount() - 1);
        }
        voteRepository.save(mapToVote(voteDto, post));
        postRepository.save(post);
    }

    private Vote mapToVote(VoteDto voteDto, Post post) {
        return Vote.builder()
                .type(voteDto.getType())
                .post(post)
                .user(authService.getCurrentUser())
                .build();
    }

}
