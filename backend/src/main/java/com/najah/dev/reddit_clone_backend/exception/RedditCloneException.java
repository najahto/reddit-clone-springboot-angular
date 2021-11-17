package com.najah.dev.reddit_clone_backend.exception;

public class RedditCloneException extends RuntimeException {
    public RedditCloneException(String exMessage, Exception exception) {
        super(exMessage, exception);
    }

    public RedditCloneException(String exMessage) {
        super(exMessage);
    }
}
