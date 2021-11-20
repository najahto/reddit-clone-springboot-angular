package com.najah.dev.reddit_clone_backend.constant;

import static com.najah.dev.reddit_clone_backend.constant.BaseUrlConstant.*;

public class EmailConstant {
    public static final String FROM_EMAIL = "reddit_clone@email.com";
    public static final String ACTIVATE_ACCOUNT_SUBJECT = "Please activate your account";
    public static final String ACTIVATE_ACCOUNT_BODY = "Thank you for signing up to Reddit Clone," +
            "please click on the below url to activate your account :" +
            BASE_URL + "/api/auth/accountVerification/";

    public static final  String COMMENT_NOTIFICATION_SUBJECT = "Commented on your post";
    public static final  String COMMENT_NOTIFICATION_BODY = "Posted a comment on your post: ";
}
