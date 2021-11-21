package com.najah.dev.reddit_clone_backend.service;

import com.najah.dev.reddit_clone_backend.entity.RefreshToken;

public interface RefreshTokenService {

    public RefreshToken generateRefreshToken();

    void validateRefreshToken(String token);

    public void deleteRefreshToken(String token);

}
