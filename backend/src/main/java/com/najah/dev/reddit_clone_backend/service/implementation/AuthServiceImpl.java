package com.najah.dev.reddit_clone_backend.service.implementation;

import com.najah.dev.reddit_clone_backend.constant.EmailConstant;
import com.najah.dev.reddit_clone_backend.dto.RegisterRequest;
import com.najah.dev.reddit_clone_backend.entity.EmailNotification;
import com.najah.dev.reddit_clone_backend.entity.User;
import com.najah.dev.reddit_clone_backend.entity.VerificationToken;
import com.najah.dev.reddit_clone_backend.exception.RedditCloneException;
import com.najah.dev.reddit_clone_backend.repository.UserRespository;
import com.najah.dev.reddit_clone_backend.repository.VerificationTokenRepository;
import com.najah.dev.reddit_clone_backend.service.AuthService;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

import static com.najah.dev.reddit_clone_backend.constant.EmailConstant.*;
import static java.time.Instant.now;

@Service
@AllArgsConstructor
@Transactional
public class AuthServiceImpl implements AuthService {

    private final PasswordEncoder passwordEncoder;
    private final UserRespository userRespository;
    private final VerificationTokenRepository verificationTokenRepository;
    private final MailServiceImpl mailService;

    @Override
    public void signup(RegisterRequest registerRequest) {
        User user = new User();
        user.setUsername(registerRequest.getUsername());
        user.setEmail(registerRequest.getEmail());
        user.setPassword(passwordEncoder.encode(registerRequest.getPassword()));
        user.setCreated(now());
        user.setEnabled(false);
        userRespository.save(user);

        String token = generateVerificationToken(user);
        mailService.sendMail(new EmailNotification(ACTIVATE_ACCOUNT_SUBJECT, user.getEmail(),
                ACTIVATE_ACCOUNT_BODY + token));
    }

    private String generateVerificationToken(User user) {
        String token = UUID.randomUUID().toString();
        VerificationToken verificationToken = new VerificationToken();
        verificationToken.setToken(token);
        verificationToken.setUser(user);
        verificationTokenRepository.save(verificationToken);

        return token;
    }

    public void verifyAccount(String token) {
        Optional<VerificationToken> verificationToken = verificationTokenRepository.findByToken(token);
        verificationToken.orElseThrow(() -> new RedditCloneException("Invalid Token"));
        enableUser(verificationToken.get());
    }

    private void enableUser(VerificationToken verificationToken) {
        String username = verificationToken.getUser().getUsername();
        User user = userRespository.findByUsername(username).orElseThrow(() -> new RedditCloneException("No user found with username" + username));
        user.setEnabled(true);
        userRespository.save(user);
    }


}
