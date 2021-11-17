package com.najah.dev.reddit_clone_backend.service;

import com.najah.dev.reddit_clone_backend.entity.EmailNotification;

public interface MailService {

    public void sendMail(EmailNotification emailNotification);

}
