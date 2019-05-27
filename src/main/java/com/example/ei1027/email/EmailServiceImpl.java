package com.example.ei1027.email;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

@Component
public class EmailServiceImpl implements EmailService {
    @Autowired
    JavaMailSender emailSender;

    @Autowired
    TemplateEngine templateEngine;

    @Override
    public void sendSimpleMessage(String to, String subject, String templateName) throws MessagingException {
        MimeMessage message = emailSender.createMimeMessage();
        MimeMessageHelper messageHelper = new MimeMessageHelper(message, true, "UTF-8");
        messageHelper.setSubject(subject);
        Context ctx = new Context();
        String htmlContent = this.templateEngine.process("messages/" + templateName, ctx);
        messageHelper.setText(htmlContent, true);
        messageHelper.setTo(to);
        emailSender.send(message);

    }


}
