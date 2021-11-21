package com.najah.dev.reddit_clone_backend.service.implementation;

import com.najah.dev.reddit_clone_backend.constant.BaseUrlConstant;
import com.najah.dev.reddit_clone_backend.constant.EmailConstant;
import com.najah.dev.reddit_clone_backend.dto.CommentDto;
import com.najah.dev.reddit_clone_backend.entity.Comment;
import com.najah.dev.reddit_clone_backend.entity.EmailNotification;
import com.najah.dev.reddit_clone_backend.entity.Post;
import com.najah.dev.reddit_clone_backend.entity.User;
import com.najah.dev.reddit_clone_backend.exception.PostNotFoundException;
import com.najah.dev.reddit_clone_backend.mapper.CommentMapper;
import com.najah.dev.reddit_clone_backend.repository.CommentRepository;
import com.najah.dev.reddit_clone_backend.repository.PostRepository;
import com.najah.dev.reddit_clone_backend.repository.UserRepository;
import com.najah.dev.reddit_clone_backend.service.AuthService;
import com.najah.dev.reddit_clone_backend.service.CommentService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

import static com.najah.dev.reddit_clone_backend.constant.BaseUrlConstant.POSTS_API_BASE_URL;
import static com.najah.dev.reddit_clone_backend.constant.EmailConstant.COMMENT_NOTIFICATION_BODY;
import static com.najah.dev.reddit_clone_backend.constant.EmailConstant.COMMENT_NOTIFICATION_SUBJECT;

@Service
@AllArgsConstructor
@Slf4j
@Transactional
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;
    private final PostRepository postRepository;
    private final AuthService authService;
    private final UserRepository userRepository;
    private final CommentMapper commentMapper;
    private final MailContentBuilderServiceImpl mailContentBuilder;
    private final MailServiceImpl mailService;

    @Override
    public void save(CommentDto commentDto) {
        // Create comment
        Post post = postRepository.findById(commentDto.getPostId())
                .orElseThrow(() -> new PostNotFoundException("No post found with id:" + commentDto.getPostId().toString()));
        Comment comment = commentMapper.map(commentDto, post, authService.getCurrentUser());
        commentRepository.save(comment);

        // send notification email
        String POST_URL = POSTS_API_BASE_URL + post.getPostId();
        String message = mailContentBuilder.build(authService.getCurrentUser().getUsername() + " " + COMMENT_NOTIFICATION_BODY + POST_URL);
        log.info("send mail to user:{}", post.getUser());
        sendCommentNotification(message, post.getUser());
    }

    @Override
    public List<CommentDto> getCommentsByPost(Long postId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new PostNotFoundException("No post found with id:" + postId.toString()));

        return commentRepository.findByPost(post)
                .stream()
                .map(commentMapper::mapToDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<CommentDto> getCommentsByUser(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("no user found with username:" + username));

        return commentRepository.findByUser(user)
                .stream()
                .map(commentMapper::mapToDto)
                .collect(Collectors.toList());
    }

    private void sendCommentNotification(String message, User user) {
//        log.info("send mail to user:{}", user);
        mailService.sendMail(new EmailNotification(user.getUsername() + " "+
                COMMENT_NOTIFICATION_SUBJECT, user.getEmail(), message));
    }
}
