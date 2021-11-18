package com.najah.dev.reddit_clone_backend.service;

import com.najah.dev.reddit_clone_backend.dto.AuthenticationResponse;
import com.najah.dev.reddit_clone_backend.dto.LoginRequest;
import com.najah.dev.reddit_clone_backend.dto.RegisterRequest;

public interface AuthService {

    public void signup(RegisterRequest registerRequest);
    public void verifyAccount(String token);
    public AuthenticationResponse login(LoginRequest loginRequest);

}
