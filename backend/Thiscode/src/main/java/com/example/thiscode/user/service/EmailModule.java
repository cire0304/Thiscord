package com.example.thiscode.user.service;

import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

import java.util.Random;

@RequiredArgsConstructor
@Component
public class EmailModule {

    private final JavaMailSender javaMailSender;

    private final int leftLimit = 48; // numeral '0'
    private final int rightLimit = 122; // letter 'z'
    private final int targetStringLength = 6;
    private final String EMAIL_SUB= "[ Thidcode ] 회웝가입 인증이메일";
    private final String EMAIL_FROM = "dltpwns0@gmail.com";

    public String generateCheckCode() {
        Random random = new Random();
        return random.ints(leftLimit, rightLimit + 1)
                .filter(i -> (i <= 57 || i >= 65) && (i <= 90 || i >= 97))
                .limit(targetStringLength)
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                .toString();
    }

    public void sendCheckCodeEmail(String email, String code) {
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(email);
        mailMessage.setFrom(EMAIL_FROM);
        mailMessage.setSubject(EMAIL_SUB);
        mailMessage.setText("다음 인증코드를 입력해주세요: " + code);
        javaMailSender.send(mailMessage);
    }

}
