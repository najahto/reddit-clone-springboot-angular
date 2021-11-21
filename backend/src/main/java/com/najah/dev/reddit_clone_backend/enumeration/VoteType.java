package com.najah.dev.reddit_clone_backend.enumeration;

public enum VoteType {
    UPVOTE(1),
    DOWNVOTE(-1),
    ;

    private int direction;

    VoteType(int direction){
    }



}
