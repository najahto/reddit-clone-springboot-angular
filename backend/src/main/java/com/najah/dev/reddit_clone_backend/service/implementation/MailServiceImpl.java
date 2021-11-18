package com.najah.dev.reddit_clone_backend.service.implementation;

import com.najah.dev.reddit_clone_backend.entity.EmailNotification;
import com.najah.dev.reddit_clone_backend.exception.RedditCloneException;
import com.najah.dev.reddit_clone_backend.service.MailService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Service;

import static com.najah.dev.reddit_clone_backend.constant.EmailConstant.FROM_EMAIL;

@Service
@AllArgsConstructor
@Slf4j
public class MailServiceImpl implements MailService {

    private final JavaMailSender mailSender;
    private final MailContentBuilderServiceImpl mailContentBuilder;

    @Override
    public void sendMail(EmailNotification emailNotification) {
        MimeMessagePreparator messagePreparation = mimeMessage -> {
            MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage);
            messageHelper.setFrom(FROM_EMAIL);
            messageHelper.setTo(emailNotification.getRecipient());
            messageHelper.setSubject(emailNotification.getSubject());
            messageHelper.setText(emailNotification.getBody());
        };
        try {
            mailSender.send(messagePreparation);
            log.info("Activation email sent!!");
        } catch (MailException e) {
            log.error("Exception occurred when sending mail", e);
            throw new RedditCloneException("Exception occurred when sending mail to " + emailNotification.getRecipient(), e);
        }
    }
}
