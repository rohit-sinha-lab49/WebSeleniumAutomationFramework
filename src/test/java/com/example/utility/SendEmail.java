package com.example.utility;



import java.util.Properties;
import jakarta.mail.*;
import jakarta.mail.internet.*;

public class SendEmail {
    public static void main(String[] args) {
        final String username = "rohit.1si09is032@gmail.com";
        final String password = "vvzn ffkq kmfi cjdi"; // Use the generated app-specific password

        Properties prop = new Properties();
        prop.put("mail.smtp.host", "smtp.gmail.com");
        prop.put("mail.smtp.port", "587");
        prop.put("mail.smtp.auth", "true");
        prop.put("mail.smtp.starttls.enable", "true"); // TLS

        Session session = Session.getInstance(prop, new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        });

        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress("rohit.1si09is032@gmail.com"));
            message.setRecipients(
                    Message.RecipientType.TO,
                    InternetAddress.parse("rohitsinha025@gmail.com")
            );
            message.setSubject("Testing Jakarta Mail");
            message.setText("Dear Mail Crawler,"
                    + "\n\n This is a test email!");

            Transport.send(message);

            System.out.println("Done");

        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }
}
