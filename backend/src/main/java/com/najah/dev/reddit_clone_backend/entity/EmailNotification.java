package com.najah.dev.reddit_clone_backend.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmailNotification {

    private String subject;

    private String recipient;

    private String body;
}
