package com.najah.dev.reddit_clone_backend.service;

import com.najah.dev.reddit_clone_backend.dto.RegisterRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;

public interface AuthService {

    public void signup(RegisterRequest registerRequest);

}
