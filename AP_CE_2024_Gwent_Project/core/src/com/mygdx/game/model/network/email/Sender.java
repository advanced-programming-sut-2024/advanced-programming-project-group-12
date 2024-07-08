package com.mygdx.game.model.network.email;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

public class Sender {

    public static void sendEmail(String recipient, String verificationToken) {
        Properties properties = new Properties();
        properties.put("mail.smtp.host", "smtp.gmail.com");
        properties.put("mail.smtp.port", "587");
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");

        // Use the generated app password here in place of your actual password
        final String username = "gwent.project.ap.sut@gmail.com";
        final String appPassword = "lklizorjtvkhqawu";

        Session session = Session.getInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, appPassword);
            }
        });

        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(username));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(recipient));
            message.setSubject("Verification Email");
            message.setText("Please use the following token to verify your email: " + verificationToken);

            Transport.send(message);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }
}