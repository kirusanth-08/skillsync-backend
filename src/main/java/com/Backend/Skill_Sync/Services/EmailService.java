package com.Backend.Skill_Sync.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;

    public void sendResetToken(String toEmail, String token) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(toEmail);
        message.setSubject("SkillSync Password Reset Token");
        message.setText("Here is your password reset token (valid for 15 minutes):\n\n" + token);
        message.setFrom("yourgmail@gmail.com");

        mailSender.send(message);
    }
}
