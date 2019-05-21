package com.example.ei1027.message;

import javax.mail.MessagingException;

/**
 * Created by CIT on 21/05/2019.
 */
public interface EmailService {
    void sendSimpleMessage(String to, String subject, String text);
    void sendMessageWithAttachment(String to, String subject, String text, String pathToAttachment) throws MessagingException;
}
