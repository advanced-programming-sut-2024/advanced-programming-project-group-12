package com.mygdx.game.model.network.email;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

public class Sender {

    private static final String USERNAME = "gwent.project.ap.sut@gmail.com";
    private static final String APP_PASSWORD = "lklizorjtvkhqawu";

    public static void sendEmail(String recipient, String subject, String content) {
        Properties properties = new Properties();
        properties.put("mail.smtp.host", "smtp.gmail.com");
        properties.put("mail.smtp.port", "587");
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");

        Session session = Session.getInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(USERNAME, APP_PASSWORD);
            }
        });

        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(USERNAME));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(recipient));
            message.setSubject(subject);
            message.setText(content);

            Transport.send(message);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }

    public static void sendVerificationCode(String recipient, String verificationCode) {
        String subject = "Your Verification Code";
        String content = "Your verification code is: " + verificationCode;
        sendEmail(recipient, subject, content);
        System.out.println("email sent to " + recipient);
    }

    public static void sendEmailVerificationLink(String recipient, String verificationToken) {
        String subject = "Email Verification";
        String content = "Please click the following link to verify your email: "
                + "http://localhost:4567/verify?token=" + verificationToken;
        sendEmail(recipient, subject, content);
    }
}
